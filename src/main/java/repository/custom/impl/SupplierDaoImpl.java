package repository.custom.impl;

import entity.SupplierEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Session;
import org.hibernate.query.Query;
import repository.custom.SupplierDao;
import util.HibernateUtil;

import java.util.List;

public class SupplierDaoImpl implements SupplierDao {
    @Override
    public boolean save(SupplierEntity supplierEntity) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        session.persist(supplierEntity);
        session.getTransaction().commit();
        session.close();
        return false;
    }

    @Override
    public boolean delete(String id) {
        Session session = HibernateUtil.getSession();
        try {
            session.beginTransaction();
            SupplierEntity supplierEntity = session.get(SupplierEntity.class, id);

            if (supplierEntity != null) {
                session.remove(supplierEntity);
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
    public ObservableList<SupplierEntity> getAll() {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        Query<SupplierEntity> query = session.createQuery("FROM SupplierEntity", SupplierEntity.class);
        List<SupplierEntity> supplierEntityList = query.getResultList();
        session.getTransaction().commit();
        session.close();
        System.out.println(supplierEntityList);
        return FXCollections.observableArrayList(supplierEntityList);
    }

    @Override
    public boolean update(SupplierEntity supplierEntity) {
        Session session = HibernateUtil.getSession();
        try {
            session.beginTransaction();
            session.update(supplierEntity);
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
    public SupplierEntity search(String id) {
        Session session = HibernateUtil.getSession();
        try {
            session.beginTransaction();
            SupplierEntity supplierEntity = session.get(SupplierEntity.class, id);
            session.getTransaction().commit();
            return supplierEntity;
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
