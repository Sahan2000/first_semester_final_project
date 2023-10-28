package lk.ijse.shopManagement.bo.custom;

import lk.ijse.shopManagement.bo.SuperBO;
import lk.ijse.shopManagement.dto.DeliveryDTO;
import lk.ijse.shopManagement.dto.PlaceOrderDTO;

import java.sql.SQLException;

public interface PlaceOrderBO extends SuperBO {
    boolean placeOrder(PlaceOrderDTO placeOrderDTO, DeliveryDTO deliveryDTO) throws SQLException;
}
