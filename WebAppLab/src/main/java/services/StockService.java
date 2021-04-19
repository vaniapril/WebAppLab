package services;

import models.DAOs.AbstractDao;
import models.DAOs.proxy.ProxyFabric;
import models.Validator;
import models.exceptions.DaoLayerException;
import models.exceptions.ValidationException;
import models.units.FinancialUnit;
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

    public static StockService sharedInstance() throws Exception {
        if (null == shared){
            shared = new StockService();
        }
        return shared;
    }

    private StockService() throws Exception {
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
        }
        locker = new ReentrantLock();
    }

    public List<Stock> getElements() throws DaoLayerException {
        locker.lock();
        try {
            List<Stock> list = dao.read();
            return list;
        } catch (Exception exception){
            throw new DaoLayerException();
        } finally {
            locker.unlock();
        }
    }

    public Stock getElement(String uuid) throws DaoLayerException, NoSuchElementException {
        List<Stock> list = getElements();
        return list.stream().filter(e -> e.uuid.equals(uuid)).findFirst().get();
    }

    public void create(Stock element) throws DaoLayerException, ValidationException {
        Validator.sharedInstance().validate(element, Stock.class);
        locker.lock();
        try {
            List<Stock> list = dao.read();
            list.add(element);
            dao.write(list);
        } catch (Exception exception){
            throw new DaoLayerException();
        } finally {
            locker.unlock();
        }
    }

    public void update(Stock element) throws Exception, NoSuchElementException, ValidationException {
        Validator.sharedInstance().validate(element, Stock.class);
        locker.lock();
        try {
            List<Stock> list = dao.read();
            Stock elem = list.stream().filter(e -> e.uuid.equals(element.uuid)).findFirst().get();
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
            List<Stock> list = dao.read();
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

    public List<Stock> task1() throws DaoLayerException {
        return operation1(getElements().stream().map(e -> (FinancialUnit)e).collect(Collectors.toList()))
                .stream().map(e -> (Stock)e).collect(Collectors.toList());
    }

    public List<Stock> task2() throws DaoLayerException {
        return operation2(getElements().stream().map(e -> (FinancialUnit)e).collect(Collectors.toList()))
                .stream().map(e -> (Stock)e).collect(Collectors.toList());
    }
}
