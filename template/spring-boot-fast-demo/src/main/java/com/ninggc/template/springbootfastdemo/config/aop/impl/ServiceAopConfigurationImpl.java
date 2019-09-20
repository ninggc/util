package com.ninggc.template.springbootfastdemo.config.aop.impl;

import com.ninggc.template.springbootfastdemo.config.aop.AopConfiguration;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * 控制controller的函数的入口和出口处打印日志
 */
@Component
@Aspect
public class ServiceAopConfigurationImpl extends AopConfiguration {
    @Pointcut("execution(* com.ninggc.template.springbootfastdemo.service.impl.*..*(..))")
    protected void pointCutMethod() {
    }

    @Override
    public String getTag() {
        return "service";
    }

    @Override
    public void before(JoinPoint joinPoint, String[] parameterNames, Object[] args) {

    }

    @Override
    public void afterReturn(JoinPoint joinPoint, Object returnValue) {

    }

    @Override
    public void afterThrow(JoinPoint joinPoint, Exception exception) throws Exception {

    }
}
