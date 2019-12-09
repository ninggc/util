package com.ninggc.template.springbootfastdemo.project.config.aop;

import com.ninggc.template.springbootfastdemo.common.config.aop.AopAdapterConfig;
import com.ninggc.template.springbootfastdemo.common.config.aop.AopConfiguration;
import com.ninggc.template.springbootfastdemo.common.config.aop.adapter.IAopAdapter;
import com.ninggc.template.springbootfastdemo.common.config.aop.adapter.impl.LoggerAopAdapter;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * 控制controller的函数的入口和出口处打印日志
 */
@Component
@Aspect
@AopAdapterConfig(acceptAdapters = LoggerAopAdapter.class)
public class DaoAopLoggerConfigurationImpl extends AopConfiguration {
    @Pointcut("execution(* com.ninggc.template.springbootfastdemo.project.dao.DaoFactory.*..*(..))")
    protected void pointCutMethod() { }

    @Override
    public String getTag() {
        return "dao";
    }

}
