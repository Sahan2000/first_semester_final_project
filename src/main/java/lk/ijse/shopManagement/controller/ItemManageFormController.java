package lk.ijse.shopManagement.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import lk.ijse.shopManagement.bo.BOFactory;
import lk.ijse.shopManagement.bo.custom.ItemBO;
import lk.ijse.shopManagement.bo.custom.SupplierBO;
import lk.ijse.shopManagement.dto.ItemDTO;
import lk.ijse.shopManagement.dto.SupplierDTO;
import lk.ijse.shopManagement.dto.tm.ItemTM;
import lk.ijse.shopManagement.regex.Regex;
import lk.ijse.shopManagement.regex.RegexPattern;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class ItemManageFormController implements Initializable {
    @FXML
    public JFXTextField txtCode;
    @FXML
    public JFXTextField txtName;
    @FXML
    public JFXTextField txtSupplier;
    @FXML
    public JFXTextField txtGetPrice;
    @FXML
    public JFXTextField txtSellPrice;
    @FXML
    public JFXTextField TxtQty;
    @FXML
    public JFXComboBox<String> cmbItemType;
    @FXML
    public TableView tblItem;
    @FXML
    public TableColumn colItemName;
    @FXML
    public TableColumn colItemCode;
    @FXML
    public TableColumn colItemType;
    @FXML
    public TableColumn colItemQty;
    @FXML
    public JFXComboBox cmbSupplier;

    ItemBO itemBO = (ItemBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ITEM);
    SupplierBO supplierBO = (SupplierBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.SUPPLIER);
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadItemType();
        getAll();
        setcellValueFactory();
        loadSupplier();
    }

    private void loadSupplier() {
        ObservableList<String> observableList = FXCollections.observableArrayList();
        try {
            List<String> supplierList = supplierBO.getSupplierName();
            for (String supplier:supplierList) {
                observableList.add(supplier);
            }
            cmbSupplier.setItems(observableList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void setcellValueFactory() {
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colItemName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        colItemType.setCellValueFactory(new PropertyValueFactory<>("itemType"));
        colItemQty.setCellValueFactory(new PropertyValueFactory<>("quantity"));
    }

    void getAll(){
        ObservableList<ItemTM> observableList = FXCollections.observableArrayList();
        try {
            List<ItemDTO> itemList = itemBO.getAllItem();
            for (ItemDTO item:itemList) {
                observableList.add(new ItemTM(
                        item.getItemCode(),
                        item.getItemName(),
                        item.getSellPrice(),
                        item.getGetPrice(),
                        item.getItemType(),
                        item.getSupplier(),
                        item.getQuantity()
                ));
            }
            tblItem.setItems(observableList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void loadItemType() {
        ObservableList<String> option = FXCollections.observableArrayList(
                "Home appliances",
                "Kitchen appliances",
                "Refrigerator",
                "Washing machine",
                "Television",
                "Air conditioners"
        );
        cmbItemType.setItems(option);
    }

    public void cmbItemTypeOnAction(ActionEvent actionEvent) {
        String itemType = cmbItemType.getValue();
    }

    public void saveBtnOnAction(ActionEvent actionEvent) {
        String code = txtCode.getText();
        String name = txtName.getText();
        String itemType = cmbItemType.getValue();
        String supplier = String.valueOf(cmbSupplier.getValue());
        double getPrice = Double.parseDouble(txtGetPrice.getText());
        double sellPrice = Double.parseDouble(txtSellPrice.getText());
        int quantity = Integer.parseInt(TxtQty.getText());

        try {
            String id = supplierBO.getSupplierId(supplier);
            if (id== null){
                new Alert(Alert.AlertType.ERROR, "invalid supplier name");
            }else {
                boolean isSaved = itemBO.saveItem(new ItemDTO(code,name,sellPrice,getPrice,itemType,id,quantity));
                if (isSaved){
                    new Alert(Alert.AlertType.CONFIRMATION,"New item saved");
                    getAll();
                }else{
                    new Alert(Alert.AlertType.ERROR,"item not founded");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.WARNING,"SQLException");
        }
    }

    public void updateOnAction(ActionEvent actionEvent) {
        String code = txtCode.getText();
        String name = txtName.getText();
        String itemType = cmbItemType.getValue();
        String supplier = String.valueOf(cmbSupplier.getValue());
        double getPrice = Double.parseDouble(txtGetPrice.getText());
        double sellPrice = Double.parseDouble(txtSellPrice.getText());
        int quantity = Integer.parseInt(TxtQty.getText());

        try {
            String id = supplierBO.getSupplierId(supplier);
            if (id == null){
                new Alert(Alert.AlertType.ERROR,"Supplier not founded");
            }else {
                boolean isUpdated = itemBO.updateItem(new ItemDTO(code,name,sellPrice,getPrice,itemType,id,quantity));
                if (isUpdated){
                    new Alert(Alert.AlertType.CONFIRMATION,"Item updated").show();
                    getAll();
                }else {
                    new Alert(Alert.AlertType.ERROR,"Item not updated").show();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.WARNING,"SQLException").show();
        }
    }


    public void deleteBtnOnAction(ActionEvent actionEvent) {
        String item_code = txtCode.getText();
        try {
            boolean isDeleted = itemBO.deleteItem(item_code);
            if (isDeleted){
                new Alert(Alert.AlertType.CONFIRMATION, "item deleted").show();
                getAll();
            }else {
                new Alert(Alert.AlertType.ERROR,"Item not founded").show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.WARNING,"SQLException").show();
        }
    }

    public void itemCodeSearchOnAction(ActionEvent actionEvent) {
        String code = txtCode.getText();
        try {
            ItemDTO item = itemBO.searchItem(code);
            txtName.setText(item.getItemName());
            txtGetPrice.setText(String.valueOf(item.getGetPrice()));
            txtSellPrice.setText(String.valueOf(item.getSellPrice()));
            TxtQty.setText(String.valueOf(item.getQuantity()));
            SupplierDTO supplierDTO = supplierBO.searchSupplier(item.getSupplier());
            cmbSupplier.setValue(supplierDTO.getSupplierName());
            cmbItemType.setValue(item.getItemType());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void newItemLoadBtnOnAction(ActionEvent actionEvent) throws IOException {
        Parent load = FXMLLoader.load(getClass().getResource("/view/addNewItemLoadForm.fxml"));
        Scene scene = new Scene(load);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Add New Item Load");
        stage.centerOnScreen();
        stage.show();
    }

    public void itemTblOnMouseClicked(MouseEvent mouseEvent) {
        ItemTM selectedItem = (ItemTM) tblItem.getSelectionModel().getSelectedItem();
        try {
            ItemDTO item = itemBO.searchItem(selectedItem.getItemCode());
            txtCode.setText(item.getItemCode());
            txtName.setText(item.getItemName());
            SupplierDTO supplierDTO = supplierBO.searchSupplier(item.getSupplier());
            cmbSupplier.setValue(supplierDTO.getSupplierName());
            txtSellPrice.setText(String.valueOf(item.getSellPrice()));
            txtGetPrice.setText(String.valueOf(item.getGetPrice()));
            TxtQty.setText(String.valueOf(item.getQuantity()));
            cmbItemType.setValue(item.getItemType());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void clearBtnOnAction(ActionEvent actionEvent) {
        txtCode.clear();
        txtName.clear();
        cmbItemType.setValue("");
        cmbSupplier.setValue("");
        txtGetPrice.clear();
        txtSellPrice.clear();
        TxtQty.clear();
    }

    public void cmbSupplierOnAction(ActionEvent actionEvent) {
        cmbSupplier.getValue();
    }

    public void TxtQtyOnKeyReleased(KeyEvent keyEvent) {
        if (Regex.getInstance().getPattern(RegexPattern.INT_PATTERN).matcher(TxtQty.getText()).matches()){
            TxtQty.setFocusColor(Color.WHITE);
        }else {
            TxtQty.setFocusColor(Color.RED);
        }
    }

    public void txtCodeOnKeyReleased(KeyEvent keyEvent) {
        if (Regex.getInstance().getPattern(RegexPattern.ITEM_ID_PATTERN).matcher(txtCode.getText()).matches()){
            txtCode.setFocusColor(Color.WHITE);
        }else {
            txtCode.setFocusColor(Color.RED);
        }
    }

    public void txtGetPriceOnKeyReleased(KeyEvent keyEvent) {
        if (Regex.getInstance().getPattern(RegexPattern.DOUBLE_PATTERN).matcher(txtGetPrice.getText()).matches()){
            txtGetPrice.setFocusColor(Color.WHITE);
        }else {
            txtGetPrice.setFocusColor(Color.RED);
        }
    }

    public void txtSellPriceOnKeyReleased(KeyEvent keyEvent) {
        if (Regex.getInstance().getPattern(RegexPattern.DOUBLE_PATTERN).matcher(txtSellPrice.getText()).matches()){
            txtSellPrice.setFocusColor(Color.WHITE);
        }else {
            txtSellPrice.setFocusColor(Color.RED);
        }
    }

    public void txtNameOnKeyReleased(KeyEvent keyEvent) {
        if (Regex.getInstance().getPattern(RegexPattern.NAME_PATTERN).matcher(txtName.getText()).matches()){
            txtName.setFocusColor(Color.WHITE);
        }else {
            txtName.setFocusColor(Color.RED);
        }
    }
    public void newBtnOnAction(ActionEvent actionEvent) {
        try {
            String nextId = itemBO.generateNextItemId();
            txtCode.setText(nextId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
