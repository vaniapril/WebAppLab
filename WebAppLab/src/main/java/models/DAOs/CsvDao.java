package models.DAOs;

import models.DAOs.mappers.CsvMapper;
import models.units.FinancialUnit;

import java.io.File;
import java.util.List;

public class CsvDao<T extends FinancialUnit> implements AbstractDao<T> {
    File file;
    Class clazz;

    public CsvDao(File file, Class clazz) throws Exception{
        this.file = file;
        this.clazz = clazz;
    }

    @Override
    public List<T> read() throws Exception{
        CsvMapper<T> mapper = new CsvMapper<T>();
        List<T> list = mapper.readValue(file, clazz);
        return list;
    }

    @Override
    public void write(List<T> list) throws Exception {
        CsvMapper<T> mapper = new CsvMapper<T>();
        mapper.writeValue(file, list, clazz);
    }
}
