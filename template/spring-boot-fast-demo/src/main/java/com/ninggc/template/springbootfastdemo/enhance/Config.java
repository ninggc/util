package com.ninggc.template.springbootfastdemo.enhance;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebMvc
@Configuration
public class Config implements WebMvcConfigurer {
    // @Bean
    // public UrlLogInterceptor urlLogInterceptor() {
    //     return new UrlLogInterceptor();
    // }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new UrlLogInterceptor()).addPathPatterns("/**");
    }
}
