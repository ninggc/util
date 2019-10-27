package com.ninggc.template.springbootfastdemo.common.security;

/**
 * @description 验证字段是否合法
 * @author Ninggc
 * @create 2019-10-11 14:50
 */
public interface Valid {
    void validate() throws IllegalArgumentException;
}
