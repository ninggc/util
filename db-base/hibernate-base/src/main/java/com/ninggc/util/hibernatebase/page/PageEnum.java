//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ninggc.util.hibernatebase.page;

public enum PageEnum {
    NUMBER(1, "起始页页码"),
    SIZE(10, "每页记录数");

    private int value;
    private String info;

    private PageEnum(int value, String info) {
        this.value = value;
        this.info = info;
    }

    public int value() {
        return this.value;
    }

    public String info() {
        return this.info;
    }
}
