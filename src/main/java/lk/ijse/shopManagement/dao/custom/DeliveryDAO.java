package lk.ijse.shopManagement.dao.custom;

import lk.ijse.shopManagement.dao.CrudDAO;
import lk.ijse.shopManagement.entity.Delivery;

import java.sql.SQLException;

public interface DeliveryDAO extends CrudDAO<Delivery> {
    boolean savedelivery(Delivery delivery) throws SQLException;
}
