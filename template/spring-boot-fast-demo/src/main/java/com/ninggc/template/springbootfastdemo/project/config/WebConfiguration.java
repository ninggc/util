package com.ninggc.template.springbootfastdemo.project.config;

import com.ninggc.util.common.aop.interceptor.UrlLogInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new UrlLogInterceptor())
                .addPathPatterns("/**");
    }

    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebConfiguration() {
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                // registry.addInterceptor()
            }

            @Override
            public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
                System.out.println("addAR");
            }
        };
    }
}