package com.solutionwerk.qb;

import com.solutionwerk.qb.web.interceptors.ApiInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {

    public static final String API_CONTROLLER_MAPPING = "/api";
    @Autowired
    private ApiInterceptor apiInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(apiInterceptor).addPathPatterns(API_CONTROLLER_MAPPING + "/**");
//                .excludePathPatterns(API_CONTROLLER_MAPPING + "/**");
    }

}