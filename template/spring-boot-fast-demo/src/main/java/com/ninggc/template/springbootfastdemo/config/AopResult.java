package com.ninggc.template.springbootfastdemo.config;

import lombok.Data;

import java.util.List;

@Data
public class AopResult {
    private String explain;
    private String type;
    private Integer totalSize;
    private Integer totalLength;
    private List<Object> subList;
}
