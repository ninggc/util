package com.ninggc.util.common.config;

import com.ninggc.util.common.aop.interceptor.UrlLogInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author ninggc
 */
@ConditionalOnBean(UrlLogInterceptor.class)
@Configuration
public class InterceptorConfiguration implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new UrlLogInterceptor())
                .addPathPatterns("/**");
    }
}
