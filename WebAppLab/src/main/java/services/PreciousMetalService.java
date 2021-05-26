package services;

import models.DAOs.AbstractDao;
import models.DAOs.proxy.ProxyFabric;
import models.Validator;
import models.exceptions.DaoLayerException;
import models.exceptions.ServiceLayerException;
import models.units.Cryptocurrency;
import models.units.FinancialUnit;
import models.units.PreciousMetal;
import services.components.FileFabric;
import services.components.FileName;

import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

public class PreciousMetalService extends AbstractService {
    AbstractDao<PreciousMetal> dao;
    ReentrantLock locker;

    private static PreciousMetalService shared;

    public static PreciousMetalService sharedInstance() throws DaoLayerException {
        if (null == shared){
            shared = new PreciousMetalService();
        }
        return shared;
    }

    private PreciousMetalService() throws DaoLayerException {
        try{
            switch (fileType){
                case XML:
                    dao = new ProxyFabric<PreciousMetal>().getXmlDaoProxy(FileFabric.getFile(FileName.PRECIOUS_METAL, fileType), PreciousMetal.class);
                    break;
                case CSV:
                    dao = new ProxyFabric<PreciousMetal>().getCsvDaoProxy(FileFabric.getFile(FileName.PRECIOUS_METAL, fileType), PreciousMetal.class);
                    break;
                case JSON:
                    dao = new ProxyFabric<PreciousMetal>().getJsonDaoProxy(FileFabric.getFile(FileName.PRECIOUS_METAL, fileType), PreciousMetal.class);
                    break;
                case DB:
                    dao = new ProxyFabric<PreciousMetal>().getDBDaoProxy(PreciousMetal.class);
                    break;
            }
        } catch (Exception e){
            throw new DaoLayerException();
        }
        locker = new ReentrantLock();
    }

    public List<PreciousMetal> getElements() throws DaoLayerException {
        locker.lock();
        try {
            return dao.readAll();
        } finally {
            locker.unlock();
        }
    }

    public PreciousMetal getElement(String uuid) throws DaoLayerException, ServiceLayerException {
        locker.lock();
        try {
            return dao.readOne(uuid);
        } finally {
            locker.unlock();
        }
    }

    public void create(PreciousMetal element) throws DaoLayerException, ServiceLayerException {
        try {
            Validator.sharedInstance().validate(element, PreciousMetal.class);
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

    public void update(PreciousMetal element) throws DaoLayerException, ServiceLayerException {
        try {
            Validator.sharedInstance().validate(element, PreciousMetal.class);
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

    public List<PreciousMetal> task1() throws DaoLayerException {
        return operation1(getElements().stream().map(e -> (FinancialUnit)e).collect(Collectors.toList()))
                .stream().map(e -> (PreciousMetal)e).collect(Collectors.toList());
    }

    public List<PreciousMetal> task2() throws DaoLayerException {
        return operation2(getElements().stream().map(e -> (FinancialUnit)e).collect(Collectors.toList()))
                .stream().map(e -> (PreciousMetal)e).collect(Collectors.toList());
    }
}
