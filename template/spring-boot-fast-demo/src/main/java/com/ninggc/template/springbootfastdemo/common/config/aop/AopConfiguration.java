package com.ninggc.template.springbootfastdemo.common.config.aop;

import com.ninggc.template.springbootfastdemo.common.config.aop.adapter.IAopAdapter;
import com.ninggc.template.springbootfastdemo.common.config.aop.logger.IAopLoggerHandler;
import com.ninggc.template.springbootfastdemo.common.util.IUtilGson;
import com.ninggc.template.springbootfastdemo.common.util.IUtilLogger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.*;

/**
 * 控制切面的自定义操作，
 * 使用方式如下（基于注解）：
 * 1. 重写pointCut方法并定义切点
 * 2. 重写对应方法方法进入执行内容
 * 使用方式如下（基于xml）：
 * 1. 继承该类
 * 2. 配置切点
 * 3. 配置wrap方法为对应的before、after等
 */
@SuppressWarnings("unused")
@Component
public abstract class AopConfiguration implements IUtilGson, IUtilLogger, IAopLoggerHandler, ApplicationListener<ContextRefreshedEvent> {
    private List<IAopAdapter> adapters = new ArrayList<>();

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        // 对该实例的切面配上adapter
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
    }

    /**
     * 需要重写并定义切点
     */
    protected abstract void pointCutMethod();

    /**
     * 包裹before函数
     */
//    @Before("pointCutMethod()")
    public final void wrapBefore(JoinPoint joinPoint) {
        String[] parameterNames = ((MethodSignature) joinPoint.getSignature()).getParameterNames();
        Object[] args = joinPoint.getArgs();
        for (IAopAdapter adapter : adapters) {
            adapter.doBefore(joinPoint, parameterNames, args);
        }
    }

    /**
     * 包裹afterReturn函数
     */
//    @AfterReturning(value = "pointCutMethod()", returning = "returnValue")
    public final Object wrapAfterReturn(JoinPoint joinPoint, Object returnValue) {
        Object resultValue = null;
        for (IAopAdapter adapter : adapters) {
            Object o = adapter.doAfterReturn(joinPoint, returnValue);
            if (null != o) {
                resultValue = o;
            }
        }
        return resultValue;
    }

    /**
     * 包裹afterThrow函数
     */
//    @AfterThrowing(value = "pointCutMethod()", throwing = "exception")
    public final void wrapAfterThrow(JoinPoint joinPoint, Exception exception) throws Exception {
        for (IAopAdapter adapter : adapters) {
            adapter.doAfterThrow(joinPoint, exception);
        }
    }

    /**
     * 包裹around函数
     */
//    @Around(value = "pointCutMethod() && @annotation(methodLog)", argNames = "joinPoint")
    @Around(value = "pointCutMethod()", argNames = "joinPoint")
    protected Object wrapAround(ProceedingJoinPoint joinPoint) throws Throwable {
        String classAndMethodName = joinPoint.getSignature().toShortString();
        String[] parameterNames = ((MethodSignature) joinPoint.getSignature()).getParameterNames();
        Object[] args = joinPoint.getArgs();
        Object returnValue = null;

        // 执行切点
        try {
            // before
            wrapBefore(joinPoint);
            long start = System.currentTimeMillis();
            // proceed
            returnValue = joinPoint.proceed(args);
            long duration = System.currentTimeMillis() - start;
            if (duration > getTimeThreshold()) {
                warn(classAndMethodName + " execute time is too long: " + " --> " + duration + " ms");
            } else {
                info(classAndMethodName + " execute time: " + " --> " + duration + " ms");
            }
            // after
            Object o = wrapAfterReturn(joinPoint, returnValue);
            if (null != o) {
                returnValue = o;
            }
        } catch (Exception e) {
            // after
            wrapAfterThrow(joinPoint, e);
            throw e;
        }
        return returnValue;
    }
}
