package lk.ijse.shopManagement.model;

import lk.ijse.shopManagement.db.DbConnection;
import lk.ijse.shopManagement.dto.CartDTO;
import lk.ijse.shopManagement.dto.DetailDTO;
import lk.ijse.shopManagement.dto.ItemDTO;
import lk.ijse.shopManagement.util.CrudUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ItemModel {




//    public static Item search(String id) throws SQLException {
//        String sql = " select i.item_code , i.item_name,i.item_sell_price , i.item_get_price, i.item_type , i.supplier_id , i.item_qty ,s.supplier_name from item i inner join supplier s on i.supplier_id = s.supplier_Id where i.item_code = ?";
//        ResultSet resultSet = CrudUtil.execute(sql, id);
//        if (resultSet.next()){
//            String item_code = resultSet.getString(1);
//            String item_name = resultSet.getString(2);
//            double item_sell_price = resultSet.getDouble(3);
//            double item_get_price = resultSet.getDouble(4);
//            String item_type = resultSet.getString(5);
//            String supplier_name = resultSet.getString(6);
//            int item_qty = resultSet.getInt(7);
//            return new Item(item_code,item_name,item_type,supplier_name,item_sell_price,item_get_price,item_qty);
//        }
//        return null;
//    }



    public static Optional<ItemDTO> searchByPk(String id) throws SQLException {
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
            return  Optional.of(new ItemDTO(item_code, item_name, sell_price, get_price, item_type, sup_id, item_qty));
        }
        return Optional.empty();
    }





    public static List<ItemDTO> getitemBySupplierID(String id) throws SQLException {
        String sql = "SELECT * FROM item WHERE supplier_id= ?";
        ResultSet resultSet = CrudUtil.execute(sql,id);
        List<ItemDTO> data = new ArrayList<>();
        while (resultSet.next()) {
            data.add(new ItemDTO(
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

    public static List<String> getCodes() throws SQLException {
        String sql = "SELECT * FROM item";
        ResultSet resultSet = CrudUtil.execute(sql);
        List<String> data = new ArrayList<>();
        while (resultSet.next()) {
            data.add(resultSet.getString(1));
        }
        return data;
    }

    public static boolean updateQty(List<CartDTO> cartDTOList) throws SQLException {
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

    public static int itemCount() throws SQLException {
        String sql = "SELECT item_code FROM item";
        ResultSet resultSet = CrudUtil.execute(sql);
        int count=0;
        while (resultSet.next()){
            count++;
        }
        return count;
    }


    public static int getHomeValue() throws SQLException {
        String sql = "SELECT COUNT(item_code) FROM item WHERE item_type = 'Home appliances'";
        ResultSet resultSet = CrudUtil.execute(sql);
        if (resultSet.next()){
            return Integer.parseInt(resultSet.getString(1));
        }
        return 0;
    }

    public static int getKitchenValue() throws SQLException {
        String sql = "SELECT COUNT(item_code) FROM item WHERE item_type = 'Kitchen appliances'";
        ResultSet resultSet = CrudUtil.execute(sql);
        if (resultSet.next()){
            return Integer.parseInt(resultSet.getString(1));
        }
        return 0;
    }

    public static int getWashingMachineValue() throws SQLException {
        String sql = "SELECT COUNT(item_code) FROM item WHERE item_type = 'Washing machine'";
        ResultSet resultSet = CrudUtil.execute(sql);
        if (resultSet.next()){
            return Integer.parseInt(resultSet.getString(1));
        }
        return 0;
    }

    public static int getRefrigeratorValue() throws SQLException {
        String sql = "SELECT COUNT(item_code) FROM item WHERE item_type = 'Refrigerator'";
        ResultSet resultSet = CrudUtil.execute(sql);
        if (resultSet.next()){
            return Integer.parseInt(resultSet.getString(1));
        }
        return 0;
    }

    public static int getAirConditionValue() throws SQLException {
        String sql = "SELECT COUNT(item_code) FROM item WHERE item_type = 'Air conditioners'";
        ResultSet resultSet = CrudUtil.execute(sql);
        if (resultSet.next()){
            return Integer.parseInt(resultSet.getString(1));
        }
        return 0;
    }

    public static int getTelevisionValue() throws SQLException {
        String sql = "SELECT COUNT(item_code) FROM item WHERE item_type = 'Television'";
        ResultSet resultSet = CrudUtil.execute(sql);
        if (resultSet.next()){
            return Integer.parseInt(resultSet.getString(1));
        }
        return 0;
    }
    public static boolean updateLoadQty(ArrayList<DetailDTO> details) throws SQLException {
        for (DetailDTO dto : details) {
            if (!updateQty(dto)) {
                return false;
            }
        }
        return true;
    }

    private static boolean updateQty(DetailDTO detail) throws SQLException {
        Connection con = DbConnection.getInstance().getConnection();
        String sql = "UPDATE item SET item_qty = (item_qty + ?) WHERE item_code = ?";
        return CrudUtil.execute(sql, detail.getQty(),detail.getCode());
    }

    public static String generateNextOrderId() throws SQLException {
        String sql = "SELECT item_code FROM item ORDER BY item_code DESC LIMIT 1";
        ResultSet resultSet = CrudUtil.execute(sql);
        if (resultSet.next()){
            String id = resultSet.getString(1);
            return splitOrderId(id);
        }
        return splitOrderId(null);
    }

    private static String splitOrderId(String id) {
        if (id != null){
            String[] strings = id.split("I");
            int index = Integer.parseInt(strings[1]);
            index++;
            String generated = String.format("I%03d", index);
            return generated ;
        }
        return "I001";
    }
}
