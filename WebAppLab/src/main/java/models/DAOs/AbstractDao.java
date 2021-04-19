package models.DAOs;

import models.units.FinancialUnit;

import java.util.List;

public interface AbstractDao<T extends FinancialUnit> {
    List<T> read() throws Exception;
    void write(List<T> list) throws Exception;
}
