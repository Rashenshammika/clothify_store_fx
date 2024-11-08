package repository.custom.impl;

import entity.ProductEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Session;
import org.hibernate.query.Query;
import repository.custom.ProductDao;
import util.HibernateUtil;

import java.util.List;

public class ProductDaoImpl implements ProductDao {
    @Override
    public boolean save(ProductEntity productEntity) {
        System.out.println(productEntity);
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        session.persist(productEntity);
        session.getTransaction().commit();
        session.close();
        return false;
    }

    @Override
    public boolean delete(String id) {
        Session session = HibernateUtil.getSession();
        try {
            session.beginTransaction();
            ProductEntity productEntity = session.get(ProductEntity.class, id);

            if (productEntity  != null) {
                session.remove(productEntity );
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
    public ObservableList<ProductEntity> getAll() {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        Query<ProductEntity> query = session.createQuery("FROM ProductEntity", ProductEntity.class);
        List<ProductEntity> productList = query.getResultList();
        session.getTransaction().commit();
        session.close();
        return FXCollections.observableArrayList(productList);
    }

    @Override
    public boolean update(ProductEntity productEntity) {
        Session session = HibernateUtil.getSession();
        try {
            session.beginTransaction();
            session.update(productEntity);
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
    public ProductEntity search(String id) {
        Session session = HibernateUtil.getSession();
        try {
            session.beginTransaction();
            ProductEntity productEntity = session.get(ProductEntity.class, id);
            session.getTransaction().commit();
            return productEntity;
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
