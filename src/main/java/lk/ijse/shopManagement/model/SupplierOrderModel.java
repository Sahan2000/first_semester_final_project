package lk.ijse.shopManagement.model;

import lk.ijse.shopManagement.dto.DetailDTO;
import lk.ijse.shopManagement.dto.SupplierOrderDTO;
import lk.ijse.shopManagement.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SupplierOrderModel {
    public static Boolean saveOrder(SupplierOrderDTO s1) throws SQLException {
        String sql = "INSERT INTO supplier_orders VALUES(?,?,?,?,?)";
        return CrudUtil.execute(sql,s1.getSuo_order_id(),s1.getDate(),s1.getSupplierid(),s1.getAmount(),s1.getStatus());
    }

    public static boolean saveOrderDetail(ArrayList<DetailDTO> details, SupplierOrderDTO s1) throws SQLException {
        for(DetailDTO dto :  details) {
            if(!save(s1, dto)) {
                return false;
            }
        }
        return true;
    }

    private static boolean save(SupplierOrderDTO s1 , DetailDTO d1) throws SQLException {
        String sql = "INSERT INTO supplier_order_details VALUES (?, ?, ?, ?)";
        return CrudUtil.execute(sql,s1.getSuo_order_id(),d1.getCode(),d1.getAmount(),d1.getQty());
    }

    /*public static boolean placeOrder(SupplierOrderDTO s1, InvoiceDTO invoice) throws SQLException {
        try {
            DbConnection.getInstance().getConnection().setAutoCommit(false);
            Boolean saveOrder = SupplierOrderModel.saveOrder(s1);
            if (saveOrder) {
                System.out.println("Done 1");
                boolean saveOrderDetail = SupplierOrderModel.saveOrderDetail(s1.getDetails(), s1);
                if (saveOrderDetail) {
                    System.out.println("Done 2");
                    boolean updateLoadQty = ItemModel.updateLoadQty(s1.getDetails());
                    if (updateLoadQty){
                        System.out.println("Done 3");
                        String status = s1.getStatus();
                        boolean saveInvoice = false;
                        boolean save = false;
                        if (status.equals("Invoice")){
                            System.out.println("Done 4");
                            saveInvoice =invoiceModel.savehavePay(invoice);
                        }else {
                            System.out.println("Done 5");
                            save = invoiceModel.save(invoice);
                        }
                        if ((save) || (saveInvoice)) {
                            DbConnection.getInstance().getConnection().commit();
                            return true;
                        }
                    }
                }
            }
            DbConnection.getInstance().getConnection().rollback();
            return false;
        } finally {
            DbConnection.getInstance().getConnection().setAutoCommit(true);
        }
    }*/

    public static String generateNextLoadId() throws SQLException {
        String sql = "SELECT supplier_order_id FROM supplier_orders ORDER BY supplier_order_id DESC LIMIT 1";
        ResultSet resultSet = CrudUtil.execute(sql);
        if (resultSet.next()){
            String id = resultSet.getString(1);
            return splitOrderId(id);
        }
        return splitOrderId(null);
    }

    public static String splitOrderId(String id){
        if (id != null){
            String[] strings = id.split("L");
            int index = Integer.parseInt(strings[1]);
            index++;
            String generated = String.format("L%03d", index);
            return generated ;
        }
        return "L001";
    }
}
