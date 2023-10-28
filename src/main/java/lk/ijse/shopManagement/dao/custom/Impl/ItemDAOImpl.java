package lk.ijse.shopManagement.dao.custom.Impl;

import lk.ijse.shopManagement.dao.custom.ItemDAO;
import lk.ijse.shopManagement.db.DbConnection;
import lk.ijse.shopManagement.dto.CartDTO;
import lk.ijse.shopManagement.entity.Details;
import lk.ijse.shopManagement.entity.Item;
import lk.ijse.shopManagement.util.CrudUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ItemDAOImpl implements ItemDAO {

    @Override
    public boolean save(Item entity) throws SQLException {
        String sql = "INSERT INTO item(item_code,item_name,item_sell_price,item_get_price,item_type,supplier_id,item_qty) VALUES (?,?,?,?,?,?,?)";
        return CrudUtil.execute(sql, entity.getItemCode(),entity.getItemName(),entity.getSellPrice(),entity.getGetPrice(),entity.getItemType(),entity.getSupplier(),entity.getQuantity());
    }

    @Override
    public boolean update(Item entity) throws SQLException {
        String sql = "UPDATE item SET item_name=?, item_sell_price=?, item_get_price=?, item_type=?, supplier_id=?, item_qty=? WHERE item_code=?";
        return CrudUtil.execute(sql, entity.getItemCode(),entity.getItemName(),entity.getSellPrice(),entity.getGetPrice(),entity.getItemType(),entity.getSupplier(),entity.getQuantity());
    }

    @Override
    public ArrayList<Item> getAll() throws SQLException {
        String sql = "SELECT * FROM item";
        ResultSet resultSet = CrudUtil.execute(sql);
        ArrayList<Item> data = new ArrayList<>();

        while (resultSet.next()) {
            data.add(new Item(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getDouble(3),
                    resultSet.getDouble(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getInt(7)
            ));
        }
        return data;
    }

    @Override
    public Item search(String id) throws SQLException {
        String sql = "select * from item where item_code=? ";
        ResultSet resultSet = CrudUtil.execute(sql, id);
        if (resultSet.next()) {
            String item_code = resultSet.getString(1);
            String item_name = resultSet.getString(2);
            double sell_price = resultSet.getDouble(3);
            double get_price = resultSet.getDouble(4);
            String item_type = resultSet.getString(5);
            String sup_id = resultSet.getString(6);
            int item_qty = resultSet.getInt(7);
            return new Item(item_code, item_name, sell_price, get_price, item_type, sup_id, item_qty);
        }
        return null;
    }

    @Override
    public boolean delete(String id) throws SQLException {
        String sql = "DELETE FROM item WHERE item_code = ?";
        return CrudUtil.execute(sql, id);
    }

    @Override
    public String generateNextId() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT Item_code FROM item ORDER BY item_code DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("item_code");
            int newCustomerId = Integer.parseInt(id.replace("I00-", "")) + 1;
            return String.format("I00-%03d", newCustomerId);
        } else {
            return "I00-001";
        }
    }

    @Override
    public List<Item> getitemBySupplierID(String text) throws SQLException {
        String sql = "SELECT * FROM item WHERE supplier_id= ?";
        ResultSet resultSet = CrudUtil.execute(sql,text);
        List<Item> data = new ArrayList<>();
        while (resultSet.next()) {
            data.add(new Item(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getDouble(3),
                    resultSet.getDouble(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getInt(7)
            ));
        }
        return data;
    }

    @Override
    public Optional<Item> searchByPk(String id) throws SQLException {
        String sql = "select * from item where item_code=? ";
        ResultSet resultSet = CrudUtil.execute(sql, id);
        if (resultSet.next()) {
            String item_code = resultSet.getString(1);
            String item_name = resultSet.getString(2);
            double sell_price = resultSet.getDouble(3);
            double get_price = resultSet.getDouble(4);
            String item_type = resultSet.getString(5);
            String sup_id = resultSet.getString(6);
            int item_qty = resultSet.getInt(7);
            return  Optional.of(new Item(item_code, item_name, sell_price, get_price, item_type, sup_id, item_qty));
        }
        return Optional.empty();
    }

    @Override
    public boolean updateQty(Details details) throws SQLException {
        String sql = "UPDATE item SET item_qty = (item_qty + ?) WHERE item_code = ?";
        return CrudUtil.execute(sql, details.getQty(),details.getCode());
    }

    @Override
    public boolean updateqty(List<CartDTO> cartDTOList) throws SQLException {
        for (CartDTO dto : cartDTOList) {
            if (!updateQty(dto)) {
                return false;
            }
        }
        return true;
    }

    private static boolean updateQty(CartDTO dto) throws SQLException {
        Connection con = DbConnection.getInstance().getConnection();
        String sql = "UPDATE item SET item_qty = (item_qty - ?) WHERE item_code = ?";
        return CrudUtil.execute(sql, dto.getItem_qty(), dto.getItem_code());
    }

    public  boolean saveoditem(String oId, List<CartDTO> cartDTOList) throws SQLException {
        for(CartDTO dto :  cartDTOList) {
            if(!save(oId, dto)) {
                return false;
            }
        }
        return true;
    }

    private static boolean save(String oId, CartDTO dto) throws SQLException {
        String sql = "INSERT INTO order_details(item_code,order_id, quantity, amount) VALUES (?, ?, ?, ?)";
        return CrudUtil.execute(sql,dto.getItem_code(),oId,dto.getItem_qty(),dto.getUnit_price());

    }
}
