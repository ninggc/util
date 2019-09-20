package com.ninggc.util.morphia.util;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class Page<T> {
    private Integer pageSize;
    private Integer pageNumber;
    private Long count;
    private List<T> list;
}
