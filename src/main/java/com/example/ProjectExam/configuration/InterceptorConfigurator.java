package com.example.ProjectExam.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfigurator implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
      // registry.addInterceptor(new LoggingInterceptor());
      // registry.addInterceptor( new IndividualInterceptor()).addPathPatterns("/shoppingBag");

    }
}