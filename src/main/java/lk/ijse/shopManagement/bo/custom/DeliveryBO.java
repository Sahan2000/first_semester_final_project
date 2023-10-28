package lk.ijse.shopManagement.bo.custom;

import lk.ijse.shopManagement.bo.SuperBO;

import java.sql.SQLException;

public interface DeliveryBO extends SuperBO {
    String generateNextDeliveryId() throws SQLException;
}
