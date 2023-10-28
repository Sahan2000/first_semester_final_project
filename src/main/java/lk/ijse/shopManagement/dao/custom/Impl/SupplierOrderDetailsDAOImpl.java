package lk.ijse.shopManagement.dao.custom.Impl;

import lk.ijse.shopManagement.dao.custom.SupplierOrderDetailsDAO;
import lk.ijse.shopManagement.entity.Details;
import lk.ijse.shopManagement.util.CrudUtil;

import java.sql.SQLException;
import java.util.ArrayList;

public class SupplierOrderDetailsDAOImpl implements SupplierOrderDetailsDAO {
    @Override
    public boolean save(Details entity) throws SQLException {
        String sql = "INSERT INTO supplier_order_details VALUES (?, ?, ?, ?)";
        return CrudUtil.execute(sql,entity.getSo_id(),entity.getCode(),entity.getAmount(),entity.getQty());
    }

    @Override
    public boolean update(Details entity) throws SQLException {
        return false;
    }

    @Override
    public ArrayList<Details> getAll() throws SQLException {
        return null;
    }

    @Override
    public Details search(String id) throws SQLException {
        return null;
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return false;
    }

    @Override
    public String generateNextId() throws SQLException {
        return null;
    }
}
