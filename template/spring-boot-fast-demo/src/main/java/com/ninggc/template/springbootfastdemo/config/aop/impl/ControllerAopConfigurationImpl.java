package com.ninggc.template.springbootfastdemo.config.aop.impl;

import com.ninggc.template.springbootfastdemo.config.aop.AopConfiguration;
import com.ninggc.template.springbootfastdemo.config.aop.IAopLoggerHandler;
import com.ninggc.template.springbootfastdemo.config.aop.adapter.IAopLogAdapter;
import com.ninggc.template.springbootfastdemo.config.aop.adapter.impl.DefaultAopLogAdapter;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * 控制controller的函数的入口和出口处打印日志
 */
@Component
@Aspect
public class ControllerAopConfigurationImpl extends AopConfiguration {
    public ControllerAopConfigurationImpl(IAopLogAdapter aopLogAdapter) {
        super(aopLogAdapter);
    }

    @Pointcut("execution(* com.ninggc.template.springbootfastdemo.web.controller.*..*(..))")
    @Override
    protected void pointCutMethod() {
    }

    @Override
    public String getTag() {
        return "controller";
    }
}
