package com.ninggc.template.springbootfastdemo.common.config.handler;

import com.google.gson.Gson;
import com.ninggc.common.web.ResponseData;
import com.ninggc.template.springbootfastdemo.common.util.IUtilGson;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// 标记是对controller的advice
@RestControllerAdvice
public class ControllerExceptionHandler implements IUtilGson {
    // 标记匹配的异常
    @ExceptionHandler(value = NullPointerException.class)
    public ResponseData<Object> handException(NullPointerException e) {
        // 在这里可以对相应的异常进行封装
        e.printStackTrace();
        StackTraceElement[] stackTrace = e.getStackTrace();
        return ResponseData.buildFailed(gson.toJson(new StackTraceElement[]{stackTrace[0], stackTrace[1], stackTrace[2]}));
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseData<Object> handException(Exception e) {
        // 在这里可以对相应的异常进行封装
        e.printStackTrace();
        return ResponseData.buildFailed(e.getMessage());
    }
}
