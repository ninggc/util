package com.ninggc.template.springbootfastdemo.project.config.aop;

import com.ninggc.template.springbootfastdemo.common.config.aop.AopConfiguration;
import com.ninggc.template.springbootfastdemo.common.config.aop.adapter.IAopAdapter;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * 控制controller的函数的入口和出口处打印日志
 */
@Component
@Aspect
public class ControllerAopLoggerConfigurationImpl extends AopConfiguration {
    public ControllerAopLoggerConfigurationImpl(IAopAdapter aopLogAdapter) {
        super(aopLogAdapter);
    }

    @Pointcut("execution(* com.ninggc.template.springbootfastdemo.project.web.controller.*..*(..))")
    @Override
    protected void pointCutMethod() {
    }

    @Override
    public String getTag() {
        return "controller";
    }
}
