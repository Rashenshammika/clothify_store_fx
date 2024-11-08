package repository;

import repository.custom.impl.*;
import util.DaoType;

public class DaoFactory {
    private static DaoFactory instance;
    private DaoFactory(){}

    public static DaoFactory getInstance() {
        return instance==null?instance=new DaoFactory():instance;
    }

    public <T extends SuperDao>T getDaoType(DaoType type){
        switch (type){
            case TEMPUSER:return (T) new TempUserDaoImpl();
            case USER:return (T) new UserDaoImpl();
            case SUPPLIER:return (T) new SupplierDaoImpl();
            case PRODUCT:return (T) new ProductDaoImpl();
            case ORDER:return (T) new OrderDaoImpl();
            case ORDERDETAILS:return (T) new OrderDetailsDaoImpl();
        }
        return null;

    }
}
