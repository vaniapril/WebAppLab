package services;

import models.DAOs.AbstractDao;
import models.DAOs.proxy.ProxyFabric;
import models.Validator;
import models.exceptions.DaoLayerException;
import models.exceptions.ServiceLayerException;
import models.units.Cryptocurrency;
import models.units.FinancialUnit;
import services.components.FileName;
import services.components.FileFabric;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

public class CryptocurrencyService extends AbstractService{
    AbstractDao<Cryptocurrency> dao;
    ReentrantLock locker;

    private static CryptocurrencyService shared;

    public static CryptocurrencyService sharedInstance() throws DaoLayerException {
        if (null == shared){
            shared = new CryptocurrencyService();
        }
        return shared;
    }

    private CryptocurrencyService() throws DaoLayerException {
        try {
            switch (fileType){
                case XML:
                    dao = new ProxyFabric<Cryptocurrency>().getXmlDaoProxy(FileFabric.getFile(FileName.CRYPTO_CURRENCY, fileType), Cryptocurrency.class);
                    break;
                case CSV:
                    dao = new ProxyFabric<Cryptocurrency>().getCsvDaoProxy(FileFabric.getFile(FileName.CRYPTO_CURRENCY, fileType), Cryptocurrency.class);
                    break;
                case JSON:
                    dao = new ProxyFabric<Cryptocurrency>().getJsonDaoProxy(FileFabric.getFile(FileName.CRYPTO_CURRENCY, fileType), Cryptocurrency.class);
                    break;
                case DB:
                    dao = new ProxyFabric<Cryptocurrency>().getDBDaoProxy(Cryptocurrency.class);
                    break;
            }
        } catch (Exception e){
            throw new DaoLayerException();
        }

        locker = new ReentrantLock();
    }

    public List<Cryptocurrency> getElements() throws DaoLayerException {
        locker.lock();
        try {
            return dao.readAll();
        } finally {
            locker.unlock();
        }
    }

    public Cryptocurrency getElement(String uuid) throws DaoLayerException, ServiceLayerException {
        locker.lock();
        try {
            return dao.readOne(uuid);
        } finally {
            locker.unlock();
        }
    }

    public void create(Cryptocurrency element) throws DaoLayerException, ServiceLayerException {
        try {
            Validator.sharedInstance().validate(element, Cryptocurrency.class);
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

    public void update(Cryptocurrency element) throws DaoLayerException, ServiceLayerException {
        try {
            Validator.sharedInstance().validate(element, Cryptocurrency.class);
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

    public List<Cryptocurrency> task1() throws DaoLayerException {
        return operation1(getElements().stream().map(e -> (FinancialUnit)e).collect(Collectors.toList()))
                .stream().map(e -> (Cryptocurrency)e).collect(Collectors.toList());
    }

    public List<Cryptocurrency> task2() throws DaoLayerException {
        return operation2(getElements().stream().map(e -> (FinancialUnit)e).collect(Collectors.toList()))
                .stream().map(e -> (Cryptocurrency)e).collect(Collectors.toList());
    }
}
