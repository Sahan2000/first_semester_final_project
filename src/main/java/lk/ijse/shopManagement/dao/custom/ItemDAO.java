package lk.ijse.shopManagement.dao.custom;

import lk.ijse.shopManagement.dao.CrudDAO;
import lk.ijse.shopManagement.dto.CartDTO;
import lk.ijse.shopManagement.entity.Details;
import lk.ijse.shopManagement.entity.Item;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface ItemDAO extends CrudDAO<Item> {
    List<Item> getitemBySupplierID(String text) throws SQLException;

    Optional<Item> searchByPk(String id) throws SQLException;

    boolean updateQty(Details details) throws SQLException;

    boolean updateqty(List<CartDTO> cartDTOList) throws SQLException;

    boolean saveoditem(String oId, List<CartDTO> cartDTOList) throws SQLException;
}
