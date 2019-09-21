package com.ninggc.template.springbootfastdemo.common.config.aop.adapter;

import com.ninggc.template.springbootfastdemo.common.config.aop.IAopLoggerHandler;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;

public interface IAopAdapter {
    void doBefore(JoinPoint joinPoint, String[] parameterNames, Object[] args);

    Object doAfterReturn(JoinPoint joinPoint, final Object returnValue);

    void doAfterThrow(JoinPoint joinPoint, Exception exception) throws Exception;

    Object doAround(ProceedingJoinPoint joinPoint, String[] parameterNames, Object[] args) throws Throwable;

    void setAopLoggerHandler(IAopLoggerHandler aopLoggerHandler);
}
