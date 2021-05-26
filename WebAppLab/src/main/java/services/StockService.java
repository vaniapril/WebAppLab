package services;

import models.DAOs.AbstractDao;
import models.DAOs.proxy.ProxyFabric;
import models.Validator;
import models.exceptions.DaoLayerException;
import models.exceptions.ServiceLayerException;
import models.units.Cryptocurrency;
import models.units.FinancialUnit;
import models.units.PreciousMetal;
import models.units.Stock;
import services.components.FileFabric;
import services.components.FileName;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

public class StockService extends AbstractService{
    AbstractDao<Stock> dao;
    ReentrantLock locker;

    private static StockService shared;

    public static StockService sharedInstance() throws DaoLayerException {
        if (null == shared){
            shared = new StockService();
        }
        return shared;
    }

    private StockService() throws DaoLayerException {
        try {
            switch (fileType){
                case XML:
                    dao = new ProxyFabric<Stock>().getXmlDaoProxy(FileFabric.getFile(FileName.STOCK, fileType), Stock.class);
                    break;
                case CSV:
                    dao = new ProxyFabric<Stock>().getCsvDaoProxy(FileFabric.getFile(FileName.STOCK, fileType), Stock.class);
                    break;
                case JSON:
                    dao = new ProxyFabric<Stock>().getJsonDaoProxy(FileFabric.getFile(FileName.STOCK, fileType), Stock.class);
                    break;
                case DB:
                    dao = new ProxyFabric<Stock>().getDBDaoProxy(Stock.class);
                    break;
            }
        } catch (Exception e){
            throw new DaoLayerException();
        }
        locker = new ReentrantLock();
    }

    public List<Stock> getElements() throws DaoLayerException {
        locker.lock();
        try {
            return dao.readAll();
        } finally {
            locker.unlock();
        }
    }

    public Stock getElement(String uuid) throws DaoLayerException, ServiceLayerException {
        locker.lock();
        try {
            return dao.readOne(uuid);
        } finally {
            locker.unlock();
        }
    }

    public void create(Stock element) throws DaoLayerException, ServiceLayerException {
        try {
            Validator.sharedInstance().validate(element, Stock.class);
        } catch (Exception e){
            throw new ServiceLayerException();
        }

        locker.lock();
        try {
            dao.create(element);
        } finally {
            locker.unlock();
        }
    }

    public void update(Stock element) throws DaoLayerException, ServiceLayerException {
        try {
            Validator.sharedInstance().validate(element, Stock.class);
        } catch (Exception e){
            throw new ServiceLayerException();
        }

        locker.lock();
        try {
            dao.update(element);
        } finally {
            locker.unlock();
        }
    }

    public void delete(String uuid) throws ServiceLayerException, DaoLayerException {
        locker.lock();
        try {
            dao.delete(uuid);
        } finally {
            locker.unlock();
        }
    }

    public List<Stock> task1() throws DaoLayerException {
        return operation1(getElements().stream().map(e -> (FinancialUnit)e).collect(Collectors.toList()))
                .stream().map(e -> (Stock)e).collect(Collectors.toList());
    }

    public List<Stock> task2() throws DaoLayerException {
        return operation2(getElements().stream().map(e -> (FinancialUnit)e).collect(Collectors.toList()))
                .stream().map(e -> (Stock)e).collect(Collectors.toList());
    }
}
