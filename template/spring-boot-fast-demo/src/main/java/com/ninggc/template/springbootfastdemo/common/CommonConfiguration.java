package com.ninggc.template.springbootfastdemo.common;

import com.google.gson.Gson;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

@Configuration
public class CommonConfiguration {
    @Bean
    public Gson gson() {
        return new Gson();
    }

    @Bean
    public ThreadLocal<DateFormat> simpleDateFormat() {
        return ThreadLocal.withInitial(() -> new SimpleDateFormat("yy-MM-dd HH:mm:ss.sss"));
    }
}
