package lk.ijse.shopManagement.dao.custom.Impl;

import lk.ijse.shopManagement.dao.custom.OrderDAO;
import lk.ijse.shopManagement.entity.Order;
import lk.ijse.shopManagement.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDAOImpl implements OrderDAO {
    @Override
    public boolean save(Order entity) throws SQLException {
        String sql = "INSERT INTO orders(order_id,order_date,order_time,customer_id,deliverystatus) VALUES(?,?,?,?,?) ";

        return CrudUtil.execute(sql,entity.getId(),entity.getDate(),entity.getTime(),entity.getCustId(),entity.getStatus());
    }

    @Override
    public boolean update(Order entity) throws SQLException {
        return false;
    }

    @Override
    public ArrayList<Order> getAll() throws SQLException {
        return null;
    }

    @Override
    public Order search(String id) throws SQLException {
        return null;
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return false;
    }

    @Override
    public String generateNextId() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT order_id FROM orders ORDER BY order_id DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("order_id");
            int newCustomerId = Integer.parseInt(id.replace("O00-", "")) + 1;
            return String.format("O00-%03d", newCustomerId);
        } else {
            return "O00-001";
        }
    }
}
