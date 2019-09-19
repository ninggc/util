package com.ninggc.template.springbootfastdemo.config.impl;

import com.ninggc.template.springbootfastdemo.config.AopConfiguration;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

/**
 * 控制controller的函数的入口和出口处打印日志
 */
@Component
@Aspect
public class DaoAopConfigurationImpl extends AopConfiguration {

    @Pointcut("execution(* com.ninggc.template.springbootfastdemo.dao.DaoFactory.*..*(..))")
    private void pointCutMethod() {
    }

    @Before("pointCutMethod()")
    @Override
    public void before(JoinPoint joinPoint) {
        String[] parameterNames = ((MethodSignature) joinPoint.getSignature()).getParameterNames();
        Object[] args = joinPoint.getArgs();
        String classAndMethodName = joinPoint.getSignature().toShortString();

        String logContent = "before dao execute: " + classAndMethodName + "\t" +
                gson.toJson(parameterNames) + " --> " + gson.toJson(args);
        log(logContent);
    }

    @AfterReturning(value = "pointCutMethod()", returning = "returnValue")
    @Override
    public Object afterReturn(JoinPoint joinPoint, Object returnValue) {
        String classAndMethodName = joinPoint.getSignature().toShortString();

        String logContent = "after dao execute: " + classAndMethodName + "\t"
                + "result --> " + gson.toJson(getResultToAopResult(returnValue));
        log(logContent);
        return returnValue;
    }

    @AfterThrowing(value = "pointCutMethod()", throwing = "exception")
    @Override
    public void afterThrow(JoinPoint joinPoint, Exception exception) throws Exception {
        String classAndMethodName = joinPoint.getSignature().toShortString();
        String logContent = "after dao execute: " + classAndMethodName + "\t"
                + "throw --> " + gson.toJson(exception.getMessage());
        log(logContent);
        throw exception;
    }

    @Override
    public void around(ProceedingJoinPoint joinPoint) throws Throwable {
//        System.out.println("before");
//        Object[] args = joinPoint.getArgs();
//        String[] parameterNames = ((MethodSignature) joinPoint.getSignature()).getParameterNames();
//
//        Object proceed = joinPoint.proceed(args);
//
//        System.out.println("after");
    }
}
