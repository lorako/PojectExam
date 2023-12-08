package com.example.ProjectExam.controllers;

import com.example.ProjectExam.models.DTOs.View.ContactViewDTO;
import com.example.ProjectExam.models.entities.ContactFormEntity;
import com.example.ProjectExam.services.ContactFormService;
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

@SpringBootTest
@AutoConfigureMockMvc
class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Mock
    private HttpSession session;
    @Mock
    private ContactFormService mockContactService;

    @Test
    @WithMockUser(username = "admin", authorities = { "ROLE_Admin"})
    void testAdminWithAdmin() throws Exception {

            ModelAndView model=new ModelAndView();
            List<ContactViewDTO>all=mockContactService.findAll();
            model.addObject("all", all);


        when(mockContactService.findAll()).thenReturn(all);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/admin").with(csrf());
        MockMvcResultMatchers MockMvcResultMatchers = null;
            mockMvc.perform(requestBuilder)
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.view().name("admin"));

    }
    @Test
    @WithMockUser(username = "user", authorities = { "ROLE_User"})
    void testAdminWithNOAdmin() throws Exception {

        ModelAndView model=new ModelAndView();
        List<ContactViewDTO>all=mockContactService.findAll();
        model.addObject("all", all);


        when(mockContactService.findAll()).thenReturn(all);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/admin").with(csrf());
        MockMvcResultMatchers MockMvcResultMatchers = null;
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isForbidden());



    }



    @Test
    @WithMockUser(username = "admin", authorities = { "ROLE_Admin"})
    void testDeletePostSuccess() throws Exception {
        List<ContactViewDTO>all=listContaltList();
        when(mockContactService.findAll()).thenReturn(all);
        List<ContactViewDTO> all1 = mockContactService.findAll();
        if(all1.size()>1){
            mockContactService.deleteById(1L);
            all1.clear();
            RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/admin").with(csrf());
            MockMvcResultMatchers MockMvcResultMatchers = null;
            mockMvc.perform(requestBuilder)
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.view().name("admin"));

        }else{
            return;
        }

    }
    @Test
    @WithMockUser(username = "admin", authorities = { "ROLE_Admin"})
    void testDeleteWithIDSuccess() throws Exception {
        List<ContactViewDTO>all=listContaltList();
        when(mockContactService.findAll()).thenReturn(all);
        List<ContactViewDTO> all1 = mockContactService.findAll();

        if(all1.size()>1){
            mockContactService.deleteById(1L);
            all1.clear();
            Long id=3L;
            RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/admin/delete/"+id).with(csrf());
            MockMvcResultMatchers MockMvcResultMatchers = null;
            mockMvc.perform(requestBuilder)
                    .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                    .andExpect(MockMvcResultMatchers.view().name("redirect:/admin"));

        }else{
            return;
        }

    }
    private List<ContactViewDTO> listContaltList(){
        ContactViewDTO contact=new ContactViewDTO(new ContactFormEntity());
        contact.setText("llkjhgf");
        contact.setUsername("Boris");
        contact.setEmail("abv@av.bg");
        contact.setId(1L);

        ContactViewDTO contact1=new ContactViewDTO(new ContactFormEntity());
        contact1.setText("llkjhgfsdf");
        contact1.setUsername("Boris1");
        contact1.setEmail("ab1v@av.bg");
        contact1.setId(2L);
        List<ContactViewDTO>list=new ArrayList<>();
        list.add(contact);
        list.add(contact1);
        return list;
    }
}