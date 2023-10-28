package lk.ijse.shopManagement.dao.custom;

import lk.ijse.shopManagement.dao.CrudDAO;
import lk.ijse.shopManagement.entity.Delivery;
import lk.ijse.shopManagement.entity.PlaceOrder;

import java.sql.SQLException;

public interface PlaceOrderDAO extends CrudDAO<PlaceOrder> {
    boolean placeOrder(PlaceOrder placeOrder, Delivery delivery) throws SQLException;
}
