package models.DAOs;

import models.DAOs.mappers.CsvMapper;
import models.exceptions.DaoLayerException;
import models.units.FinancialUnit;

import java.io.File;
import java.util.List;

public class CsvDao<T extends FinancialUnit> extends AbstractFileDao<T> {
    Class clazz;

    public CsvDao(File file, Class clazz) throws Exception{
        this.file = file;
        this.clazz = clazz;
    }

    @Override
    protected List<T> read() throws Exception{
        CsvMapper<T> mapper = new CsvMapper<T>();
        List<T> list = mapper.readValue(file, clazz);
        return list;
    }

    @Override
    protected void write(List<T> list) throws Exception {
        CsvMapper<T> mapper = new CsvMapper<T>();
        mapper.writeValue(file, list, clazz);
    }
}
