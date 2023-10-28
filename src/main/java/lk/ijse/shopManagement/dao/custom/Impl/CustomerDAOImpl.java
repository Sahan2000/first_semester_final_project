package lk.ijse.shopManagement.dao.custom.Impl;

import lk.ijse.shopManagement.dao.custom.CustomerDAO;
import lk.ijse.shopManagement.entity.Customer;
import lk.ijse.shopManagement.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerDAOImpl implements CustomerDAO {
    @Override
    public boolean save(Customer customer) throws SQLException {
        String sql = "INSERT INTO customer(customer_id,customer_name,customer_address, customer_contact,customer_NIC) VALUES (?,?,?,?,?)";
        return CrudUtil.execute(sql,customer.getId(),customer.getName(),customer.getAddress(),customer.getContact(),customer.getNIC());
    }

    @Override
    public boolean update(Customer customer) throws SQLException {
        String sql = "UPDATE customer SET customer_name =?, customer_contact =?, customer_address =?, customer_NIC =? WHERE customer_id = ?";
        return CrudUtil.execute(sql,customer.getName(),customer.getAddress(),customer.getContact(),customer.getNIC(),customer.getId());
    }

    @Override
    public ArrayList<Customer> getAll() throws SQLException {
        String sql = "SELECT * FROM customer";
        ArrayList<Customer> data = new ArrayList<>();
        ResultSet resultSet = CrudUtil.execute(sql);
        while (resultSet.next()){
            data.add(new Customer(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5)
            ));
        }
        return data;
    }

    @Override
    public Customer search(String id) throws SQLException {
        String sql = "SELECT * FROM customer WHERE customer_id = ?";
        ResultSet result = CrudUtil.execute(sql, id);
        if (result.next()){
            String customer_id = result.getString(1);
            String customer_name = result.getString(2);
            String customerContact = result.getString(3);
            String customer_address = result.getString(4);
            String customer_NIC = result.getString(5);
            return new Customer(customer_id,customer_name,customerContact,customer_address,customer_NIC);
        }
        return null;
    }

    @Override
    public boolean delete(String id) throws SQLException {
        String sql = "DELETE FROM customer WHERE customer_id = ?";
        return CrudUtil.execute(sql,id);
    }

    @Override
    public String generateNextId() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT customer_id FROM customer ORDER BY customer_id DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("customer_id");
            int newCustomerId = Integer.parseInt(id.replace("C00-", "")) + 1;
            return String.format("C00-%03d", newCustomerId);
        } else {
            return "C00-001";
        }
    }

    @Override
    public Customer searchCustomerNIC(String nic) throws SQLException {
        System.out.println("Hiiiiiiii");
        String sql = "SELECT * FROM customer WHERE customer_NIC = ?";
        ResultSet resultSet = CrudUtil.execute(sql,nic);
        if (resultSet.next()){
            String customer_id = resultSet.getString(1);
            String customer_name = resultSet.getString(2);
            String customerContact = resultSet.getString(3);
            String customer_address = resultSet.getString(4);
            String customer_NIC = resultSet.getString(5);
//            System.out.println(customer_id+""+customer_name+""+customerContact+""+customer_address+""+customer_NIC);
            return new Customer(customer_id,customer_name,customer_address,customerContact,customer_NIC);
        }
        return null;


    }
}

