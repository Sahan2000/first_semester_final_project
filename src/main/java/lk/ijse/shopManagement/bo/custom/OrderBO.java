package lk.ijse.shopManagement.bo.custom;

import lk.ijse.shopManagement.bo.SuperBO;

import java.sql.SQLException;

public interface OrderBO extends SuperBO {
    String generateNextOrderId() throws SQLException;
}
