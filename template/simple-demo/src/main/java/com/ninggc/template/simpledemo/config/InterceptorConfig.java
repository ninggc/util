package com.ninggc.template.simpledemo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //&emsp;如果有多个拦截器，继续registry.add往下添加就可以啦
        registry.addInterceptor(new CustomInterceptor()).addPathPatterns("/**");
    }

}
