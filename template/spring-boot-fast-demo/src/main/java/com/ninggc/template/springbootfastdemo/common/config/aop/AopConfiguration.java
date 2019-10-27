package com.ninggc.template.springbootfastdemo.common.config.aop;

import com.ninggc.template.springbootfastdemo.common.config.aop.adapter.IAopAdapter;
import com.ninggc.template.springbootfastdemo.common.util.IUtilGson;
import com.ninggc.template.springbootfastdemo.common.util.IUtilLogger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        // 注册所有的adapter：在切面实现多项自定义功能
        Map<String, Object> beansWithAnnotation = event.getApplicationContext().getBeansWithAnnotation(AopAdapter.class);
        for (Object value : beansWithAnnotation.values()) {
            if (value instanceof IAopAdapter) {
                // 排除有ExcludeAopAdapter标记
                ExcludeAopAdapter excludeAopAdapter = this.getClass().getAnnotation(ExcludeAopAdapter.class);
                boolean exclude = false;
                if (null != excludeAopAdapter) {
                    for (Class<? extends IAopAdapter> excludeClass : excludeAopAdapter.excludeClasses()) {
                        if (value.getClass().equals(excludeClass)) {
                            exclude = true;
                            break;
                        }
                    }
                }
                if (!exclude) {
                    IAopAdapter adapter = (IAopAdapter) value;
                    adapter.setAopLoggerHandler(this);
                    adapters.add(adapter);
                }
            }
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
                log(classAndMethodName + " execute time: " + " --> " + duration + " ms");
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
