package com.ninggc.template.springbootfastdemo.config.aop.adapter;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;

public interface IAopLogAdapter {
    void doBefore(JoinPoint joinPoint, String[] parameterNames, Object[] args);

    Object doAfterReturn(JoinPoint joinPoint, final Object returnValue);

    void doAfterThrow(JoinPoint joinPoint, Exception exception) throws Exception;

    Object doAround(ProceedingJoinPoint joinPoint, String[] parameterNames, Object[] args) throws Throwable;
}
