package com.ninggc.template.springbootfastdemo.config.common;

import com.google.gson.Gson;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommonConfiguration {
    @Bean
    public Gson gson() {
        return new Gson();
    }
}