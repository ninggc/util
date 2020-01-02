package com.ninggc.template.springbootfastdemo.common.config.aop.adapter.impl;

import com.ninggc.template.springbootfastdemo.common.config.aop.AopAdapter;
import com.ninggc.template.springbootfastdemo.common.config.aop.adapter.IAopAdapter;
import org.aspectj.lang.JoinPoint;

@AopAdapter
public class JustTestAopAdapter implements IAopAdapter {

    private StackTraceElement getMethodInfo() {
        return Thread.currentThread().getStackTrace()[2];
    }

    @Override
    public void doBefore(JoinPoint joinPoint, String[] parameterNames, Object[] args) {
        info(getMethodInfo().getMethodName());
    }

    @Override
    public Object doAfterReturn(JoinPoint joinPoint, Object returnValue) {
        info(getMethodInfo().getMethodName());
        return null;
    }

    @Override
    public void doAfterThrow(JoinPoint joinPoint, Exception exception) {
        info(getMethodInfo().getMethodName());
    }
}
