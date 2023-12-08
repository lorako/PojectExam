package com.example.ProjectExam.controllers;

import com.example.ProjectExam.services.HeroService;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.servlet.ModelAndView;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;


@SpringBootTest
@AutoConfigureMockMvc
class HomeControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Mock
    private HttpSession session;
    @Mock
    private HeroService mockHeroService;

    @Test
    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER" })
    void testHome() throws Exception {

       ModelAndView model=new ModelAndView();
       model.addObject("most", mockHeroService.getTheMost());

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/home").with(csrf());
        MockMvcResultMatchers MockMvcResultMatchers = null;
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("home"));

    }

    @Test
    public void testIndexEndpoint() throws Exception {
       
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/index");
        MockMvcResultMatchers MockMvcResultMatchers = null;
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("index"));
    }

}
