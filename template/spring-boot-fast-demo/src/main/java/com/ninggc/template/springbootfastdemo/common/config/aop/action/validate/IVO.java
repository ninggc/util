package com.ninggc.template.springbootfastdemo.common.config.aop.action.validate;

import com.ninggc.template.springbootfastdemo.common.config.aop.util.IUtilGson;
import com.ninggc.template.springbootfastdemo.common.config.aop.util.IUtilLogger;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * 标记这是一个需要format的VO
 */
@Deprecated
public interface IVO extends IUtilGson, IUtilLogger {
    default Map<String, Object> format() throws IllegalAccessException {
        Map<String, Object> resultMap = new HashMap<>();
        Field[] parents = this.getClass().getDeclaredFields();

        try {
            for (Field field : parents) {
                field.setAccessible(true);
                Object parent = field.get(this);

                for (Field child : parent.getClass().getDeclaredFields()) {
                    child.setAccessible(true);
                    resultMap.put(child.getName(), child.get(parent));
                }
            }
        } catch (IllegalAccessException e) {
            error(e.getMessage());
            throw e;
        }

        return resultMap;
    }
}
