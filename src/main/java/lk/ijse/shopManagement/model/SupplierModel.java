package lk.ijse.shopManagement.model;

import lk.ijse.shopManagement.dto.SupplierDTO;
import lk.ijse.shopManagement.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SupplierModel {
    public static SupplierDTO searchSupplierByName(String name) throws SQLException {
        String sql ="SELECT * FROM supplier WHERE supplier_name = ?";
        ResultSet resultSet = CrudUtil.execute(sql, name);
        if (resultSet.next()){
            String supplier_id = resultSet.getString(1);
            String supplier_name = resultSet.getString(2);
            String supplier_company = resultSet.getString(3);
            String supplier_contact = resultSet.getString(4);
            return new SupplierDTO(supplier_id,supplier_name,supplier_company,supplier_contact);
        }
        return null;
    }
}
