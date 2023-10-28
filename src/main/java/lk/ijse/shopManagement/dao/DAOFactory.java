package lk.ijse.shopManagement.dao;

import lk.ijse.shopManagement.dao.custom.Impl.*;

public class DAOFactory {
    private static DAOFactory daoFactory;

    private DAOFactory(){}

    public static DAOFactory getDaoFactory(){
        return (daoFactory == null) ? daoFactory = new DAOFactory() : daoFactory;
    }

    public enum DAOTypes{
        CUSTOMER,EMPLOYEE,SUPPLIER,ITEM,SUPPLIER_ORDER,SUPPLIER_ORDER_DETAILS,HAVEPAYINVOICE,INVOICE,ORDER,DELIVERY,PLACE_ORDER
    }

    public SuperDAO getDAO(DAOTypes daoTypes){
        switch (daoTypes){
            case CUSTOMER:
                return new CustomerDAOImpl();
            case EMPLOYEE:
                return new EmployeeDAOImpl();
            case SUPPLIER:
                return new SupplierDAOImpl();
            case ITEM:
                return new ItemDAOImpl();
            case SUPPLIER_ORDER:
                return new SupplierOrderDAOImpl();
            case SUPPLIER_ORDER_DETAILS:
                return new SupplierOrderDetailsDAOImpl();
            case INVOICE:
                return new InvoiceDAOImpl();
            case HAVEPAYINVOICE:
                return new HavePayInvoiceDAOImpl();
            case ORDER:
                return new OrderDAOImpl();
            case DELIVERY:
                return new DeliveryDAOImpl();
            case PLACE_ORDER:
                return new PlaceOrderDAOImpl();
            default:
                return null;
        }
    }
}
