package com.example.ProjectExam.interceptor;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class LoggingInterceptorTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void preHandle() {
    }

    @Test
    void postHandle() {
    }
    @Test
    @WithMockUser(username = "user", authorities = { "ROLE_User"})
    public void testAfterCompletion() throws Exception {
        // Assuming you have a controller endpoint mapped to /example
        mockMvc.perform(MockMvcRequestBuilders.get("/example"))
                .andExpect(status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.content().string("")) // Assuming the response content is empty
                .andExpect(result -> {
                    // Validate the behavior after completion
                    System.out.println("Response status: " + result.getResponse().getStatus());
                    // Add additional assertions based on your specific requirements
                });
    }
}