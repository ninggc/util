package com.ninggc.util.autogenerate;

import java.lang.reflect.Field;
import java.lang.reflect.Type;

public class AutoGeneration {

    private static RandomFactory randomFactory = new RandomFactory();

    public static <T> T genInstance(Class<T> clazz) throws RuntimeException{
        T t = null;
        try {
            t = clazz.newInstance();
            for (Field item : clazz.getDeclaredFields()) {
                Field field = clazz.getDeclaredField(item.getName());
                Type genericType = field.getGenericType();
                if (!genericType.getTypeName().equals(String.class.getName())) {
                    continue;
                }
                field.setAccessible(true);
                field.set(t, randomFactory.getString(randomFactory.getInt(1, 10)));
            }
        } catch (NoSuchFieldException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return t;
    }
}
