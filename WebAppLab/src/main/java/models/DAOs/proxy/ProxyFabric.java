package models.DAOs.proxy;

import models.DAOs.*;
import models.units.FinancialUnit;

import java.io.File;
import java.lang.reflect.Proxy;

public class ProxyFabric<T extends FinancialUnit> {
    public AbstractDao<T> getXmlDaoProxy(File file, Class clazz) throws Exception{
        return (AbstractDao<T>) Proxy.newProxyInstance(
                XMLDao.class.getClassLoader(),
                new Class[]{AbstractDao.class},
                new DaoProxyHandler<>(new XMLDao<T>(file, clazz))
        );
    }
    public AbstractDao<T> getJsonDaoProxy(File file, Class clazz) throws Exception{
        return (AbstractDao<T>) Proxy.newProxyInstance(
                JsonDao.class.getClassLoader(),
                new Class[]{AbstractDao.class},
                new DaoProxyHandler<>(new JsonDao<T>(file, clazz))
        );
    }
    public AbstractDao<T> getCsvDaoProxy(File file, Class clazz) throws Exception{
        return (AbstractDao<T>) Proxy.newProxyInstance(
                CsvDao.class.getClassLoader(),
                new Class[]{AbstractDao.class},
                new DaoProxyHandler<>(new CsvDao<T>(file, clazz))
        );
    }
    public AbstractDao<T> getDBDaoProxy(Class clazz) throws Exception{
        return (AbstractDao<T>) Proxy.newProxyInstance(
                DBDao.class.getClassLoader(),
                new Class[]{AbstractDao.class},
                new DaoProxyHandler<>(new DBDao<T>(clazz))
        );
    }
}
