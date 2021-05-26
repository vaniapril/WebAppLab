package models.DAOs;

import models.exceptions.DaoLayerException;
import models.units.FinancialUnit;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

public class DBDao<T extends FinancialUnit> implements AbstractDao<T>{
    Class clazz;
    private EntityManager manager;

    public DBDao(Class clazz) throws Exception{
        this.clazz = clazz;
        manager = Persistence.createEntityManagerFactory("MYSQL").createEntityManager();
    }

    @Override
    public void create(T obj) throws DaoLayerException {
        try {
            if (!manager.getTransaction().isActive()) {
                manager.getTransaction().begin();
            }
            manager.persist(obj);
            manager.getTransaction().commit();
        } catch (Exception e) {
            manager.getTransaction().rollback();
            throw new DaoLayerException();
        }
    }

    @Override
    public List<T> readAll() throws DaoLayerException {
        Query query = manager.createQuery("FROM " + this.clazz.getSimpleName(), clazz);
        return query.getResultList();
    }

    @Override
    public T readOne(String key) throws DaoLayerException {
        return (T) manager.find(this.clazz, key);
    }

    @Override
    public void update(T obj) throws DaoLayerException {
        manager.getTransaction().begin();
        manager.merge(obj);
        manager.getTransaction().commit();
    }

    @Override
    public void delete(String key) throws DaoLayerException {
        manager.getTransaction().begin();
        T obj = readOne(key);
        if (obj != null) {
            manager.remove(obj);
        }
        manager.getTransaction().commit();
    }
}
