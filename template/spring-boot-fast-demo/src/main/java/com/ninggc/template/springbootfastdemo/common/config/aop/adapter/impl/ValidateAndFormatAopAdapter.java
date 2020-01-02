package com.ninggc.template.springbootfastdemo.common.config.aop.adapter.impl;

import com.ninggc.common.web.ResponseData;
import com.ninggc.template.springbootfastdemo.common.config.aop.AopAdapter;
import com.ninggc.template.springbootfastdemo.common.config.aop.adapter.IAopAdapter;
import com.ninggc.template.springbootfastdemo.common.security.Valid;
import com.ninggc.template.springbootfastdemo.common.config.aop.action.validate.IVO;
import org.aspectj.lang.JoinPoint;
import org.springframework.util.Assert;

import javax.validation.constraints.NotNull;
import java.lang.reflect.Field;
import java.util.Map;

/**
 * 参数校验的adapter
 */
@AopAdapter
public class ValidateAndFormatAopAdapter implements IAopAdapter {
    @Override
    public void doBefore(JoinPoint joinPoint, String[] parameterNames, Object[] args) {
        for (Object arg : args) {
            for (Field field : arg.getClass().getDeclaredFields()) {
                if (field.getAnnotation(NotNull.class) != null) {
                    try {
                        field.setAccessible(true);
                        Assert.notNull(field.get(arg), field.getName() + " cannot be null!");
                    } catch (IllegalAccessException ignored) { }
                }
            }
            if (arg instanceof Valid) {
                ((Valid) arg).validate();
            }
        }
    }

    @Override
    public Object doAfterReturn(JoinPoint joinPoint, Object returnValue) {
        if (returnValue instanceof IVO) {
            Map<String, Object> format = null;
            try {
                format = ((IVO) returnValue).format();
            } catch (IllegalAccessException e) {
                error(e.getMessage());
                return ResponseData.buildSuccess(returnValue);
            }
            return ResponseData.buildSuccess(format);
        }
        return null;
    }

    @Override
    public void doAfterThrow(JoinPoint joinPoint, Exception exception) {

    }
}
