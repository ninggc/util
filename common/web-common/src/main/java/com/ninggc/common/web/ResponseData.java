package com.ninggc.common.web;

// import lombok.Data;
// import lombok.experimental.Accessors;

// @Data @Accessors(chain = true)
public class ResponseData<T> {
    private boolean success;
    private String message; //消息
    private int code;
    private T result; //返回的数据

    /**
     * 时间戳
     */
    private final long timestamp = System.currentTimeMillis();

    /**
     * 不允许通过构造函数建立对象
     */
    private ResponseData() {
        this.success = true;
        this.message = "";
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

    public boolean isSuccess() {
        return success;
    }

    public ResponseData<T> setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public ResponseData<T> setMessage(String message) {
        this.message = message;
        return this;
    }

    public int getCode() {
        return code;
    }

    public ResponseData<T> setCode(int code) {
        this.code = code;
        return this;
    }

    public T getResult() {
        return result;
    }

    public ResponseData<T> setResult(T result) {
        this.result = result;
        return this;
    }

    public long getTimestamp() {
        return timestamp;
    }
}