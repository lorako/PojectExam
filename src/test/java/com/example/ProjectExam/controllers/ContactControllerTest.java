package com.example.ProjectExam.controllers;

import com.example.ProjectExam.models.DTOs.BindingModel.ContactFormAddDTO;
import com.example.ProjectExam.models.DTOs.View.ContactViewDTO;
import com.example.ProjectExam.models.entities.ContactFormEntity;
import com.example.ProjectExam.services.ContactFormService;
import org.junit.jupiter.api.Assertions;
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

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@SpringBootTest
@AutoConfigureMockMvc
class ContactControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private ContactFormService mockContactService;

    @Test
    @WithMockUser(username = "admin", authorities = { "ROLE_Admin"})
    void testContactForm() throws Exception {

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/contacts").with(csrf());
        MockMvcResultMatchers MockMvcResultMatchers = null;
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk()
                )
                .andExpect(MockMvcResultMatchers.view().name("contacts"));



    }

    @Test
    @WithMockUser(username = "user", authorities = { "ROLE_User"})
    void submitTestForm() throws Exception {
        ContactFormAddDTO mockYourFormDTO = new ContactFormAddDTO();
        mockYourFormDTO.setUsername("Boris");
        mockYourFormDTO.setText("lkjhg");
        ContactFormEntity contact=new ContactFormEntity();
        contact.setEmail(mockYourFormDTO.getEmail());
        contact.setText(mockYourFormDTO.getText());
        contact.setUsername(mockYourFormDTO.getUsername());

         mockContactService.safeForm(mockYourFormDTO);
        ContactViewDTO contactViewDTO=new ContactViewDTO(contact);
        contactViewDTO.setUsername("Boris");
        List<ContactViewDTO> all = mockContactService.findAll();
        if(all.contains(contactViewDTO)) {

            mockMvc.perform(MockMvcRequestBuilders.post("/contacts").with(csrf())
                            .flashAttr("contactFormAddDTO", mockYourFormDTO))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.view().name("redirect:/thanks"));

        }
        Assertions.assertEquals("Boris",contactViewDTO.getUsername());
        verify(mockContactService).safeForm(mockYourFormDTO);
    }


    @Test
    @WithMockUser(username = "admin", authorities = { "ROLE_Admin"})
    void thanks() throws Exception {


        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/thanks").with(csrf());
        MockMvcResultMatchers MockMvcResultMatchers = null;
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk()
                )
                .andExpect(MockMvcResultMatchers.view().name("thanks"));

    }

}