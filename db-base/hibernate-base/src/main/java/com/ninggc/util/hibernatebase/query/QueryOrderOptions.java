package com.ninggc.util.hibernatebase.query;

/**
 * <p>  Created by LXL on 2017-08-01  </p>
 */
public enum QueryOrderOptions {
    ASC("asc", "升序排列"),
    DESC("desc", "降序排列");


    private String order; // 排序方式
    private String info;  // 排序方式的含义


    QueryOrderOptions(String order, String info) {
        this.order = order;
        this.info = info;
    }

    /**
     * Created by LXL on 2017-07-27
     * <br> 获取排序方式
     * @return 排序方式
     */
    public String order() {
        return order;
    }

    /**
     * Created by LXL on 2017-07-27
     * <br> 获取对应排序方式的含义
     * @return 排序方式的含义
     */
    public String info() {
        return info;
    }
}

