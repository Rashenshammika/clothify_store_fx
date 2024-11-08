package repository.custom.impl;

import entity.TempUserEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Session;
import org.hibernate.query.Query;
import repository.custom.TempUserDao;
import util.HibernateUtil;

import java.util.List;

public class TempUserDaoImpl implements TempUserDao {
    @Override
    public boolean save(TempUserEntity tempUserEntity) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        session.persist(tempUserEntity);
        session.getTransaction().commit();
        session.close();
        return false;
    }

    @Override
    public boolean delete(String id) {
        return false;
    }

    @Override
    public ObservableList<TempUserEntity> getAll() {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        Query<TempUserEntity> query = session.createQuery("FROM TempUserEntity", TempUserEntity.class);
        List<TempUserEntity> tempUserList = query.getResultList();
        session.getTransaction().commit();
        session.close();
        return FXCollections.observableArrayList(tempUserList);
    }

    @Override
    public boolean update(TempUserEntity tempUserEntity) {
        return false;
    }

    @Override
    public TempUserEntity search(String id) {
        return null;
    }
}
