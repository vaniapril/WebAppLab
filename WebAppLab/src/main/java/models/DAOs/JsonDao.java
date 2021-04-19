package models.DAOs;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.units.FinancialUnit;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class JsonDao<T extends FinancialUnit> implements AbstractDao<T> {
    File file;

    public JsonDao(File file, Class clazz) throws Exception{
        this.file = file;
    }

    @Override
    public List<T> read() throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        List<T> list = mapper.readValue(file, new TypeReference<List<T>>(){});
        return list;
    }

    @Override
    public void write(List<T> list) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(file,list);
    }
}
