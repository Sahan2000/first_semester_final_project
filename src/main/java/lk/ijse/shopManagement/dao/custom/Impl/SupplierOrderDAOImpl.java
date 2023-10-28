package lk.ijse.shopManagement.dao.custom.Impl;

import lk.ijse.shopManagement.dao.DAOFactory;
import lk.ijse.shopManagement.dao.custom.*;
import lk.ijse.shopManagement.db.DbConnection;
import lk.ijse.shopManagement.entity.Details;
import lk.ijse.shopManagement.entity.HavePayInvoice;
import lk.ijse.shopManagement.entity.Invoice;
import lk.ijse.shopManagement.entity.SupplierOrder;
import lk.ijse.shopManagement.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SupplierOrderDAOImpl implements SupplierOrderDAO {

    //SupplierOrderDAO supplierOrderDAO = (SupplierOrderDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.SUPPLIER_ORDER);
    SupplierOrderDetailsDAO supplierOrderDetailsDAO = (SupplierOrderDetailsDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.SUPPLIER_ORDER_DETAILS);
    ItemDAO itemDAO = (ItemDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ITEM);
    InvoiceDAO invoiceDAO = (InvoiceDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.INVOICE);
    HavePayInvoiceDAO havePayInvoiceDAO = (HavePayInvoiceDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.HAVEPAYINVOICE);
    @Override
    public boolean save(SupplierOrder entity) throws SQLException {
        String sql = "INSERT INTO supplier_orders VALUES(?,?,?,?,?)";
        return CrudUtil.execute(sql,entity.getSup_order_id(),entity.getDate(),entity.getSupplierid(),entity.getAmount(),entity.getStatus());
    }

    @Override
    public boolean update(SupplierOrder entity) throws SQLException {
        return false;
    }

    @Override
    public ArrayList<SupplierOrder> getAll() throws SQLException {
        return null;
    }

    @Override
    public SupplierOrder search(String id) throws SQLException {
        return null;
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return false;
    }

    @Override
    public String generateNextId() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT supplier_order_id FROM supplier_orders ORDER BY supplier_order_id DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("supplier_order_id");
            int newCustomerId = Integer.parseInt(id.replace("L00-", "")) + 1;
            return String.format("L00-%03d", newCustomerId);
        } else {
            return "L00-001";
        }
    }

    @Override
    public boolean placeSupplierOrder(SupplierOrder s1, Invoice invoice, Details details) throws SQLException {
        try {
            DbConnection.getInstance().getConnection().setAutoCommit(false);
            Boolean saveOrder = save(s1);
            if (saveOrder) {

                boolean saveOrderDetail = supplierOrderDetailsDAO.save(details);
                if (saveOrderDetail) {

                    boolean updateLoadQty = itemDAO.updateQty(details);
                    if (updateLoadQty){

                        String status = s1.getStatus();
                        boolean saveInvoice = false;
                        boolean save = false;
                        if (status.equals("Invoice")){
                            HavePayInvoice havePayInvoice = new HavePayInvoice(invoice.getId(), invoice.getDate(),invoice.getSupid(), invoice.getAmount());
                            saveInvoice = havePayInvoiceDAO.save(havePayInvoice);
                        }else {

                            save = invoiceDAO.save(invoice);
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
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DbConnection.getInstance().getConnection().setAutoCommit(true);
        }
    }


}
