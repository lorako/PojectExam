package com.example.ProjectExam.controllers.Rest;

import com.example.ProjectExam.models.DTOs.RestDTO.ArtistRestDTO;
import com.example.ProjectExam.services.ArtistService;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ArtistRestControllerTest {


    @Autowired
    private MockMvc mockMvc;
    @Mock
    private HttpSession session;

    @Mock
    private ArtistService toTest;

    @Test
    public void testGetAllArtistsReturnedBody() throws Exception {


            ArtistRestDTO art1 = new ArtistRestDTO();
            art1.setUsername("Superman");
            ArtistRestDTO art2 = new ArtistRestDTO();
            art1.setUsername("Superman1");

            List<ArtistRestDTO> dtoList=new ArrayList<>();
            dtoList.add(art1);
            dtoList.add(art2);
            when(toTest.getAllArtistsRest()).thenReturn(dtoList);


            RequestBuilder requestBuilder = MockMvcRequestBuilders
                    .get("/api/artist")
                    .contentType(MediaType.APPLICATION_JSON);

            this.mockMvc.perform(get("/api/artists"))
                    .andDo(print()).andExpect(status().isOk());



    }

}