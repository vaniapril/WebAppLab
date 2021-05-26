package services;

import models.DAOs.AbstractDao;
import models.DAOs.proxy.ProxyFabric;
import models.Validator;
import models.exceptions.DaoLayerException;
import models.exceptions.ServiceLayerException;
import models.units.Cryptocurrency;
import models.units.FinancialUnit;
import models.units.MonetaryUnit;
import services.components.FileFabric;
import services.components.FileName;

import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

public class MonetaryUnitService extends AbstractService {
    AbstractDao<MonetaryUnit> dao;
    ReentrantLock locker;

    private static MonetaryUnitService shared;

    public static MonetaryUnitService sharedInstance() throws DaoLayerException {
        if (null == shared) {
            shared = new MonetaryUnitService();
        }
        return shared;
    }

    private MonetaryUnitService() throws DaoLayerException {
        try{
            switch (fileType) {
                case XML:
                    dao = new ProxyFabric<MonetaryUnit>().getXmlDaoProxy(FileFabric.getFile(FileName.MONETARY_UNIT, fileType), MonetaryUnit.class);
                    break;
                case CSV:
                    dao = new ProxyFabric<MonetaryUnit>().getCsvDaoProxy(FileFabric.getFile(FileName.MONETARY_UNIT, fileType), MonetaryUnit.class);
                    break;
                case JSON:
                    dao = new ProxyFabric<MonetaryUnit>().getJsonDaoProxy(FileFabric.getFile(FileName.MONETARY_UNIT, fileType), MonetaryUnit.class);
                    break;
                case DB:
                    dao = new ProxyFabric<MonetaryUnit>().getDBDaoProxy(MonetaryUnit.class);
                    break;
            }
        } catch (Exception e){
            throw new DaoLayerException();
        }

        locker = new ReentrantLock();
    }

    public List<MonetaryUnit> getElements() throws DaoLayerException {
        locker.lock();
        try {
            return dao.readAll();
        } finally {
            locker.unlock();
        }
    }

    public MonetaryUnit getElement(String uuid) throws DaoLayerException, ServiceLayerException {
        locker.lock();
        try {
            return dao.readOne(uuid);
        } finally {
            locker.unlock();
        }
    }

    public void create(MonetaryUnit element) throws DaoLayerException, ServiceLayerException {
        try {
            Validator.sharedInstance().validate(element, MonetaryUnit.class);
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

    public void update(MonetaryUnit element) throws DaoLayerException, ServiceLayerException {
        try {
            Validator.sharedInstance().validate(element, MonetaryUnit.class);
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

    public List<MonetaryUnit> task1() throws DaoLayerException {
        return operation1(getElements().stream().map(e -> (FinancialUnit)e).collect(Collectors.toList()))
                .stream().map(e -> (MonetaryUnit)e).collect(Collectors.toList());
    }

    public List<MonetaryUnit> task2() throws DaoLayerException {
        return operation2(getElements().stream().map(e -> (FinancialUnit)e).collect(Collectors.toList()))
                .stream().map(e -> (MonetaryUnit)e).collect(Collectors.toList());
    }
}