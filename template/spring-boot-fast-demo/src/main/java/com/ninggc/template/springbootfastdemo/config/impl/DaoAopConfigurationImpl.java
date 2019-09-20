package com.ninggc.template.springbootfastdemo.config.impl;

import com.ninggc.template.springbootfastdemo.config.AopConfiguration;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * 控制controller的函数的入口和出口处打印日志
 */
@Component
@Aspect
public class DaoAopConfigurationImpl extends AopConfiguration {

    @Pointcut("execution(* com.ninggc.template.springbootfastdemo.dao.DaoFactory.*..*(..))")
    protected void pointCutMethod() {
    }

    @Override
    public String getTag() {
        return "dao";
    }
}
