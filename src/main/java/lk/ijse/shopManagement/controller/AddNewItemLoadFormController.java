package lk.ijse.shopManagement.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.shopManagement.bo.BOFactory;
import lk.ijse.shopManagement.bo.custom.ItemBO;
import lk.ijse.shopManagement.bo.custom.SupplierBO;
import lk.ijse.shopManagement.bo.custom.SupplierOrderBO;
import lk.ijse.shopManagement.dto.*;
import lk.ijse.shopManagement.dto.tm.loadTM;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class AddNewItemLoadFormController {

    public TextField txtId;
    public JFXComboBox cmbsupplier;
    public TextField txtSupplierId;
    public JFXComboBox cmbitem;
    public TextField txtDesc;
    public TextField txtQty;
    public TextField txtPrice;
    public JFXButton btnAdd;
    public JFXTextField txtTotal;
    public JFXComboBox cmbStatus;
    public JFXTextField txtInvoice;
    public TableView tbload;
    public TableColumn colItemId;
    public TableColumn colqty;
    public TableColumn colPrice;
    public JFXButton btnLoad;
    private ObservableList<loadTM> oblist = FXCollections.observableArrayList();
    ItemDTO item;
    Optional<ItemDTO> items;

    SupplierBO supplierBO = (SupplierBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.SUPPLIER);
    ItemBO itemBO = (ItemBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ITEM);
    SupplierOrderBO supplierOrderBO = (SupplierOrderBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.SUPPLIER_ORDER);
    public void initialize() {
        loadSupplier();
        loadNewId();
        setcellValueFactory();
        String[] type = {"Invoice","Cash"};
        ObservableList<String> list = FXCollections.observableArrayList(type);
        cmbStatus.setItems(list);
    }

    private void loadNewId() {
        try {
            String id = supplierOrderBO.generateNextLoadId();
            txtId.setText(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void setcellValueFactory() {
        colItemId.setCellValueFactory(new PropertyValueFactory<loadTM,String>("itemid"));
        colqty.setCellValueFactory(new PropertyValueFactory<loadTM,Integer>("qty"));
        colPrice.setCellValueFactory(new PropertyValueFactory<loadTM,Double>("price"));
    }

    private void loadItem() {
        try {
            List<ItemDTO> itemList = itemBO.getitemBySupplierID(txtSupplierId.getText());
            ObservableList<String> itemId = FXCollections.observableArrayList();
            for (ItemDTO item : itemList) {
                String id = item.getItemCode();
                itemId.add(id);
            }
            cmbitem.setItems(itemId);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void loadSupplier() {
        try {
            ObservableList<String> list = FXCollections.observableArrayList();
            List<String> supplierName = supplierBO.getSupplierName();
            for (String name : supplierName){
                list.add(name);
            }
            cmbsupplier.setItems(list);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void setSupplierDetail(ActionEvent actionEvent) {
        String supplier = cmbsupplier.getSelectionModel().getSelectedItem().toString();
        try {
            String id = supplierBO.getSupplierId(supplier);
            txtSupplierId.setText(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        loadItem();
    }

    public void setItemDetail(ActionEvent actionEvent) {
        String id = cmbitem.getSelectionModel().getSelectedItem().toString();
        try {
            item = itemBO.searchItem(id);
            items = itemBO.searchByPk(id);
            txtDesc.setText(item.getItemName());
            txtQty.requestFocus();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void AddNewLoadOnAction(ActionEvent actionEvent) {
        String id = cmbitem.getSelectionModel().getSelectedItem().toString();
        int Qty = Integer.parseInt(txtQty.getText());
        double price = Double.parseDouble(txtPrice.getText());

        ObservableList s1 = tbload.getItems();
        if (!oblist.isEmpty()) {
            L1:
            /* check same item has been in table. If so, update that row instead of adding new row to the table */
            for (int i = 0; i <= s1.size(); i++) {
                if (colItemId.getCellData(i)==null){
                    break L1;
                }
                if (colItemId.getCellData(i).equals(id)) {
                    Qty += (int)colqty.getCellData(i);
                    price = items.get().getGetPrice()*Qty;

                    oblist.get(i).setQty(Qty);
                    oblist.get(i).setPrice(price);
                    tbload.refresh();
                    return;
                }
            }
        }
        System.out.println(id);
        oblist.add(new loadTM(id,Qty,price));
        tbload.setItems(oblist);
        calculateNetTotal();
    }

    private void calculateNetTotal() {
        double total = 0.0;
        for (loadTM loadtm : oblist){
            total+=loadtm.getPrice();
        }
        txtTotal.setText(String.valueOf(total));
    }

    public void LoadOnAction(ActionEvent actionEvent) {
        String status = cmbStatus.getSelectionModel().getSelectedItem().toString();
        System.out.println(status);
        if(status.equals(null)){
            new Alert(Alert.AlertType.WARNING, "Please select the status!").show();
        }
        String so_id = txtId.getText();
        String date = "2023-04-26";
        System.out.println(date);
        String supid = txtSupplierId.getText();
        double total = Double.parseDouble(txtTotal.getText());
        DetailDTO details = null;
        /* load all cart items' to orderDetails arrayList */
        for (int i = 0; i < tbload.getItems().size(); i++) {
            /* get each row details to (PlaceOrderTm)tm in each time and add them to the orderDetails */
            loadTM detailTM = oblist.get(i);
            details = new DetailDTO(so_id,detailTM.getItemid(),detailTM.getPrice(),detailTM.getQty());
        }
        SupplierOrderDTO supplierOrder = new SupplierOrderDTO(so_id,date,supid,total,status);
        InvoiceDTO invoice = new InvoiceDTO(txtInvoice.getText(),date,supid,total);
        System.out.println(supplierOrder);
        System.out.println(invoice);
        try {
            boolean placeOrder = supplierOrderBO.placeSupplierOrder(supplierOrder, invoice,details);
            if (placeOrder){
                new Alert(Alert.AlertType.CONFIRMATION,"Load is Added!", ButtonType.OK).show();
            }else {
                new Alert(Alert.AlertType.ERROR,"CAnnot Added!",ButtonType.CLOSE).show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void priceOnAction(ActionEvent actionEvent) {
        int Qty = Integer.parseInt(txtQty.getText());
        double price = items.get().getGetPrice() * Qty;
        txtPrice.setText(String.valueOf(price));
    }
}
