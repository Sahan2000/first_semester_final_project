package lk.ijse.shopManagement.model;

import lk.ijse.shopManagement.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

public class OrderModel {
    public static String generateNextOrderId() throws SQLException {
        String sql = "SELECT order_id FROM orders ORDER BY order_id DESC LIMIT 1";
        ResultSet resultSet = CrudUtil.execute(sql);
        if (resultSet.next()){
            String id = resultSet.getString(1);
            return splitOrderId(id);
        }
        return splitOrderId(null);
    }

    public static String splitOrderId(String id){
        if (id != null){
            String[] strings = id.split("D");
            int index = Integer.parseInt(strings[1]);
            index++;
            String generated = String.format("D%03d", index);
            return generated ;
        }
        return "D001";
    }


    public static boolean save(String txtOrderId, String txtCustomerId, String status, LocalDate now, LocalTime now1) throws SQLException {
        String sql = "INSERT INTO orders(order_id,order_date,order_time,customer_id,deliverystatus) VALUES(?,?,?,?,?) ";

        return CrudUtil.execute(sql,txtOrderId,now,now1,txtCustomerId,status);

    }

    public static int orderCount() throws SQLException {
        String sql = "SELECT COUNT(order_id) FROM orders";
        ResultSet resultSet = CrudUtil.execute(sql);
        while (resultSet.next()){
            return resultSet.getInt(1);
        }
        return 0;
    }


    public static Integer[] getMonthlySalesValues() throws SQLException {
        Integer[] data = new Integer[12];
        int jan = 0;
        int feb = 0;
        int mar = 0;
        int apr = 0;
        int may = 0;
        int jun = 0;
        int jul = 0;
        int aug = 0;
        int sep = 0;
        int oct = 0;
        int nov = 0;
        int dec = 0;

        String sql = "SELECT MONTH(order_date), COUNT(order_id) FROM orders GROUP BY MONTH(order_date)";
        ResultSet resultSet = CrudUtil.execute(sql);
        while (resultSet.next()) {
            switch (resultSet.getString(1)) {
                case "1":
                    jan = Integer.parseInt(resultSet.getString(2));
                    break;
                case "2":
                    feb = Integer.parseInt(resultSet.getString(2));
                    break;
                case "3":
                    mar = Integer.parseInt(resultSet.getString(2));
                    break;
                case "4":
                    apr = Integer.parseInt(resultSet.getString(2));
                    break;
                case "5":
                    may = Integer.parseInt(resultSet.getString(2));
                    break;
                case "6":
                    jun = Integer.parseInt(resultSet.getString(2));
                    break;
                case "7":
                    jul = Integer.parseInt(resultSet.getString(2));
                    break;
                case "8":
                    aug = Integer.parseInt(resultSet.getString(2));
                    break;
                case "9":
                    sep = Integer.parseInt(resultSet.getString(2));
                    break;
                case "10":
                    oct = Integer.parseInt(resultSet.getString(2));
                    break;
                case "11":
                    nov = Integer.parseInt(resultSet.getString(2));
                    break;
                case "12":
                    dec = Integer.parseInt(resultSet.getString(2));
                    break;

            }
        }
        data[0] = jan;
        data[1] = feb;
        data[2] = mar;
        data[3] = apr;
        data[4] = may;
        data[5] = jun;
        data[6] = jul;
        data[7] = aug;
        data[8] = sep;
        data[9] = oct;
        data[10] = nov;
        data[11] = dec;
        return data;
    }
}
