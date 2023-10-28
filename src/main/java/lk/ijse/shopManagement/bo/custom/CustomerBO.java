package lk.ijse.shopManagement.bo.custom;

import lk.ijse.shopManagement.bo.SuperBO;
import lk.ijse.shopManagement.dto.CustomerDTO;

import java.sql.SQLException;
import java.util.List;

public interface CustomerBO extends SuperBO {
    boolean saveCustomer(CustomerDTO customerDTO) throws SQLException;

    boolean updateCustomer(CustomerDTO customerDTO) throws SQLException;

    List<CustomerDTO> getAllCustomer() throws SQLException;

    CustomerDTO searchCustomerId(String id) throws SQLException;

    boolean deleteCustomer(String id) throws SQLException;

    String generateNextCustomerId() throws SQLException;

    CustomerDTO searchCustomerNIC(String nic) throws SQLException;
}
