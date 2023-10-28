package lk.ijse.shopManagement.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import lk.ijse.shopManagement.bo.BOFactory;
import lk.ijse.shopManagement.bo.custom.CustomerBO;
import lk.ijse.shopManagement.db.DbConnection;
import lk.ijse.shopManagement.dto.CustomerDTO;
import lk.ijse.shopManagement.dto.tm.CustomerTM;
import lk.ijse.shopManagement.regex.Regex;
import lk.ijse.shopManagement.regex.RegexPattern;
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

public class CustomerManageFormController implements Initializable {
    CustomerBO customerBO = (CustomerBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.CUSTOMER);
    @FXML
    public JFXTextField txtId;
    @FXML
    public JFXTextField txtName;
    @FXML
    public JFXTextField txtAddress;
    @FXML
    public JFXTextField txtContact;
    @FXML
    public JFXTextField txtNIC;
    @FXML
    public TableView tblCustomer;
    @FXML
    public TableColumn colId;
    @FXML
    public TableColumn colName;
    @FXML
    public TableColumn colAddress;
    @FXML
    public TableColumn colContact;
    @FXML
    public TableColumn colNIC;
    @FXML
    public  CustomerTM selectedCustomer;
    @FXML
    public void saveBtnOnAction(ActionEvent actionEvent) {
        String id = txtId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String contact = txtContact.getText();
        String nic = txtNIC.getText();

        try {
            boolean isSaved = customerBO.saveCustomer(new CustomerDTO(id,name,address,contact,nic));
            if (isSaved){
                new Alert(Alert.AlertType.CONFIRMATION,"Customer saved").show();
                getAll();
            }else {
                new Alert(Alert.AlertType.ERROR, "Customer not saved").show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQLException").show();
        }
    }
    @FXML
    public void updateOnAction(ActionEvent actionEvent) {
        String id = txtId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String contact = txtContact.getText();
        String nic = txtNIC.getText();

        try {
            boolean isUpdated = customerBO.updateCustomer(new CustomerDTO(id,name,address,contact,nic));
            if (isUpdated){
                new Alert(Alert.AlertType.CONFIRMATION, "Customer updated").show();
                getAll();
            }else {
                new Alert(Alert.AlertType.ERROR, "Customer not updated").show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQLException");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCellValueFactory();
        getAll();
    }

    void getAll() {
        ObservableList<CustomerTM> oblist = FXCollections.observableArrayList();
        try {
            List<CustomerDTO> custList = customerBO.getAllCustomer();
            for (CustomerDTO customer:custList) {
                oblist.add(new CustomerTM(
                        customer.getId(),
                        customer.getName(),
                        customer.getContact(),
                        customer.getAddress(),
                        customer.getNic()
                ));
            }
            tblCustomer.setItems(oblist);

        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Query error");
        }
    }

    void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colNIC.setCellValueFactory(new PropertyValueFactory<>("nic"));
    }

    @FXML
    public void tblCustomerOnMouseClicked(MouseEvent mouseEvent) {
        CustomerTM selectedCustomer = (CustomerTM) tblCustomer.getSelectionModel().getSelectedItem();
        try {
            CustomerDTO customer = customerBO.searchCustomerId(selectedCustomer.getId());
            txtId.setText(customer.getId());
            txtName.setText(customer.getName());
            txtContact.setText(String.valueOf(customer.getContact()));
            txtAddress.setText(customer.getAddress());
            txtNIC.setText(customer.getNic());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteBtnOnAction(ActionEvent actionEvent) {
        String id = txtId.getText();

        try {
            boolean isDeleted = customerBO.deleteCustomer(id);
            if(isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Customer deleted !").show();
                getAll();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "something happened !").show();
        }
    }


    public void clearBtnOnAction(ActionEvent actionEvent) {
        txtNIC.clear();
        txtName.clear();
        txtAddress.clear();
        txtContact.clear();
        txtId.clear();
    }

    public void txtIdOnAction(ActionEvent actionEvent) {
        String id = txtId.getText();

        try {
            CustomerDTO customer = customerBO.searchCustomerId(id);
            if (customer != null){
                txtId.setText(customer.getId());
                txtName.setText(customer.getName());
                txtContact.setText(String.valueOf(customer.getContact()));
                txtAddress.setText(customer.getAddress());
                txtNIC.setText(customer.getNic());
            }else {
                new Alert(Alert.AlertType.ERROR, "Customer not found").show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.WARNING, "SQLException").show();
        }
    }

    public void reportBtnOnAction(ActionEvent actionEvent) throws JRException, SQLException {
        JasperDesign load = JRXmlLoader.load(new File("E:\\Final_Project_65\\src\\main\\resources\\report\\Customer1.jrxml"));
        JRDesignQuery newQuery = new JRDesignQuery();
        String sql = "SELECT * FROM customer";
        newQuery.setText(sql);
        load.setQuery(newQuery);
        JasperReport js = JasperCompileManager.compileReport(load);
        HashMap<String, Object> hm = new HashMap<>();
        JasperPrint jp = JasperFillManager.fillReport(js, hm, DbConnection.getInstance().getConnection());
        JasperViewer viewer = new JasperViewer(jp, false);
        viewer.show();

    }

    public void txtIdOnKeyReleased(KeyEvent keyEvent) {
        if (Regex.getInstance().getPattern(RegexPattern.CUSTOMER_ID_PATTERN).matcher(txtId.getText()).matches()){
            txtId.setFocusColor(Color.WHITE);
        }else {

            txtId.setFocusColor(Color.RED);
        }
    }

    public void txtNameOnKeyReleased(KeyEvent keyEvent) {
        if (Regex.getInstance().getPattern(RegexPattern.NAME_PATTERN).matcher(txtName.getText()).matches()){
            txtName.setFocusColor(Color.WHITE);
        }else {

            txtName.setFocusColor(Color.RED);
        }
    }

    public void txtAddressOnKeyReleased(KeyEvent keyEvent) {
        if (Regex.getInstance().getPattern(RegexPattern.ADDRESS_PATTERN).matcher(txtAddress.getText()).matches()){
            txtAddress.setFocusColor(Color.WHITE);
        }else {

            txtAddress.setFocusColor(Color.RED);
        }
    }

    public void txtContactOnKeyReleased(KeyEvent keyEvent) {
        if (Regex.getInstance().getPattern(RegexPattern.CONTACT_PATTERN).matcher(txtContact.getText()).matches()){
            txtContact.setFocusColor(Color.WHITE);
        }else {

            txtContact.setFocusColor(Color.RED);
        }
    }

    public void txtNICOnKeyreleased(KeyEvent keyEvent) {
        if (Regex.getInstance().getPattern(RegexPattern.CUSTOMER_NIC_OLD_PATTERN).matcher(txtNIC.getText()).matches() || Regex.getInstance().getPattern(RegexPattern.CUSTOMER_NIC_NEW_PTTERN).matcher(txtNIC.getText()).matches()){
            txtNIC.setFocusColor(Color.WHITE);
        }else {
            txtNIC.setFocusColor(Color.RED);
        }
    }

    public void newBtnOnAction(ActionEvent actionEvent) {
        try {
            String nextId = customerBO.generateNextCustomerId();
            txtId.setText(nextId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
