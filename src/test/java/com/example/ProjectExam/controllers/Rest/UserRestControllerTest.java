package com.example.ProjectExam.controllers.Rest;

import com.example.ProjectExam.models.DTOs.RestDTO.UserRestDTO;
import com.example.ProjectExam.services.UserService;
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
class UserRestControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Mock
    private HttpSession session;

    @Mock
    private UserService toTest;

    @Test
    void getAllUsers() throws Exception {

        UserRestDTO mockuser1 = new UserRestDTO();
        mockuser1.setUsername("BOBO");
        UserRestDTO mockuser2 = new UserRestDTO();
        mockuser1.setUsername("BOBO1");

        List<UserRestDTO> dtoList=new ArrayList<>();
        dtoList.add(mockuser1);
        dtoList.add(mockuser2);
        when(toTest.getAllUsersRest()).thenReturn(dtoList);


        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/users")
                .contentType(MediaType.APPLICATION_JSON);

        this.mockMvc.perform(get("/api/users"))
                .andDo(print()).andExpect(status().isOk());

    }
}