package com.ninggc.template.springbootfastdemo.config.aop;

import org.aspectj.lang.JoinPoint;

public interface IAopConfiguration {
    void before(JoinPoint joinPoint, String[] parameterNames, Object[] args);

    void afterReturn(JoinPoint joinPoint, final Object returnValue);

    void afterThrow(JoinPoint joinPoint, Exception exception) throws Exception;
}
