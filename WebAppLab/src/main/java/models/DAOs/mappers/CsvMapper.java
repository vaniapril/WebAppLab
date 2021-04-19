package models.DAOs.mappers;
import models.units.FinancialUnit;

import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Field;
import java.util.*;

public class CsvMapper<T extends FinancialUnit> {

    public List<T> readValue(File file, Class clazz) throws Exception{
        Scanner scanner = new Scanner(file);
        List<T> list = new ArrayList<>();
        while (scanner.hasNext()) {
            String str = scanner.nextLine();
            list.add(valueFromString(str, clazz));
        }
        scanner.close();
        return list;
    }

    public void writeValue(File file, List<T> list, Class clazz) throws Exception{
        FileWriter fileWriter = new FileWriter(file);
        for(int i = 0; i < list.size(); i++){
            fileWriter.write(valueToString(list.get(i), clazz) + '\n');
        }
        fileWriter.close();
    }

    private T valueFromString(String str, Class clazz) throws Exception {
        StringTokenizer tokenizer = new StringTokenizer(str, ",");
        ArrayList<String> source = new ArrayList<>();
        while (tokenizer.hasMoreElements()){
            source.add(tokenizer.nextToken());
        }
        Collections.reverse(source);
        Class oClass = clazz;
        Object data = oClass.getConstructor().newInstance();
        int id = 0;
        while (oClass != Object.class){
            id = valueToClass(oClass, data, source, id);
            oClass = oClass.getSuperclass();
        }
        return (T) data;
    }

    private int valueToClass(Class oClass, Object data, ArrayList<String> source, int id) throws Exception {
        Field[] fields = oClass.getDeclaredFields();
        for (int j = fields.length - 1; j >= 0; j--){
            Field field = fields[j];
            if (field.getType().isPrimitive() || String.class.equals(field.getType())){
                setValue(field,data,source.get(id));
                id++;
            } else {
                Object subData = field.getType().getConstructor().newInstance();
                id = valueToClass(field.getType(), subData, source, id);
                field.setAccessible(true);
                field.set(data, subData);
            }
        }
        return id;
    }

    private void setValue(Field field, Object data, String value) throws Exception{
        field.setAccessible(true);
        //Todo: class Double, int, class Integer
        if (field.getType().getName().equals("double")) {
            field.set(data, Double.parseDouble(value));
        } else if (String.class.equals(field.getType())) {
            field.set(data, value);
        } else {
            throw new Exception();
        }
    }

    private<T2> String valueToString(T2 value, Class clazz) throws Exception {
        String str = "";
        Class oClass = clazz;
        while (oClass != Object.class){
            Field[] fields = oClass.getDeclaredFields();
            for (int j = fields.length - 1; j >= 0; j--){
                Field field = fields[j];
                field.setAccessible(true);
                Object object = field.get(value);
                if (field.getType().isPrimitive() || String.class.equals(field.getType())){
                    str = object + "," + str;
                } else {
                    str = valueToString(object, field.getType()) + "," + str;
                }
            }
            oClass = oClass.getSuperclass();
        }
        return str.substring(0,str.length() - 1);
    }
}
