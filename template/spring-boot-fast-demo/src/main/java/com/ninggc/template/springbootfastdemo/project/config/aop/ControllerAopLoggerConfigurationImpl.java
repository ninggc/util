package com.ninggc.template.springbootfastdemo.project.config.aop;

import com.ninggc.template.springbootfastdemo.common.config.aop.AopConfiguration;
import com.ninggc.template.springbootfastdemo.common.config.aop.AopAdapterConfig;
import com.ninggc.template.springbootfastdemo.common.config.aop.adapter.impl.LoggerAopAdapter;
import com.ninggc.template.springbootfastdemo.common.config.aop.adapter.impl.ValidateAndFormatAopAdapter;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * 控制controller的函数的入口和出口处打印日志
 */
@Component
@Aspect
@AopAdapterConfig(acceptAdapters = {LoggerAopAdapter.class, ValidateAndFormatAopAdapter.class})
public class ControllerAopLoggerConfigurationImpl extends AopConfiguration {

//    @Pointcut("execution(* com.ninggc.template.springbootfastdemo.project.web.controller.*..*(..))")
    @Pointcut("(execution(* com.ninggc.template.springbootfastdemo.project.web..*.*(..))) " +
            "and (@within(org.springframework.stereotype.Controller))")
    @Override
    protected void pointCutMethod() {
    }

    @Override
    public String getTag() {
        return "controller";
    }
}
