package repository.custom.impl;

import entity.OrderDetailsEntity;
import javafx.collections.ObservableList;
import org.hibernate.Session;
import repository.custom.OrderDetailsDao;
import util.HibernateUtil;

public class OrderDetailsDaoImpl implements OrderDetailsDao {
    @Override
    public boolean save(OrderDetailsEntity orderDetailsEntity) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        session.persist(orderDetailsEntity);
        session.getTransaction().commit();
        session.close();
        return false;
    }

    @Override
    public boolean delete(String id) {
        return false;
    }

    @Override
    public ObservableList<OrderDetailsEntity> getAll() {
        return null;
    }

    @Override
    public boolean update(OrderDetailsEntity orderDetailsEntity) {
        return false;
    }

    @Override
    public OrderDetailsEntity search(String id) {
        return null;
    }
}
