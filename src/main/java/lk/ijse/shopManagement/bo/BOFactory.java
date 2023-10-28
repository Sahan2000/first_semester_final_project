package lk.ijse.shopManagement.bo;

import lk.ijse.shopManagement.bo.custom.Impl.*;

public class BOFactory {
    private static BOFactory boFactory;

    private BOFactory(){}

    public static BOFactory getBoFactory(){
        return (boFactory == null) ? boFactory = new BOFactory() : boFactory;
    }

    public enum BOTypes{
        CUSTOMER,EMPLOYEE,SUPPLIER,ITEM,SUPPLIER_ORDER,ORDER,DELIVERY,PLACE_ORDER
    }

    public SuperBO getBO(BOTypes boTypes){
        switch (boTypes){
            case CUSTOMER:
                return new CustomerBOImpl();
            case EMPLOYEE:
                return new EmployeeBOImpl();
            case SUPPLIER:
                return new SupplierBOImpl();
            case ITEM:
                return new ItemBOImpl();
            case SUPPLIER_ORDER:
                return new SupplierOrderBOImpl();
            case ORDER:
                return new OrderBOImpl();
            case DELIVERY:
                return new DeliveryBOImpl();
            case PLACE_ORDER:
                return new PlaceOrderBOImpl();
            default:
                return null;
        }
    }
}
