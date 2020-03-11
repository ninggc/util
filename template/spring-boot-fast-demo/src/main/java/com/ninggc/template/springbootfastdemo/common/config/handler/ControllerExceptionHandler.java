package com.ninggc.template.springbootfastdemo.common.config.handler;

import com.ninggc.common.web.ResponseData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author ninggc
 * 标记是对controller的advice
 */
@RestControllerAdvice
public class ControllerExceptionHandler {
    Logger logger = LogManager.getLogger(ControllerExceptionHandler.class);

    /**
     * 标记匹配的异常
     */
    @ExceptionHandler(value = NullPointerException.class)
    public ResponseData<Object> handException(NullPointerException e) {
        // 在这里可以对相应的异常进行封装
        logger.error(e.getMessage(), e);
        return ResponseData.buildFailed(e.getMessage());
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseData<Object> handException(Exception e) {
        // 在这里可以对相应的异常进行封装
        logger.error(e.getMessage(), e);
        return ResponseData.buildFailed(e.getMessage());
    }
}
