package services;

import models.DAOs.AbstractDao;
import models.DAOs.proxy.ProxyFabric;
import models.Validator;
import models.exceptions.DaoLayerException;
import models.exceptions.ValidationException;
import models.units.FinancialUnit;
import models.units.MonetaryUnit;
import services.components.FileFabric;
import services.components.FileName;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

public class MonetaryUnitService extends AbstractService {
    AbstractDao<MonetaryUnit> dao;
    ReentrantLock locker;

    private static MonetaryUnitService shared;

    public static MonetaryUnitService sharedInstance() throws Exception {
        if (null == shared) {
            shared = new MonetaryUnitService();
        }
        return shared;
    }

    private MonetaryUnitService() throws Exception {
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
        }
        locker = new ReentrantLock();
    }

    public List<MonetaryUnit> getElements() throws DaoLayerException {
        locker.lock();
        try {
            List<MonetaryUnit> list = dao.read();
            return list;
        } catch (Exception exception){
            throw new DaoLayerException();
        } finally {
            locker.unlock();
        }
    }

    public MonetaryUnit getElement(String uuid) throws DaoLayerException, NoSuchElementException {
        List<MonetaryUnit> list = getElements();
        return list.stream().filter(e -> e.uuid.equals(uuid)).findFirst().get();
    }

    public void create(MonetaryUnit element) throws DaoLayerException, ValidationException {
        Validator.sharedInstance().validate(element, MonetaryUnit.class);
        locker.lock();
        try {
            List<MonetaryUnit> list = dao.read();
            list.add(element);
            dao.write(list);
        } catch (Exception exception){
            throw new DaoLayerException();
        } finally {
            locker.unlock();
        }
    }

    public void update(MonetaryUnit element) throws Exception, NoSuchElementException, ValidationException {
        Validator.sharedInstance().validate(element, MonetaryUnit.class);
        locker.lock();
        try {
            List<MonetaryUnit> list = dao.read();
            MonetaryUnit elem = list.stream().filter(e -> e.uuid.equals(element.uuid)).findFirst().get();
            elem.setCode(element.code);
            elem.setPrice(element.price);
            elem.setName(element.name);
            dao.write(list);
        } catch(NoSuchElementException exception){
            throw exception;
        } catch (Exception exception){
            throw new DaoLayerException();
        } finally {
            locker.unlock();
        }
    }

    public void delete(String uuid) throws Exception, NoSuchElementException {
        locker.lock();
        try {
            List<MonetaryUnit> list = dao.read();
            list.stream().filter(e -> e.uuid.equals(uuid)).findFirst().get();
            dao.write(list.stream().filter(e -> !e.uuid.equals(uuid)).collect(Collectors.toList()));
        } catch(NoSuchElementException exception){
            throw exception;
        } catch (Exception exception){
            throw new DaoLayerException();
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