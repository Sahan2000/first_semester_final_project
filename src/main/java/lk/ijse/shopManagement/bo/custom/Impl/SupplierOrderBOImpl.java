package lk.ijse.shopManagement.bo.custom.Impl;

import lk.ijse.shopManagement.bo.custom.SupplierOrderBO;
import lk.ijse.shopManagement.dao.DAOFactory;
import lk.ijse.shopManagement.dao.custom.SupplierOrderDAO;
import lk.ijse.shopManagement.dto.DetailDTO;
import lk.ijse.shopManagement.dto.InvoiceDTO;
import lk.ijse.shopManagement.dto.SupplierOrderDTO;
import lk.ijse.shopManagement.entity.Details;
import lk.ijse.shopManagement.entity.Invoice;
import lk.ijse.shopManagement.entity.SupplierOrder;

import java.sql.SQLException;

public class SupplierOrderBOImpl implements SupplierOrderBO {
    SupplierOrderDAO supplierOrderDAO = (SupplierOrderDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.SUPPLIER_ORDER);
    @Override
    public String generateNextLoadId() throws SQLException {
        return supplierOrderDAO.generateNextId();
    }

    @Override
    public boolean placeSupplierOrder(SupplierOrderDTO supplierOrder, InvoiceDTO invoice, DetailDTO detailDTO) throws SQLException {
        return supplierOrderDAO.placeSupplierOrder(new SupplierOrder(supplierOrder.getSuo_order_id(),supplierOrder.getDate(),supplierOrder.getSupplierid(),supplierOrder.getAmount(),supplierOrder.getStatus()),new Invoice(invoice.getId(),invoice.getDate(),invoice.getSupid(),invoice.getAmount()), new Details(detailDTO.getSo_id(),detailDTO.getCode(),detailDTO.getAmount(),detailDTO.getQty()));
    }
}
