package com.ninggc.template.springbootfastdemo.common.config.aop.adapter.impl;

import com.ninggc.template.springbootfastdemo.common.config.aop.AopAdapter;
import com.ninggc.template.springbootfastdemo.common.config.aop.IAopLoggerHandler;
import com.ninggc.template.springbootfastdemo.common.config.aop.adapter.IAopAdapter;
import org.aspectj.lang.JoinPoint;

@AopAdapter
public class JustTestAopAdapter implements IAopAdapter {
    @Override
    public void doBefore(JoinPoint joinPoint, String[] parameterNames, Object[] args) {
        System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Override
    public Object doAfterReturn(JoinPoint joinPoint, Object returnValue) {
        System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
        return null;
    }

    @Override
    public void doAfterThrow(JoinPoint joinPoint, Exception exception) throws Exception {
        System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Override
    public void setAopLoggerHandler(IAopLoggerHandler aopLoggerHandler) {
        System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
    }
}
