package lk.ijse.shopManagement.dao.custom.Impl;

import lk.ijse.shopManagement.dao.DAOFactory;
import lk.ijse.shopManagement.dao.custom.DeliveryDAO;
import lk.ijse.shopManagement.dao.custom.ItemDAO;
import lk.ijse.shopManagement.dao.custom.OrderDAO;
import lk.ijse.shopManagement.dao.custom.PlaceOrderDAO;
import lk.ijse.shopManagement.db.DbConnection;
import lk.ijse.shopManagement.entity.Delivery;
import lk.ijse.shopManagement.entity.Order;
import lk.ijse.shopManagement.entity.PlaceOrder;

import java.sql.SQLException;
import java.util.ArrayList;

import static lk.ijse.shopManagement.controller.DeliveryManageFormController.cartDTOList;

public class PlaceOrderDAOImpl implements PlaceOrderDAO {

    OrderDAO orderDAO = (OrderDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDER);

    ItemDAO itemDAO =(ItemDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ITEM);

    DeliveryDAO deliveryDAO = (DeliveryDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.DELIVERY);
    @Override
    public boolean save(PlaceOrder entity) throws SQLException {
        return false;
    }

    @Override
    public boolean update(PlaceOrder entity) throws SQLException {
        return false;
    }

    @Override
    public ArrayList<PlaceOrder> getAll() throws SQLException {
        return null;
    }

    @Override
    public PlaceOrder search(String id) throws SQLException {
        return null;
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return false;
    }

    @Override
    public String generateNextId() throws SQLException {
        return null;
    }

    @Override
    public boolean placeOrder(PlaceOrder placeOrder, Delivery delivery) throws SQLException {
        try{
            DbConnection.getInstance().getConnection().setAutoCommit(false);
            boolean saveOrder = orderDAO.save(new Order(placeOrder.getId(), placeOrder.getDate(),placeOrder.getTime(), placeOrder.getCustId(), placeOrder.getStatus()));
            if (saveOrder){
                System.out.println("Done 1");
                boolean updateqty = itemDAO.updateqty(cartDTOList);
                if (updateqty){
                    System.out.println("Done 2");
                    boolean saveoditem = itemDAO.saveoditem(placeOrder.getId(), cartDTOList);
                    if (saveoditem){
                        System.out.println("Done 3");
                        String status = placeOrder.getStatus();
                        if (status.equals("Yes")){
                            boolean savedelivery = deliveryDAO.savedelivery(delivery);
                            if (savedelivery){
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
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DbConnection.getInstance().getConnection().setAutoCommit(true);
        }
        return false;
    }
}
