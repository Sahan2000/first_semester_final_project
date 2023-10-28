package lk.ijse.shopManagement.bo.custom.Impl;

import lk.ijse.shopManagement.bo.custom.PlaceOrderBO;
import lk.ijse.shopManagement.dao.DAOFactory;
import lk.ijse.shopManagement.dao.custom.PlaceOrderDAO;
import lk.ijse.shopManagement.dto.DeliveryDTO;
import lk.ijse.shopManagement.dto.PlaceOrderDTO;
import lk.ijse.shopManagement.entity.Delivery;
import lk.ijse.shopManagement.entity.PlaceOrder;

import java.sql.SQLException;

public class PlaceOrderBOImpl implements PlaceOrderBO {
    PlaceOrderDAO placeOrderDAO = (PlaceOrderDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.PLACE_ORDER);
    @Override
    public boolean placeOrder(PlaceOrderDTO placeOrderDTO, DeliveryDTO deliveryDTO) throws SQLException {
        return placeOrderDAO.placeOrder(new PlaceOrder(placeOrderDTO.getId(), placeOrderDTO.getDate(),placeOrderDTO.getTime(),placeOrderDTO.getCustId(), placeOrderDTO.getStatus()),
                new Delivery(deliveryDTO.getId(),deliveryDTO.getDistance(),deliveryDTO.getAmount()));
    }
}
