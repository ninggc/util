package com.ninggc.template.springbootfastdemo.common.config.aop.action.logger;

import com.ninggc.util.autogenerate.AutoGeneration;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
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
