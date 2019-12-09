package com.ninggc.template.springbootfastdemo.project.config.aop;

import com.ninggc.template.springbootfastdemo.common.config.aop.AopConfiguration;
import com.ninggc.template.springbootfastdemo.common.config.aop.AopAdapterConfig;
import com.ninggc.template.springbootfastdemo.common.config.aop.adapter.impl.JustTestAopAdapter;
import com.ninggc.template.springbootfastdemo.common.config.aop.adapter.impl.LoggerAopAdapter;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * 控制controller的函数的入口和出口处打印日志
 */
@Component
@Aspect
@AopAdapterConfig(acceptAdapters = LoggerAopAdapter.class)
public class ServiceAopLoggerConfigurationImpl extends AopConfiguration {

    @Pointcut("execution(* com.ninggc.template.springbootfastdemo.project.service.impl.*..*(..))")
    protected void pointCutMethod() { }

    @Override
    public String getTag() {
        return "service";
    }
}
