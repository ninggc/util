package com.ninggc.template.springbootfastdemo.common.config.aop.adapter.impl;

import com.ninggc.template.springbootfastdemo.common.config.aop.AopAdapter;
import com.ninggc.template.springbootfastdemo.common.config.aop.logger.IAopLoggerHandler;
import com.ninggc.template.springbootfastdemo.common.config.aop.adapter.IAopAdapter;
import com.ninggc.template.springbootfastdemo.common.util.IUtilGson;
import org.aspectj.lang.JoinPoint;
import org.springframework.context.annotation.Scope;
import org.springframework.util.Assert;

@AopAdapter
@Scope("prototype")
public class LoggerAopAdapter implements IAopAdapter, IUtilGson {
    private IAopLoggerHandler aopLoggerHandler;

    @Override
    public void doBefore(JoinPoint joinPoint, String[] parameterNames, Object[] args) {
        String classAndMethodName = joinPoint.getSignature().toShortString();

        String logContent = "——————" + "before " + aopLoggerHandler.getTag() + " execute: " + classAndMethodName + "——————";
        logContent += gson.toJson(parameterNames) + " --> " + gson.toJson(args);
        info(logContent);
    }

    @Override
    public Object doAfterReturn(JoinPoint joinPoint, Object returnValue) {
        String classAndMethodName = joinPoint.getSignature().toShortString();

        String logContent = "——————" + "after " + aopLoggerHandler.getTag() + " execute: " + classAndMethodName + "——————";
        logContent += " result --> " + gson.toJson(aopLoggerHandler.getResultToAopResult(returnValue));
        info(logContent);
        return null;
    }

    @Override
    public void doAfterThrow(JoinPoint joinPoint, Exception exception) throws Exception {
        String classAndMethodName = joinPoint.getSignature().toShortString();
        String logContent = "——————" + "after " + aopLoggerHandler.getTag() + " throw: " + classAndMethodName + "——————";
        logContent += " throw --> " + gson.toJson(exception.getMessage());
        info(logContent);
        throw exception;
    }

    @Override
    public void initAopAdapter(Object... objects) {
        Assert.isTrue(objects.length == 1, "初始化" + this.getClass().getName() + "的参数不正确");
        this.aopLoggerHandler = (IAopLoggerHandler) objects[0];
    }
}
