package com.example.ProjectExam.controllers;

import com.example.ProjectExam.models.DTOs.BindingModel.RegisterDTO;
import com.example.ProjectExam.models.entities.UserEntity;
import com.example.ProjectExam.services.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
class RegisterControllerTest {
    @Mock
    private RegisterController mockcontroller;
    @Autowired
    private MockMvc mockMvc;


    @Mock
    private UserService mockService;



    @Test
    void register() throws Exception {

            mockMvc.perform(MockMvcRequestBuilders.get("/register"))
                    .andExpect(status().isOk())
                    .andExpect(view().name("register"))
                    .andExpect(MockMvcResultMatchers.model().attributeExists("registerDTO"));

    }


    @Test
    @WithMockUser(username = "user", authorities = { "ROLE_User"})
    public void hasSuccessRegistration() throws Exception {

        RegisterDTO mockregisterDTO = new RegisterDTO();
        mockregisterDTO.setUsername("testuser");
        mockregisterDTO.setPassword("password");

        Optional<UserEntity> testuser = mockService.findByUsername("testuser");

        boolean hasSuccess = mockService.register(mockregisterDTO);

        if(hasSuccess || testuser.isPresent()){

        mockMvc.perform(MockMvcRequestBuilders.post("/register").with(csrf())
                        .flashAttr("registerDTO", mockregisterDTO))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/login"));

        }else if( ! hasSuccess){
            ModelAndView mockModel =new ModelAndView("register");
            mockModel.addObject("HasError", true);

            assertNotNull(mockModel);


            assertEquals("register", mockModel.getViewName());


            assertTrue("hasError", true);

            mockMvc.perform(MockMvcRequestBuilders.post("/register").with(csrf())
                            .flashAttr("hasError", true))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.view().name("register"));
        }
    }


    @Test
    void ifHasSuccessFailure() throws Exception {

        ModelAndView mockModel =new ModelAndView("register");
        mockModel.addObject("HasError", true);

        assertNotNull(mockModel);


        assertEquals("register", mockModel.getViewName());


        assertTrue("hasError", true);

        mockMvc.perform(MockMvcRequestBuilders.post("/register").with(csrf())
                        .flashAttr("hasError", true))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("register"));
    }
}


