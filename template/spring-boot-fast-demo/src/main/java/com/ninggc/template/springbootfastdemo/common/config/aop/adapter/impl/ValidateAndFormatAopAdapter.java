package com.ninggc.template.springbootfastdemo.common.config.aop.adapter.impl;

import com.ninggc.template.springbootfastdemo.common.config.aop.AopAdapter;
import com.ninggc.template.springbootfastdemo.common.config.aop.adapter.IAopAdapter;
import org.aspectj.lang.JoinPoint;

/**
 * 参数校验的adapter
 */
@AopAdapter
public class ValidateAndFormatAopAdapter implements IAopAdapter {
    @Override
    public void doBefore(JoinPoint joinPoint, String[] parameterNames, Object[] args) {
//        for (Object arg : args) {
//            for (Field field : arg.getClass().getDeclaredFields()) {
//                if (field.getAnnotation(NotNull.class) != null) {
//                    try {
//                        field.setAccessible(true);
//                        Assert.notNull(field.get(arg), field.getName() + " cannot be null!");
//                    } catch (IllegalAccessException ignored) { }
//                }
//            }
//            if (arg instanceof Valid) {
//                ((Valid) arg).validate();
//            }
//        }
    }

    @Override
    public Object doAfterReturn(JoinPoint joinPoint, Object returnValue) {
//        if (returnValue instanceof IVO) {
//            ReturnMsg<Object> returnMsg = new ReturnMsg<>();
//            Map<String, Object> format = null;
//            try {
//                format = ((IVO) returnValue).format();
//            } catch (IllegalAccessException e) {
//                error(e.getMessage());
//                returnMsg.setResult(returnValue);
//            }
//            returnMsg.setResult(format);
//            return returnMsg;
//        }
        return null;
    }

    @Override
    public void doAfterThrow(JoinPoint joinPoint, Exception exception) {

    }
}
