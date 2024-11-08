package repository.custom.impl;

import entity.TempUserEntity;
import entity.UserEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Session;
import org.hibernate.query.Query;
import repository.custom.UserDao;
import util.HibernateUtil;

import java.util.List;

public class UserDaoImpl implements UserDao {
    @Override
    public boolean save(UserEntity userEntity) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        session.persist(userEntity);
        session.getTransaction().commit();
        session.close();
        return false;
    }

    @Override
    public boolean delete(String id) {
        Session session = HibernateUtil.getSession();
        try {
            session.beginTransaction();
            UserEntity userEntity = session.get(UserEntity.class, id);

            if (userEntity != null) {
                session.remove(userEntity);
                session.getTransaction().commit();
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
    }


    @Override
    public ObservableList<UserEntity> getAll() {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        Query<UserEntity> query = session.createQuery("FROM UserEntity", UserEntity.class);
        List<UserEntity> userList = query.getResultList();
        session.getTransaction().commit();
        session.close();
        return FXCollections.observableArrayList(userList);
    }

    @Override
    public boolean update(UserEntity userEntity) {
        Session session = HibernateUtil.getSession();
        try {
            session.beginTransaction();
            session.update(userEntity);
            session.getTransaction().commit();
            return true;

        } catch (Exception e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public UserEntity search(String id) {
        Session session = HibernateUtil.getSession();
        try {
            session.beginTransaction();
            UserEntity userEntity = session.get(UserEntity.class, id);
            session.getTransaction().commit();
            return userEntity;
        } catch (Exception e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }

}
