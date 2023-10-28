package lk.ijse.shopManagement.bo.custom;

import lk.ijse.shopManagement.bo.SuperBO;
import lk.ijse.shopManagement.dto.DetailDTO;
import lk.ijse.shopManagement.dto.InvoiceDTO;
import lk.ijse.shopManagement.dto.SupplierOrderDTO;

import java.sql.SQLException;

public interface SupplierOrderBO extends SuperBO {
    String generateNextLoadId() throws SQLException;

    boolean placeSupplierOrder(SupplierOrderDTO supplierOrder, InvoiceDTO invoice, DetailDTO detailDTO) throws SQLException;
}
