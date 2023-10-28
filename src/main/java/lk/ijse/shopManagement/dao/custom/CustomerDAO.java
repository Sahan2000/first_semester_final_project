package lk.ijse.shopManagement.dao.custom;

import lk.ijse.shopManagement.dao.CrudDAO;
import lk.ijse.shopManagement.entity.Customer;

import java.sql.SQLException;

public interface CustomerDAO extends CrudDAO<Customer> {
    Customer searchCustomerNIC(String nic) throws SQLException;
}
