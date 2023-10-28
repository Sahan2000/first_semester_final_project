package lk.ijse.shopManagement.dao.custom.Impl;

import lk.ijse.shopManagement.dao.custom.HavePayInvoiceDAO;
import lk.ijse.shopManagement.entity.HavePayInvoice;
import lk.ijse.shopManagement.util.CrudUtil;

import java.sql.SQLException;
import java.util.ArrayList;

public class HavePayInvoiceDAOImpl implements HavePayInvoiceDAO {
    @Override
    public boolean save(HavePayInvoice entity) throws SQLException {
        String sql = "INSERT INTO havepay_invoice VALUES (?,?,?,?)";
        return CrudUtil.execute(sql, entity.getId(),entity.getDate(),entity.getSupid(),entity.getAmount());
    }

    @Override
    public boolean update(HavePayInvoice entity) throws SQLException {
        return false;
    }

    @Override
    public ArrayList<HavePayInvoice> getAll() throws SQLException {
        return null;
    }

    @Override
    public HavePayInvoice search(String id) throws SQLException {
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
