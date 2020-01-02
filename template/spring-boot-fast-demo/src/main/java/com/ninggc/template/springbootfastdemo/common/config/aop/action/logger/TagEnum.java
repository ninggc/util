package com.ninggc.template.springbootfastdemo.common.config.aop.action.logger;

public enum TagEnum {
    CONTROLLER("controller"),
    SERVICE("service"),
    DAO("dao")
    ;

    private String value;

    TagEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
