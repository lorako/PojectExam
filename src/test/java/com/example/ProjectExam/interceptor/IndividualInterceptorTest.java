package com.example.ProjectExam.interceptor;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@AutoConfigureMockMvc
class IndividualInterceptorTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void preHandle() {
    }

    @Test
    void postHandle() {
    }

    @Test
    void afterCompletion() {
    }
}