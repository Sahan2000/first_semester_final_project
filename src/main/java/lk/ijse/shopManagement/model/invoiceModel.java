package lk.ijse.shopManagement.model;

import lk.ijse.shopManagement.dto.InvoiceDTO;
import lk.ijse.shopManagement.util.CrudUtil;

import java.sql.SQLException;

public class invoiceModel {
    public static boolean save(InvoiceDTO invoice) throws SQLException {
        String sql = "INSERT INTO invoice VALUES (?,?,?,?)";
        return CrudUtil.execute(sql, invoice.getId(),invoice.getDate(),invoice.getSupid(),invoice.getAmount());
    }
    public static boolean savehavePay(InvoiceDTO invoice) throws SQLException {
        String sql = "INSERT INTO havepay_invoice VALUES (?,?,?,?)";
        return CrudUtil.execute(sql, invoice.getId(),invoice.getDate(),invoice.getSupid(),invoice.getAmount());
    }
}
