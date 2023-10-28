package lk.ijse.shopManagement.bo.custom;

import lk.ijse.shopManagement.bo.SuperBO;
import lk.ijse.shopManagement.dto.SupplierDTO;

import java.sql.SQLException;
import java.util.List;

public interface SupplierBO extends SuperBO {
    boolean saveSupplier(SupplierDTO supplierDTO) throws SQLException;

    boolean updateSupplier(SupplierDTO supplierDTO) throws SQLException;

    boolean deleteSupplier(String id) throws SQLException;

    List<SupplierDTO> getAllSupplier() throws SQLException;

    SupplierDTO searchSupplier(String id) throws SQLException;

    String generateNextSupplierId() throws SQLException;

    List<String> getSupplierName() throws SQLException;

    String getSupplierId(String supplier) throws SQLException;


}
