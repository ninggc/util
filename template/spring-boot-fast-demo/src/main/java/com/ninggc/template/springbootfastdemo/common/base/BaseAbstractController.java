package com.ninggc.template.springbootfastdemo.common.base;

import com.google.gson.Gson;
import org.springframework.web.bind.annotation.RestController;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

@RestController
public abstract class BaseAbstractController {
    protected static Gson gson = new Gson();
    /**
     * 默认的日期格式化器，线程安全
     */
    protected ThreadLocal<DateFormat> dateFormat = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"));


}
