package com.ninggc.template.springbootfastdemo.common.config.aop.adapter.impl;

import com.ninggc.template.springbootfastdemo.common.config.aop.AopAdapter;
import com.ninggc.template.springbootfastdemo.common.config.aop.logger.IAopLoggerHandler;
import com.ninggc.template.springbootfastdemo.common.config.aop.adapter.IAopAdapter;
import com.ninggc.template.springbootfastdemo.common.util.IUtilGson;
import org.aspectj.lang.JoinPoint;

@AopAdapter
public class DefaultAopLogAdapter implements IAopAdapter, IUtilGson {
    private IAopLoggerHandler aopLoggerHandler;

    @Override
    public void doBefore(JoinPoint joinPoint, String[] parameterNames, Object[] args) {
//        before(joinPoint, parameterNames, args);
        String classAndMethodName = joinPoint.getSignature().toShortString();

        String logContent = "——————" + "before " + aopLoggerHandler.getTag() + " execute: " + classAndMethodName + "——————";
        logContent += gson.toJson(parameterNames) + " --> " + gson.toJson(args);
        log(logContent);
    }

    @Override
    public Object doAfterReturn(JoinPoint joinPoint, Object returnValue) {
//        afterReturn(joinPoint, returnValue);
        String classAndMethodName = joinPoint.getSignature().toShortString();

        String logContent = "——————" + "after " + aopLoggerHandler.getTag() + " execute: " + classAndMethodName + "——————";
        logContent += " result --> " + gson.toJson(aopLoggerHandler.getResultToAopResult(returnValue));
        log(logContent);
        return null;
    }

    @Override
    public void doAfterThrow(JoinPoint joinPoint, Exception exception) throws Exception {
//        afterThrow(joinPoint, exception);
        String classAndMethodName = joinPoint.getSignature().toShortString();
        String logContent = "——————" + "after " + aopLoggerHandler.getTag() + " throw: " + classAndMethodName + "——————";
        logContent += " throw --> " + gson.toJson(exception.getMessage());
        log(logContent);
        throw exception;
    }

    @Override
    public void setAopLoggerHandler(IAopLoggerHandler aopLoggerHandler) {
        this.aopLoggerHandler = aopLoggerHandler;
    }
}
