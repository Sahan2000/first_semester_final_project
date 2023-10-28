package lk.ijse.shopManagement.bo.custom.Impl;

import lk.ijse.shopManagement.bo.custom.DeliveryBO;
import lk.ijse.shopManagement.dao.DAOFactory;
import lk.ijse.shopManagement.dao.custom.DeliveryDAO;

import java.sql.SQLException;

public class DeliveryBOImpl implements DeliveryBO {

    DeliveryDAO deliveryDAO = (DeliveryDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.DELIVERY);
    @Override
    public String generateNextDeliveryId() throws SQLException {
        return deliveryDAO.generateNextId();
    }
}
