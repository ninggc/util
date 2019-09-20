package com.ninggc.template.springbootfastdemo.config.impl;

import com.ninggc.template.springbootfastdemo.config.AopConfiguration;
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
}
