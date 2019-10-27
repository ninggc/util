package com.ninggc.template.springbootfastdemo.common.config.aop.adapter;

import com.ninggc.template.springbootfastdemo.common.config.aop.IAopLoggerHandler;
import com.ninggc.template.springbootfastdemo.common.util.IUtilLogger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;

/**
 * 这里推荐只是用doAround的实现
 */
public interface IAopAdapter extends IAopLoggerHandler, IUtilLogger {
    void doBefore(JoinPoint joinPoint, String[] parameterNames, Object[] args);

    Object doAfterReturn(JoinPoint joinPoint, final Object returnValue);

    void doAfterThrow(JoinPoint joinPoint, Exception exception) throws Exception;

    void setAopLoggerHandler(IAopLoggerHandler aopLoggerHandler);
}
