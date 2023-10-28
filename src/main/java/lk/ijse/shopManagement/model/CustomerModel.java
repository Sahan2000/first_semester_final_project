package lk.ijse.shopManagement.model;

import lk.ijse.shopManagement.dto.CustomerDTO;
import lk.ijse.shopManagement.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerModel {


    public static CustomerDTO searchCustomerNIC(String nic) throws SQLException {
        String sql = "SELECT * FROM customer WHERE customer_NIC = ?";
        ResultSet resultSet = CrudUtil.execute(sql,nic);
        if (resultSet.next()){
            String customer_id = resultSet.getString(1);
            String customer_name = resultSet.getString(2);
            String customerContact = resultSet.getString(3);
            String customer_address = resultSet.getString(4);
            String customer_NIC = resultSet.getString(5);
            return new CustomerDTO(customer_id,customer_name,customerContact,customer_address,customer_NIC);
        }
        return null;
    }

    public static int totalCount() throws SQLException {
        String sql = "SELECT COUNT(customer_id) FROM customer ";
        ResultSet resultSet = CrudUtil.execute(sql);
        while (resultSet.next()){
            return resultSet.getInt(1);
        }
        return 0;
    }


}
