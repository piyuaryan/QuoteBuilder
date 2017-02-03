package com.solutionwerk.qb.web.interceptors;


import com.solutionwerk.qb.util.RequestContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class ApiInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        RequestContext.setUsername(SecurityContextHolder.getContext().getAuthentication().getName());

        // Proceed normally
        return true;
    }
}
