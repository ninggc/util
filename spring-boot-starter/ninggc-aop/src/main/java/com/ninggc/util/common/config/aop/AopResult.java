package com.ninggc.util.common.config.aop;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class AopResult {
    private String explain;
    private String type;
    private Integer totalSize;
    private Integer totalLength;
    private List<Object> subList;
}
