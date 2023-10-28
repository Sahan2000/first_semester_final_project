package lk.ijse.shopManagement.model;

import lk.ijse.shopManagement.dto.CartDTO;
import lk.ijse.shopManagement.util.CrudUtil;

import java.sql.SQLException;
import java.util.List;

public class OrderDetailModel {
        public static boolean save(String oId, List<CartDTO> cartDTOList) throws SQLException {
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
