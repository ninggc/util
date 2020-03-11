package com.ninggc.template.springbootfastdemo.common.config.aop.action.logger;

import java.util.List;

public class AopResult {
    private String explain;
    private String type;
    private Integer totalSize;
    private Integer totalLength;
    private List<Object> subList;

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(Integer totalSize) {
        this.totalSize = totalSize;
    }

    public Integer getTotalLength() {
        return totalLength;
    }

    public void setTotalLength(Integer totalLength) {
        this.totalLength = totalLength;
    }

    public List<Object> getSubList() {
        return subList;
    }

    public void setSubList(List<Object> subList) {
        this.subList = subList;
    }
}
