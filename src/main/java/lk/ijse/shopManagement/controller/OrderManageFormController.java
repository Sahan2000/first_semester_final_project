package lk.ijse.shopManagement.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.shopManagement.bo.BOFactory;
import lk.ijse.shopManagement.bo.custom.CustomerBO;
import lk.ijse.shopManagement.bo.custom.ItemBO;
import lk.ijse.shopManagement.bo.custom.OrderBO;
import lk.ijse.shopManagement.dto.CartDTO;
import lk.ijse.shopManagement.dto.CustomerDTO;
import lk.ijse.shopManagement.dto.ItemDTO;
import lk.ijse.shopManagement.dto.OrderDetails;
import lk.ijse.shopManagement.dto.tm.PlaceOrderTM;
import lk.ijse.shopManagement.entity.Order;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class OrderManageFormController implements Initializable {
    @FXML
    public Label lblOrderId;
    @FXML
    public Label lblOrderDate;
    @FXML
    public JFXTextField txtCustomerNIC;
    @FXML
    public Label lblCustomerName;
    @FXML
    public Label lblCustomerId;
    @FXML
    public JFXComboBox cmbItemCode;
    @FXML
    public Label lblItemName;
    @FXML
    public Label lblItemUnitProce;
    @FXML
    public Label lblitemQTY;
    @FXML
    public TableColumn colItemCode;
    @FXML
    public TableColumn colItemName;
    @FXML
    public TableColumn colItemQTY;
    @FXML
    public TableColumn colUnitPrice;
    @FXML
    public TableColumn colItemTotal;
    @FXML
    public TableColumn colAction;

    @FXML
    private JFXRadioButton btnYes;

    @FXML
    private JFXRadioButton btnNo;

    @FXML
    public Label lblTotal;
    public TableView<PlaceOrderTM> tblOrderCart;
    @FXML
    public JFXTextField txtQty;
    private ObservableList<PlaceOrderTM> obList = FXCollections.observableArrayList();
    public String status = null;
    public static Order order;
    public static List<OrderDetails> orderDetailsList = new ArrayList<>();


    @FXML
    private AnchorPane root1;

    ItemBO itemBO = (ItemBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ITEM);
    CustomerBO customerBO = (CustomerBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.CUSTOMER);
    OrderBO orderBO = (OrderBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ORDER);

    @FXML
    public void newBtnOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/customerManageForm.fxml"));
        root1.getChildren().clear();
        root1.getChildren().add(anchorPane);
    }

    @FXML
    public void deliveryFormBtnOnAction(ActionEvent actionEvent) throws IOException, SQLException {
        String NIC = txtCustomerNIC.getText();
        CustomerDTO customer = customerBO.searchCustomerNIC(NIC);
        boolean deliveryAction = false;
        if (btnYes.isSelected()) {
            deliveryAction = true;
            status = "Yes";
        }
        if (btnNo.isSelected()) {
            deliveryAction = false;
            status = "No";
        }
        if (customer != null) {
            double net_total = Double.valueOf(lblTotal.getText());
            LocalDate date = LocalDate.parse(lblOrderDate.getText());
            order = new Order(lblOrderId.getText(), date, LocalTime.now(), lblCustomerId.getText(), status);

            List<CartDTO> cartDTOList = new ArrayList<>();

            for (int i = 0; i < tblOrderCart.getItems().size(); i++) {
                PlaceOrderTM tm = obList.get(i);

                CartDTO cartDTO = new CartDTO(tm.getCode(), tm.getQty(), tm.getUnitPrice());
                cartDTOList.add(cartDTO);
                OrderDetails orderDetails1 = new OrderDetails(tm.getCode(), lblOrderId.getText(), tm.getQty(), tm.getTotal());
                orderDetailsList.add(orderDetails1);
            }
            DeliveryManageFormController.cartDTOList = cartDTOList;

        }

        DeliveryManageFormController.Oid = lblOrderId.getText();
        DeliveryManageFormController.custId = lblCustomerId.getText();

        Parent load = FXMLLoader.load(getClass().getResource("/view/deliveryManageForm.fxml"));
        Scene scene = new Scene(load);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Delivery Manage Form");
        stage.centerOnScreen();
        stage.show();


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCellValueFactory();
        generateNextOrderId();
        setOrderDate();
        loadItemId();
    }

    private void setCellValueFactory() {
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colItemName.setCellValueFactory(new PropertyValueFactory<>("description"));
        colItemQTY.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colItemTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("btn"));
    }

    private void loadItemId() {
        ObservableList<String> observableList = FXCollections.observableArrayList();
        try {
            List<String> item = itemBO.getCodes();
            for (String itemCodes : item) {
                observableList.add(itemCodes);
            }
            cmbItemCode.setItems(observableList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setOrderDate() {
        lblOrderDate.setText(String.valueOf(LocalDate.now()));
    }

    private void generateNextOrderId() {
        try {
            String nextId = orderBO.generateNextOrderId();
            lblOrderId.setText(nextId);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void customerNICOnAction(ActionEvent actionEvent) {
        String NIC = txtCustomerNIC.getText();

        try {
            CustomerDTO customer = customerBO.searchCustomerNIC(NIC);
            if (customer != null) {
                lblCustomerName.setText(customer.getName());
                lblCustomerId.setText(customer.getId());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void cmbItemCodeOnAction(ActionEvent actionEvent) {
        String code = String.valueOf(cmbItemCode.getValue());

        try {
            ItemDTO item = itemBO.searchItem(code);
            if (item != null) {
                lblItemName.setText(item.getItemName());
                lblItemUnitProce.setText(String.valueOf(item.getSellPrice()));
                lblitemQTY.setText(String.valueOf(item.getQuantity()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void addToCartBtnOnAction(ActionEvent actionEvent) {
        String code = String.valueOf(cmbItemCode.getValue());
        String description = lblItemName.getText();
        int qty = Integer.parseInt(txtQty.getText());
        double unitPrice = Double.parseDouble(lblItemUnitProce.getText());
        double total = qty * unitPrice;
        Button btnRemove = new Button("Remove");
        btnRemove.setCursor(Cursor.HAND);

        setRemoveBtnOnAction(btnRemove);

        if (!obList.isEmpty()) {
            for (int i = 0; i < tblOrderCart.getItems().size(); i++) {
                if (colItemCode.getCellData(i).equals(code)) {
                    qty += (int) colItemQTY.getCellData(i);
                    total = qty * unitPrice;

                    obList.get(i).setQty(qty);
                    obList.get(i).setTotal(total);

                    tblOrderCart.refresh();
                    calculateNetTotal();
                    return;
                }
            }
        }

        PlaceOrderTM tm = new PlaceOrderTM(code, description, qty, unitPrice, total, btnRemove);

        obList.add(tm);
        tblOrderCart.setItems(obList);
        calculateNetTotal();

        txtQty.setText("");
    }

    private void calculateNetTotal() {
        double netTotal = 0.0;
        for (int i = 0; i < tblOrderCart.getItems().size(); i++) {
            double total = (double) colItemTotal.getCellData(i);
            netTotal += total;
        }
        lblTotal.setText(String.valueOf(netTotal));
    }

    private void setRemoveBtnOnAction(Button btn) {
        btn.setOnAction((e) -> {
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> result = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

            if (result.orElse(no) == yes) {

                int index = tblOrderCart.getSelectionModel().getSelectedIndex();

                obList.remove(index);

                tblOrderCart.refresh();
                calculateNetTotal();
            }
        });
    }

    public void yesRadioBtnOnAction(ActionEvent actionEvent) {
        btnYes.setSelected(true);
        btnNo.setSelected(false);
    }

    public void noRadioBtnOnAction(ActionEvent actionEvent) {
        btnNo.setSelected(true);
        btnYes.setSelected(false);
    }
}
