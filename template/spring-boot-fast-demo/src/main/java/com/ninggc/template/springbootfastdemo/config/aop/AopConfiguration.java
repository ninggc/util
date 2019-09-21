package com.ninggc.template.springbootfastdemo.config.aop;

import com.google.gson.Gson;
import com.ninggc.template.springbootfastdemo.config.IGson;
import com.ninggc.template.springbootfastdemo.config.aop.adapter.IAopLogAdapter;
import lombok.Data;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

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
public abstract class AopConfiguration implements IGson, IAopLoggerHandler {
    private IAopLogAdapter adapter;

    public AopConfiguration(IAopLogAdapter aopLogAdapter) {
        this.adapter = aopLogAdapter;
        aopLogAdapter.setAopLoggerHandler(this);
    }

    private Logger logger = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);

    /**
     *  需要重写并定义切点
     */
    protected abstract void pointCutMethod();

    /**
     * 包裹before函数
     */
//    @Before("pointCutMethod()")
    public final void wrapBefore(JoinPoint joinPoint) {
        String[] parameterNames = ((MethodSignature) joinPoint.getSignature()).getParameterNames();
        Object[] args = joinPoint.getArgs();
        adapter.doBefore(joinPoint, parameterNames, args);
    }

    /**
     * 包裹afterReturn函数
     */
//    @AfterReturning(value = "pointCutMethod()", returning = "returnValue")
    public final Object wrapAfterReturn(JoinPoint joinPoint, Object returnValue) {
        return adapter.doAfterReturn(joinPoint, returnValue);
    }

    /**
     * 包裹afterThrow函数
     */
//    @AfterThrowing(value = "pointCutMethod()", throwing = "exception")
    public final void wrapAfterThrow(JoinPoint joinPoint, Exception exception) throws Exception {
        adapter.doAfterThrow(joinPoint, exception);
    }

    /**
     * 包裹around函数
     * @return
     */
//    @Around(value = "pointCutMethod() && @annotation(methodLog)", argNames = "joinPoint")
    @Around(value = "pointCutMethod()", argNames = "joinPoint")
    protected Object wrapAround(ProceedingJoinPoint joinPoint) throws Throwable {
        String[] parameterNames = ((MethodSignature) joinPoint.getSignature()).getParameterNames();
        Object[] args = joinPoint.getArgs();
        return adapter.doAround(joinPoint, parameterNames, args);
    }
}
