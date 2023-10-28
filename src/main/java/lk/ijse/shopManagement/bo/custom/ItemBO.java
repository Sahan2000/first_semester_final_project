package lk.ijse.shopManagement.bo.custom;

import lk.ijse.shopManagement.bo.SuperBO;
import lk.ijse.shopManagement.dto.ItemDTO;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface ItemBO extends SuperBO {
    List<ItemDTO> getAllItem() throws SQLException;

    boolean deleteItem(String itemCode) throws SQLException;

    ItemDTO searchItem(String code) throws SQLException;

    String generateNextItemId() throws SQLException;

    boolean saveItem(ItemDTO itemDTO) throws SQLException;

    boolean updateItem(ItemDTO itemDTO) throws SQLException;

    List<ItemDTO> getitemBySupplierID(String text) throws SQLException;

    Optional<ItemDTO> searchByPk(String id) throws SQLException;

    List<String> getCodes() throws SQLException;
}
