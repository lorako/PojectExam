package com.example.ProjectExam.controllers;

import com.example.ProjectExam.models.DTOs.View.ArtistViewDTO;
import com.example.ProjectExam.services.ArtistService;
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

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ArtistsControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Mock
    private HttpSession session;
    @Mock
    private ArtistService mockArtistService;


    @Test
    void testGetAllNoCredential() throws Exception {
        List<ArtistViewDTO>listAll=new ArrayList<>();
        listAll.add(new ArtistViewDTO());
        when(mockArtistService.getAll()).thenReturn(listAll);

        mockMvc.perform(get("/artists"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));

    }
    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN", "USER"})
    void testGetAllWithCredentials() throws Exception {


        List<ArtistViewDTO>listAll=new ArrayList<>();
        listAll.add(new ArtistViewDTO());
        when(mockArtistService.getAll()).thenReturn(listAll);

        if(listAll.size()>0){
            ModelAndView model=new ModelAndView("artists");

            List<ArtistViewDTO> allA = mockArtistService.getAll();

            model.addObject("allA", allA);
            RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/artists").with(csrf());
            MockMvcResultMatchers MockMvcResultMatchers = null;
            mockMvc.perform(requestBuilder)
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.view().name("artists"));

       }

}}