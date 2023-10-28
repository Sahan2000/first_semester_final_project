package lk.ijse.shopManagement.model;

import lk.ijse.shopManagement.db.DbConnection;
import lk.ijse.shopManagement.dto.CartDTO;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class PlaceOrderModel {

    public static boolean placeOrder(String txtOrderId, String txtCustomerId, String txtDeliverId, double new_total, int distance, List<CartDTO> cartDTOList,String status ) throws SQLException {
        try{
            DbConnection.getInstance().getConnection().setAutoCommit(false);
                boolean saveOrder = OrderModel.save(txtOrderId, txtCustomerId, status, LocalDate.now(), LocalTime.now());
                if (saveOrder){
                    System.out.println("Done 1");
                    boolean updateQty = ItemModel.updateQty(cartDTOList);
                    if (updateQty){
                        System.out.println("Done 2");
                        boolean saveOrderDetail = OrderDetailModel.save(txtOrderId, cartDTOList);
                        if (saveOrderDetail){
                            System.out.println("Done 3");
                            if (status.equals("Yes")){
                                boolean save = DeliveryModel.save(txtDeliverId, new_total, distance);
                                if (save){
                                    System.out.println("Done 4");
                                    DbConnection.getInstance().getConnection().commit();
                                    return true;
                                }
                            }else {
                                System.out.println("Done 5");
                                DbConnection.getInstance().getConnection().commit();
                                return true;
                            }
                        }
                    }
                }
            DbConnection.getInstance().getConnection().rollback();
        }finally {
            DbConnection.getInstance().getConnection().setAutoCommit(true);
        }
        return false;
    }
}

