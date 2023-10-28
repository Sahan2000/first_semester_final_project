package lk.ijse.shopManagement.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import lk.ijse.shopManagement.bo.BOFactory;
import lk.ijse.shopManagement.bo.custom.CustomerBO;
import lk.ijse.shopManagement.bo.custom.DeliveryBO;
import lk.ijse.shopManagement.bo.custom.PlaceOrderBO;
import lk.ijse.shopManagement.db.DbConnection;
import lk.ijse.shopManagement.dto.*;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

public class DeliveryManageFormController implements Initializable {
    @FXML
    private JFXTextField txtDeliverId;

    @FXML
    private JFXTextField txtOrderId;

    @FXML
    private JFXTextField txtCustomerId;

    @FXML
    private JFXTextField txtCustomerName;

    @FXML
    private JFXTextField txtCustomerAddress;

    @FXML
    private JFXTextField txtDistance;

    @FXML
    private JFXTextField txtDelivertTotal;

    @FXML
    private JFXTextField txtNetTotal;

    public static List<CartDTO> cartDTOList;

    public static OrderDTO orders;

    private double new_total;
    private Object JRException;
    public static String Oid;
    public static String custId;
    private double delivery;
    public static List<OrderDetails> order = OrderManageFormController.orderDetailsList;

    DeliveryBO deliveryBO = (DeliveryBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.DELIVERY);

    PlaceOrderBO placeOrderBO = (PlaceOrderBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.PLACE_ORDER);

    CustomerBO customerBO =(CustomerBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.CUSTOMER);
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setValues();
        generateNextDeliveryId();
    }

    private void generateNextDeliveryId() {
        try {
            String nextId = deliveryBO.generateNextDeliveryId();
            txtDeliverId.setText(nextId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void setValues() {
        txtOrderId.setText(Oid);
        txtCustomerId.setText(custId);
        CustomerDTO customerDTO = null;
        try {
            customerDTO = customerBO.searchCustomerId(custId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        txtCustomerName.setText(customerDTO.getName());
        txtCustomerAddress.setText(customerDTO.getAddress());
        double calcnet = calcnet();
        txtNetTotal.setText(String.valueOf(calcnet));
        if (orders.getStatus().equals("Yes")) {
            txtDistance.setEditable(true);
            txtDelivertTotal.setEditable(true);
        } else {
            txtDistance.setEditable(false);
            txtDelivertTotal.setEditable(false);
            txtDistance.setText("0");
            txtDelivertTotal.setText("00.00");
        }

    }

    private double calcnet() {
        double net=0.0;
        for (OrderDetails cartDetail :order){
            net+=cartDetail.getAmount();
        }
        return net;
    }


    public void btnPlaceOrderOnAction(ActionEvent actionEvent) {
        DeliveryDTO delivery1 = makeObject();

        try {
            boolean isPlaced = placeOrderBO.placeOrder(new PlaceOrderDTO(orders.getId(),orders.getDate(),orders.getTime(),orders.getCustId(),orders.getStatus()),delivery1);
            if (isPlaced) {
                new Alert(Alert.AlertType.CONFIRMATION, "Order Placed!").show();

            } else {
                new Alert(Alert.AlertType.ERROR, "Order Not Placed!").show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }

    }

    private DeliveryDTO makeObject() {
        return new DeliveryDTO(txtDeliverId.getText(),Integer.parseInt(txtDistance.getText()),delivery);
    }

    public void generateBillOnAction(ActionEvent actionEvent) throws SQLException, JRException {
        JasperDesign load = null;
        load = JRXmlLoader.load(new File("E:\\Final_Project_65\\src\\main\\resources\\report\\OrderForm.jrxml"));
        JRDesignQuery newQuery = new JRDesignQuery();
        String sql = "select i.item_code as code, i.item_name as name , i.item_sell_price  as price ,oi.quantity as qty , i.item_sell_price*oi.quantity as subTotal " +
                "from item i inner join order_details oi on i.item_code = oi.item_code where oi.order_id = '" + txtOrderId.getText() + "'";;

                /*"select i.item_name as name , i.item_sell_price  as price ,oi.quantity as qty , i.item_sell_price*oi.quantity as subTotal " +
                "from item i inner join order_details oi on i.item_code = oi.item_code where oi.order_id = '" + "D001" + "'";*/
        newQuery.setText(sql);
        load.setQuery(newQuery);
        JasperReport js = JasperCompileManager.compileReport(load);
        HashMap<String, Object> hm = new HashMap<>();
        JasperPrint jp = JasperFillManager.fillReport(js, hm, DbConnection.getInstance().getConnection());
        JasperViewer viewer = new JasperViewer(jp, false);
        viewer.show();


    }

    public void calculateTotal(ActionEvent actionEvent) {
        int distance = Integer.parseInt(txtDistance.getText());
        int dist = distance-5;
        double frist = 5 * 150.00;
        if (distance < 5){
            delivery = distance * 200;
        }else {
            if (dist >= 0 && dist <= 10) {
                delivery = (dist * 250) + frist;
            }
            if (dist >= 11 && dist <= 20) {
                delivery = (dist * 350) + frist;
            }
            if (dist >= 21 && dist <= 30) {
                delivery = (dist * 450) + frist;
            }
            if (dist >= 31 && dist <= 55) {
                delivery = (dist * 550) + frist;
            }
            if (dist > 56) {
                delivery = (dist * 650) + frist;
            }
        }
        txtDelivertTotal.setText(String.valueOf(delivery));
        double calcnet = calcnet();
        double total = calcnet +delivery;
        txtNetTotal.setText(String.valueOf(total));
    }
}

