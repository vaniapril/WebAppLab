package models.DAOs;

import models.exceptions.DaoLayerException;
import models.units.FinancialUnit;

import java.util.List;

public interface AbstractDao<T extends FinancialUnit> {
    void create(T obj) throws DaoLayerException;
    List<T> readAll() throws DaoLayerException;
    T readOne(String key) throws DaoLayerException;
    void update(T obj) throws DaoLayerException;
    void delete(String key) throws DaoLayerException;
}
