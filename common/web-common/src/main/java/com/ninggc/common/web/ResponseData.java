package com.ninggc.common.web;

import lombok.Data;
import lombok.experimental.Accessors;

@Data @Accessors(chain = true)
public class ResponseData<T> {
    private boolean success;
    private String message; //消息
    private int code;
    private T result; //返回的数据

    /**
     * 时间戳
     */
    private final long timestamp = System.currentTimeMillis();

    public ResponseData() {
        this.success = true;
        this.code = 200;
    }

    public static <T> ResponseData<T> buildSuccess(T t) {
        return new ResponseData<T>().setResult(t);
    }

    public static <T> ResponseData<T> buildFailed(String msg) {
        return new ResponseData<T>()
                .setSuccess(false)
                .setCode(-1)
                .setMessage(msg);
    }
}