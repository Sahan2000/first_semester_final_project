package lk.ijse.shopManagement.dao.custom.Impl;

import lk.ijse.shopManagement.dao.custom.DeliveryDAO;
import lk.ijse.shopManagement.entity.Delivery;
import lk.ijse.shopManagement.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DeliveryDAOImpl implements DeliveryDAO {
    @Override
    public boolean save(Delivery entity) throws SQLException {
        return false;
    }

    @Override
    public boolean update(Delivery entity) throws SQLException {
        return false;
    }

    @Override
    public ArrayList<Delivery> getAll() throws SQLException {
        return null;
    }

    @Override
    public Delivery search(String id) throws SQLException {
        return null;
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return false;
    }

    @Override
    public String generateNextId() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT delivery_id FROM delivery ORDER BY delivery_id DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("delivery_id");
            int newCustomerId = Integer.parseInt(id.replace("D00-", "")) + 1;
            return String.format("D00-%03d", newCustomerId);
        } else {
            return "D00-001";
        }
    }

    @Override
    public boolean savedelivery(Delivery delivery) throws SQLException {
        String sql = "INSERT INTO delivery(delivery_id,distance,amount) VALUES(?,?,?)";
        return CrudUtil.execute(sql,delivery.getId(),delivery.getDistance(),delivery.getAmount());
    }
}
