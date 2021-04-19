package models.DAOs;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import models.units.FinancialUnit;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class XMLDao<T extends FinancialUnit> implements AbstractDao<T> {
    File file;

    public XMLDao(File file, Class clazz) throws Exception{
        this.file = file;
    }

    @Override
    public List<T> read() throws Exception {
        ObjectMapper mapper = new XmlMapper();
        List<T> list = mapper.readValue(file, new TypeReference<List<T>>(){});
        return list;
    }

    @Override
    public void write(List<T> list) throws IOException {
        ObjectMapper mapper = new XmlMapper();
        mapper.writeValue(file,list);
    }
}
