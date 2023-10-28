package lk.ijse.shopManagement.dao.custom;

import lk.ijse.shopManagement.dao.CrudDAO;
import lk.ijse.shopManagement.entity.Details;
import lk.ijse.shopManagement.entity.Invoice;
import lk.ijse.shopManagement.entity.SupplierOrder;

import java.sql.SQLException;

public interface SupplierOrderDAO extends CrudDAO<SupplierOrder> {
    boolean placeSupplierOrder(SupplierOrder s1, Invoice invoice, Details details) throws SQLException;

}
