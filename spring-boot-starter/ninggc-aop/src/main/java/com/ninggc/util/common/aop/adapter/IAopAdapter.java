package com.ninggc.util.common.aop.adapter;

import com.ninggc.util.common.aop.util.IUtilLogger;
import org.aspectj.lang.JoinPoint;

/**
 * 这里推荐只是用doAround的实现
 */
public interface IAopAdapter extends IUtilLogger {
    void doBefore(JoinPoint joinPoint, String[] parameterNames, Object[] args);

    Object doAfterReturn(JoinPoint joinPoint, final Object returnValue);

    void doAfterThrow(JoinPoint joinPoint, Exception exception);

    /**
     * adapter可以通过该方法在初始化的时候获取AopConfiguration的属性
     * 如果有特殊的需求可以在这里实现
     *
     * @param objects
     */
    default void initAopAdapter(Object... objects) {
    }
}
