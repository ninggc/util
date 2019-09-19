package com.ninggc.template.springbootfastdemo.util;

import lombok.Data;

import java.util.List;

@Data
public class Page<T> {
    private Integer pageSize;
    private Integer pageLength;
    private Integer count;
    private List<T> list;

    public List<T> getAsList() {
        return list;
    }
}
