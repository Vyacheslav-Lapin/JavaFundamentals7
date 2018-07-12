package com.epam.web.controllers;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public interface Filter {
    void destroy();

    void doFilter(ServletRequest request, ServletResponse response,
                  FilterChain chain);

    void init(FilterConfig filterConfig);
}

