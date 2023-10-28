package lk.ijse.shopManagement.bo.custom.Impl;

import lk.ijse.shopManagement.bo.custom.OrderBO;
import lk.ijse.shopManagement.dao.DAOFactory;
import lk.ijse.shopManagement.dao.custom.OrderDAO;

import java.sql.SQLException;

public class OrderBOImpl implements OrderBO {

    OrderDAO orderDAO = (OrderDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDER);
    @Override
    public String generateNextOrderId() throws SQLException {
        return orderDAO.generateNextId();
    }
}
