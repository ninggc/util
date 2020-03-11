package com.ninggc.util.common.config;

import com.google.gson.Gson;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ninggc
 */
@Configuration
public class CommonBean {
    @Bean
    public Gson gson() {
        return new Gson();
    }
}
