package com.example.ProjectExam.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.Iterator;

public class LoggingInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("PREHANDLE");
        System.out.println(request.getRequestURL() + " " + request.getMethod());
        Iterator<String> iter = request.getHeaderNames().asIterator();
        while(iter.hasNext()) {
            String headerName = iter.next();
            String headerValue = request.getHeader(headerName);
            System.out.println(headerName + ": " + headerValue);
        }
        return true;

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
       System.out.println("POSTHANDLE");
       if(modelAndView == null) {
           return;
       }
       for(String entry : modelAndView.getModel().keySet()) {
           System.out.println(entry + ": " + modelAndView.getModel().get(entry));
       }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

        System.out.println("AFTERCOMPLETION");
        if(ex != null) {
            ex.printStackTrace();
        }
        response.setStatus(200);
    }
}
