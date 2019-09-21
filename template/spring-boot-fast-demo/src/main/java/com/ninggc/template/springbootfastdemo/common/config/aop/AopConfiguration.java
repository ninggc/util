package com.ninggc.template.springbootfastdemo.common.config.aop;

import com.ninggc.template.springbootfastdemo.common.config.aop.adapter.IAopAdapter;
import com.ninggc.template.springbootfastdemo.common.util.IUtilGson;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.reflect.MethodSignature;

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
public abstract class AopConfiguration implements IUtilGson, IAopLoggerHandler {
    private IAopAdapter adapter;

    public AopConfiguration(IAopAdapter aopLogAdapter) {
        this.adapter = aopLogAdapter;
//        给适配器注册aopLogAdapter（即标签重写函数）
        aopLogAdapter.setAopLoggerHandler(this);
    }

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
     */
//    @Around(value = "pointCutMethod() && @annotation(methodLog)", argNames = "joinPoint")
    @Around(value = "pointCutMethod()", argNames = "joinPoint")
    protected Object wrapAround(ProceedingJoinPoint joinPoint) throws Throwable {
        String[] parameterNames = ((MethodSignature) joinPoint.getSignature()).getParameterNames();
        Object[] args = joinPoint.getArgs();
        return adapter.doAround(joinPoint, parameterNames, args);
    }
}
