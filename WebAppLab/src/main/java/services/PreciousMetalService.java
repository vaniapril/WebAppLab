package services;

import models.DAOs.AbstractDao;
import models.DAOs.proxy.ProxyFabric;
import models.Validator;
import models.exceptions.DaoLayerException;
import models.exceptions.ValidationException;
import models.units.FinancialUnit;
import models.units.PreciousMetal;
import services.components.FileFabric;
import services.components.FileName;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

public class PreciousMetalService extends AbstractService {
    AbstractDao<PreciousMetal> dao;
    ReentrantLock locker;

    private static PreciousMetalService shared;

    public static PreciousMetalService sharedInstance() throws Exception {
        if (null == shared){
            shared = new PreciousMetalService();
        }
        return shared;
    }

    private PreciousMetalService() throws Exception {
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
        }
        locker = new ReentrantLock();
    }

    public List<PreciousMetal> getElements() throws DaoLayerException {
        locker.lock();
        try {
            List<PreciousMetal> list = dao.read();
            return list;
        } catch (Exception exception){
            throw new DaoLayerException();
        } finally {
            locker.unlock();
        }
    }

    public PreciousMetal getElement(String uuid) throws DaoLayerException, NoSuchElementException {
        List<PreciousMetal> list = getElements();
        return list.stream().filter(e -> e.uuid.equals(uuid)).findFirst().get();
    }

    public void create(PreciousMetal element) throws DaoLayerException, ValidationException {
        Validator.sharedInstance().validate(element, PreciousMetal.class);
        locker.lock();
        try {
            List<PreciousMetal> list = dao.read();
            list.add(element);
            dao.write(list);
        } catch (Exception exception){
            throw new DaoLayerException();
        } finally {
            locker.unlock();
        }
    }

    public void update(PreciousMetal element) throws Exception, NoSuchElementException, ValidationException {
        Validator.sharedInstance().validate(element, PreciousMetal.class);
        locker.lock();
        try {
            List<PreciousMetal> list = dao.read();
            PreciousMetal elem = list.stream().filter(e -> e.uuid.equals(element.uuid)).findFirst().get();
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
            List<PreciousMetal> list = dao.read();
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

    public List<PreciousMetal> task1() throws DaoLayerException {
        return operation1(getElements().stream().map(e -> (FinancialUnit)e).collect(Collectors.toList()))
                .stream().map(e -> (PreciousMetal)e).collect(Collectors.toList());
    }

    public List<PreciousMetal> task2() throws DaoLayerException {
        return operation2(getElements().stream().map(e -> (FinancialUnit)e).collect(Collectors.toList()))
                .stream().map(e -> (PreciousMetal)e).collect(Collectors.toList());
    }
}
