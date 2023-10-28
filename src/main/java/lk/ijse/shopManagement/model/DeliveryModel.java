package lk.ijse.shopManagement.model;

import lk.ijse.shopManagement.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DeliveryModel {
    public static String generateNextOrderId() throws SQLException {
        String sql = "SELECT delivery_id FROM delivery ORDER BY delivery_id DESC LIMIT 1";
        ResultSet resultSet = CrudUtil.execute(sql);
        if (resultSet.next()){
            String id = resultSet.getString(1);
            return splitOrderId(id);
        }
        return splitOrderId(null);
    }
    public static String splitOrderId(String id){
        if (id != null){
            String[] strings = id.split("P");
            int index = Integer.parseInt(strings[1]);
            index++;
            String generated = String.format("P%03d",index);
            return generated;
        }
        return "P001";
    }

    public static boolean save(String txtDeliverId, double new_total, int distance) throws SQLException {
        String sql = "INSERT INTO delivery(delivery_id,distance,amount) VALUES(?,?,?)";
        return CrudUtil.execute(sql,txtDeliverId,distance,new_total);
    }
}
