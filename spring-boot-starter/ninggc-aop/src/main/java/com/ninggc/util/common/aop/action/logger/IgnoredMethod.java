package com.ninggc.util.common.aop.action.logger;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author ninggc
 * @apiNote 该注解表明这个方法不需要打印日志
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface IgnoredMethod {
    /**
     * 方法内部有打印重要日志吗？
     *
     * @return true: 有 | false: 没有
     */
    boolean hasLog() default false;
}
