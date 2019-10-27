package com.ninggc.template.springbootfastdemo.common.config.aop.adapter.impl;

import com.ninggc.template.springbootfastdemo.common.config.aop.AopAdapter;
import com.ninggc.template.springbootfastdemo.common.config.aop.logger.IAopLoggerHandler;
import com.ninggc.template.springbootfastdemo.common.config.aop.adapter.IAopAdapter;
import com.ninggc.template.springbootfastdemo.common.security.Valid;
import org.aspectj.lang.JoinPoint;

/**
 * 参数校验的adapter
 */
@AopAdapter
public class ValidateAopAdapter implements IAopAdapter {
    @Override
    public void doBefore(JoinPoint joinPoint, String[] parameterNames, Object[] args) {
        for (Object arg : args) {
            if (arg instanceof Valid) {
                ((Valid) arg).validate();
            }
        }
    }

    @Override
    public Object doAfterReturn(JoinPoint joinPoint, Object returnValue) {
        return null;
    }

    @Override
    public void doAfterThrow(JoinPoint joinPoint, Exception exception) throws Exception {

    }

    @Override
    public void setAopLoggerHandler(IAopLoggerHandler aopLoggerHandler) {

    }
}
