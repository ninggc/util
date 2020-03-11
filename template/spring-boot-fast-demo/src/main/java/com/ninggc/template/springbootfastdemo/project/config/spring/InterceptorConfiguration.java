package com.ninggc.template.springbootfastdemo.project.config.spring;

import com.ninggc.template.springbootfastdemo.common.interceptor.UrlLogInterceptor;
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
