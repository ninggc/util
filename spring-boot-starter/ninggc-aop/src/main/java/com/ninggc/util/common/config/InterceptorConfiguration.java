package com.ninggc.util.common.config;

import com.ninggc.util.common.aop.interceptor.UrlLogInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author ninggc
 */
@Configuration
public class InterceptorConfiguration implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new UrlLogInterceptor())
                .addPathPatterns("/**");
    }
}
