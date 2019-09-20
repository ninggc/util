package com.ninggc.template.springbootfastdemo.config.aop.adapter.impl;

import com.ninggc.template.springbootfastdemo.config.IGson;
import com.ninggc.template.springbootfastdemo.config.aop.ILoggerInfoHandler;
import com.ninggc.template.springbootfastdemo.config.aop.adapter.IAopLogAdapter;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Component;

@Component
public class DefaultAopLogAdapter implements IAopLogAdapter, ILoggerInfoHandler, IGson {
    @Override
    public String getTag() {
        return "default";
    }

    @Override
    public void doBefore(JoinPoint joinPoint, String[] parameterNames, Object[] args) {
//        before(joinPoint, parameterNames, args);
        String classAndMethodName = joinPoint.getSignature().toShortString();

        String logContent = "before " + getTag() + " execute: " + classAndMethodName + "\t" +
                gson.toJson(parameterNames) + " --> " + gson.toJson(args);
        log(logContent);
    }

    @Override
    public Object doAfterReturn(JoinPoint joinPoint, Object returnValue) {
//        afterReturn(joinPoint, returnValue);
        String classAndMethodName = joinPoint.getSignature().toShortString();

        String logContent = "after " + getTag() + " execute: " + classAndMethodName + "\t"
                + "result --> " + gson.toJson(getResultToAopResult(returnValue));
        log(logContent);
        return returnValue;
    }

    @Override
    public void doAfterThrow(JoinPoint joinPoint, Exception exception) throws Exception {
//        afterThrow(joinPoint, exception);
        String classAndMethodName = joinPoint.getSignature().toShortString();
        String logContent = "after " + getTag() + " execute: " + classAndMethodName + "\t"
                + "throw --> " + gson.toJson(exception.getMessage());
        log(logContent);
        throw exception;
    }

    @Override
    public Object doAround(ProceedingJoinPoint joinPoint, String[] parameterNames, Object[] args) throws Throwable {
        String classAndMethodName = joinPoint.getSignature().toShortString();
        Object returnValue = null;

//        执行切点
        try {
            doBefore(joinPoint, parameterNames, args);

            long start = System.currentTimeMillis();
            returnValue = joinPoint.proceed(args);
            long duration = System.currentTimeMillis() - start;
            if (duration > getTimeThreshold()) {
                warn( classAndMethodName + " execute time is too long: " + " --> " + duration + " ms");
            } else {
                log(classAndMethodName + " execute time: " + " --> " + duration + " ms");
            }

            doAfterReturn(joinPoint, returnValue);
        } catch (Exception e) {
            doAfterThrow(joinPoint, e);
            throw e;
        }
        return returnValue;
    }
}
