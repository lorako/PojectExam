package com.example.ProjectExam.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;


@SpringBootTest
@AutoConfigureMockMvc
class LoginControllerTest{

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testLogin() throws Exception {

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/login").with(csrf());
        MockMvcResultMatchers MockMvcResultMatchers = null;
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk()
                )
                .andExpect(MockMvcResultMatchers.view().name("login"));


    }

    @Test
    public void testLoginError() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/login-error").with(csrf()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("login"))
                .andExpect(MockMvcResultMatchers.model().attribute("hasError", "true"));
    }
}