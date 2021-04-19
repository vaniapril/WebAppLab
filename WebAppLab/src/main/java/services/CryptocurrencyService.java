package services;

import models.DAOs.AbstractDao;
import models.DAOs.proxy.ProxyFabric;
import models.Validator;
import models.exceptions.DaoLayerException;
import models.exceptions.ValidationException;
import models.units.Cryptocurrency;
import models.units.FinancialUnit;
import services.components.FileName;
import services.components.FileFabric;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

public class CryptocurrencyService extends AbstractService{
    AbstractDao<Cryptocurrency> dao;
    ReentrantLock locker;

    private static CryptocurrencyService shared;

    public static CryptocurrencyService sharedInstance() throws Exception {
        if (null == shared){
            shared = new CryptocurrencyService();
        }
        return shared;
    }

    private CryptocurrencyService() throws Exception {
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
        }
        locker = new ReentrantLock();
    }

    public List<Cryptocurrency> getElements() throws DaoLayerException {
        locker.lock();
        try {
            List<Cryptocurrency> list = dao.read();
            return list;
        } catch (Exception exception){
            throw new DaoLayerException();
        } finally {
            locker.unlock();
        }
    }

    public Cryptocurrency getElement(String uuid) throws DaoLayerException, NoSuchElementException {
        List<Cryptocurrency> list = getElements();
        return list.stream().filter(e -> e.uuid.equals(uuid)).findFirst().get();
    }

    public void create(Cryptocurrency element) throws DaoLayerException, ValidationException {
        Validator.sharedInstance().validate(element, Cryptocurrency.class);
        locker.lock();
        try {
            List<Cryptocurrency> list = dao.read();
            list.add(element);
            dao.write(list);
        } catch (Exception exception){
            throw new DaoLayerException();
        } finally {
            locker.unlock();
        }
    }

    public void update(Cryptocurrency element) throws Exception, NoSuchElementException, ValidationException {
        Validator.sharedInstance().validate(element, Cryptocurrency.class);
        locker.lock();
        try {
            List<Cryptocurrency> list = dao.read();
            Cryptocurrency elem = list.stream().filter(e -> e.uuid.equals(element.uuid)).findFirst().get();
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
            List<Cryptocurrency> list = dao.read();
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

    public List<Cryptocurrency> task1() throws DaoLayerException {
        return operation1(getElements().stream().map(e -> (FinancialUnit)e).collect(Collectors.toList()))
                .stream().map(e -> (Cryptocurrency)e).collect(Collectors.toList());
    }

    public List<Cryptocurrency> task2() throws DaoLayerException {
        return operation2(getElements().stream().map(e -> (FinancialUnit)e).collect(Collectors.toList()))
                .stream().map(e -> (Cryptocurrency)e).collect(Collectors.toList());
    }
}
