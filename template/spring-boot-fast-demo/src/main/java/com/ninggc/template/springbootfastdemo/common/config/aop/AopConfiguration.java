package com.ninggc.template.springbootfastdemo.common.config.aop;

import com.ninggc.template.springbootfastdemo.common.config.aop.adapter.IAopAdapter;
import com.ninggc.template.springbootfastdemo.common.config.aop.action.logger.IAopLoggerHandler;
import com.ninggc.template.springbootfastdemo.common.config.aop.adapter.impl.LoggerAopAdapter;
import com.ninggc.template.springbootfastdemo.common.config.aop.util.IUtilGson;
import com.ninggc.template.springbootfastdemo.common.config.aop.util.IUtilLogger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.*;

/**
 * 控制切面的自定义操作，
 *
 * 使用方式如下（基于注解）：
 * 1. 重写pointCut方法并定义切点
 * 2. 重写对应方法方法进入执行内容
 *
 * 使用方式如下（基于xml）：
 * 1. 继承该类
 * 2. 配置切点
 * 3. 配置wrapAround
 */
@Component
public abstract class AopConfiguration implements IUtilGson, IUtilLogger, IAopLoggerHandler, ApplicationListener<ContextRefreshedEvent> {
    // 这个logger打印非aop的日志
    Logger logger = LogManager.getLogger();

    protected Set<IAopAdapter> adapters = new HashSet<>();

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        // 对该实例的切面配上adapter
        try {
            if (this.getClass().getAnnotation(AopAdapterConfig.class) != null) {
                AopAdapterConfig aopAdapterConfig = this.getClass().getAnnotation(AopAdapterConfig.class);
                Class<? extends IAopAdapter>[] acceptAdapters = aopAdapterConfig.acceptAdapters();
                Class<? extends IAopAdapter>[] excludeAdapters = aopAdapterConfig.excludeAdapters();

                if (acceptAdapters.length > 0) {
                    for (Class<? extends IAopAdapter> adapter : acceptAdapters) {
                        adapters.add(event.getApplicationContext().getBean(adapter));
                    }
                } else {
                    // 如果没有配置accept, 就走exclude规则
                    HashSet<Object> excludeAdapterSet = new HashSet<>();
                    Collections.addAll(excludeAdapterSet, excludeAdapters);

                    Map<String, Object> allAopAdapters = event.getApplicationContext().getBeansWithAnnotation(AopAdapter.class);
                    for (Object adapter : allAopAdapters.values()) {
                        if (!excludeAdapterSet.remove(adapter.getClass())) {
                            Assert.isTrue(adapter instanceof IAopAdapter, adapter.getClass().getName() + "必须实现" + IAopAdapter.class.getName());
                            adapters.add((IAopAdapter) adapter);
                        }
                    }
                }
            } else {
                // 装配全部的adapter
                Map<String, Object> allAopAdapters = event.getApplicationContext().getBeansWithAnnotation(AopAdapter.class);
                for (Object adapter : allAopAdapters.values()) {
                    Assert.isTrue(adapter instanceof IAopAdapter, adapter.getClass().getName() + "必须实现" + IAopAdapter.class.getName());
                    adapters.add((IAopAdapter) adapter);
                }
            }

            // 按照定义的需求初始化adapter
            for (IAopAdapter adapter : adapters) {
                adapter.initAopAdapter(this);
            }
        } catch (Exception e) {
            error("aop处理出现异常: " + e.getMessage());
            logger.error("aop处理出现异常: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 如果是基于注解的项目需要重写该函数并定义切点
     * 基于xml配置的项目不用重写该函数
     */
    protected void pointCutMethod() { }

    /**
     * 包裹before函数
     */
    protected void wrapBefore(JoinPoint joinPoint) {
        try {
            String[] parameterNames = ((MethodSignature) joinPoint.getSignature()).getParameterNames();
            Object[] args = joinPoint.getArgs();
            for (IAopAdapter adapter : adapters) {
                adapter.doBefore(joinPoint, parameterNames, args);
            }
        } catch (Exception e) {
            if (e instanceof IllegalArgumentException) {
                throw e;
            }

            error("aop处理出现异常: " + e.getMessage());
            logger.error("aop处理出现异常: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 包裹afterReturn函数
     */
    protected Object wrapAfterReturn(JoinPoint joinPoint, Object returnValue) {
        try {
            Object resultValue = null;
            for (IAopAdapter adapter : adapters) {
                Object o = adapter.doAfterReturn(joinPoint, returnValue);
                if (null != o) {
                    resultValue = o;
                }
            }
            return resultValue;
        } catch (Exception e) {
            error("aop处理出现异常: " + e.getMessage());
            logger.error("aop处理出现异常: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 包裹afterThrow函数
     */
    protected void wrapAfterThrow(JoinPoint joinPoint, Exception exception) throws Exception {
        try {
            for (IAopAdapter adapter : adapters) {
                adapter.doAfterThrow(joinPoint, exception);
            }
        } catch (Exception e) {
            error("aop处理出现异常: " + e.getMessage());
            logger.error("aop处理出现异常: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 包裹around函数
     */
    @Around(value = "pointCutMethod()", argNames = "joinPoint")
    protected Object wrapAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();

        // 执行切点
        try {
            // before
            wrapBefore(joinPoint);
            // proceed
            Object returnValue = joinPoint.proceed(args);
            // after
            Object o = wrapAfterReturn(joinPoint, returnValue);
            if (null != o) {
                // 如果adapter return了非空的值，就改写返回值
                returnValue = o;
            }
            return returnValue;
        } catch (Exception e) {
            // after
            wrapAfterThrow(joinPoint, e);
            throw e;
        }
    }
}
