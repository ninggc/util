package com.ninggc.template.springbootfastdemo.common.config.aop;

import com.ninggc.template.springbootfastdemo.common.config.aop.adapter.IAopAdapter;
import org.springframework.context.annotation.Scope;
import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
/**
 * 标记具体的切面不执行的adapter
 */
public @interface ExcludeAopAdapter {
    Class<? extends IAopAdapter>[] excludeClasses();
}
