package lk.ijse.shopManagement.dao.custom;

import lk.ijse.shopManagement.dao.CrudDAO;
import lk.ijse.shopManagement.entity.Supplier;

import java.sql.SQLException;
import java.util.List;

public interface SupplierDAO extends CrudDAO<Supplier> {
    List<String> getSupplierName() throws SQLException;

    String getSupplierId(String supplier) throws SQLException;
}
