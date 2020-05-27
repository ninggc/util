package com.ninggc.util.common.aop.adapter.anno;

import com.ninggc.util.common.aop.adapter.IAopAdapter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 在切面标记执行的adapter(可选)
 * 如果没有标记就默认是全部的adapter(只推荐在不新增adapter的情况下使用默认配置)
 *
 * @author ninggc
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface AopAdapterConfig {
    /**
     * 标注切面使用的adapter
     */
    Class<? extends IAopAdapter>[] acceptAdapters() default {};

    /**
     * 标注切面不使用的adapter
     * exclude只有在accept未设置的时候才生效
     */
    Class<? extends IAopAdapter>[] excludeAdapters() default {};
}
