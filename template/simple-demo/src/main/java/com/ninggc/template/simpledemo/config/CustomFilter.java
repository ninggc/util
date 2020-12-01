package com.ninggc.template.simpledemo.config;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * @author ninggc
 */
@Component
@WebFilter(urlPatterns = "/**", filterName = "CustomFilter")
public class CustomFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("CustomFilter before");
        filterChain.doFilter(servletRequest, servletResponse);
        System.out.println("CustomFilter after");
    }

    @Override
    public void destroy() {

    }
}
