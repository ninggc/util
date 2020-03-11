package com.ninggc.template.springbootfastdemo.common.config.aop.action.validate;

/**
 * @author Ninggc
 * @description 验证字段是否合法
 * @create 2019-10-11 14:50
 */
public interface Valid {
    void validate() throws IllegalArgumentException;
}
