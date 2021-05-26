package models.DAOs;

import models.exceptions.DaoLayerException;
import models.units.FinancialUnit;
import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractFileDao<T extends FinancialUnit> implements AbstractDao<T> {
    protected File file;

    abstract protected List<T> read() throws Exception;
    abstract protected void write(List<T> list) throws Exception;

    @Override
    public void create(T obj) throws DaoLayerException {
        try {
            List<T> list = read();
            list.add(obj);
            write(list);
        } catch (Exception e){
            throw new DaoLayerException();
        }
    }

    @Override
    public List<T> readAll() throws DaoLayerException {
        try {
            List<T> list = read();
            return list;
        } catch (Exception e){
            throw new DaoLayerException();
        }
    }

    @Override
    public T readOne(String key) throws DaoLayerException {
        try {
            List<T> list = read();
            return list.stream().filter(e -> e.uuid.equals(key)).findFirst().get();
        } catch (Exception e){
            throw new DaoLayerException();
        }
    }

    @Override
    public void update(T obj) throws DaoLayerException {
        try {
            List<T> list = read();
            T elem = list.stream().filter(e -> e.uuid.equals(obj.uuid)).findFirst().get();
            elem.update(obj);
            write(list);
        } catch (Exception e){
            throw new DaoLayerException();
        }
    }

    @Override
    public void delete(String key) throws DaoLayerException {
        try {
            List<T> list = read();
            write(list.stream().filter(e -> !e.uuid.equals(key)).collect(Collectors.toList()));
        } catch (Exception e){
            throw new DaoLayerException();
        }
    }
}
