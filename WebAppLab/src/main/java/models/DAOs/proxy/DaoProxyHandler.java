package models.DAOs.proxy;

import models.DAOs.AbstractDao;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class DaoProxyHandler<T extends AbstractDao> implements InvocationHandler {
    private T target;
    static private Map<String, Integer> total = new HashMap<>();

    static void log(String message){
        System.out.println(message);
    }

    DaoProxyHandler(T target){
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (!total.containsKey(method.getName())){
            total.put(method.getName(), 1);
        } else {
            total.replace(method.getName(), total.get(method.getName()) + 1);
        }
        log("Invoke method: " + method.getName() + ", total: " + total.get(method.getName()));
        return method.invoke(target, args);
    }
}
