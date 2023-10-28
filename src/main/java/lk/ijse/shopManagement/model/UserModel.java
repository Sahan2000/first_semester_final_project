package lk.ijse.shopManagement.model;

import lk.ijse.shopManagement.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserModel {

    public static boolean check(String userName, String userPassword,String userPassword1) throws SQLException {
        String sql = "SELECT * FROM user WHERE user_email=?";

        ResultSet resultSet = CrudUtil.execute(sql, userName);

        if (resultSet.next()){
            if(resultSet.getString(3).equals(userPassword)|| resultSet.getString(3).equals(userPassword1)){
                return true;
            }
        }
        return false;
    }
}
