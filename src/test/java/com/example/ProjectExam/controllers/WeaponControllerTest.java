package com.example.ProjectExam.controllers;

import com.example.ProjectExam.models.DTOs.BindingModel.AddWeaponDTO;
import com.example.ProjectExam.models.DTOs.View.WeaponViewDTO;
import com.example.ProjectExam.models.entities.WeaponEntity;
import com.example.ProjectExam.services.WeaponService;
import jakarta.servlet.http.HttpSession;
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
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@SpringBootTest
@AutoConfigureMockMvc
class WeaponControllerTest {
    @Autowired
    private MockMvc mockMvc;


    @Mock
    private HttpSession session;
    @Mock
    public  WeaponService mockWeaponService;

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN", "USER"})
    void weaponHome() throws Exception {
        ModelAndView model = new ModelAndView();

        model.addObject("allW", mockWeaponService.getAll());

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/weapons").with(csrf());
        MockMvcResultMatchers MockMvcResultMatchers = null;
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("weapons"));
    }

    @Test
    @WithMockUser(username = "artist", authorities = {"ROLE_Artist"})
    void testAddWeapon() throws Exception {

        ModelAndView model=new ModelAndView("addWeapon");
        model.addObject("addWeapon");
            mockMvc.perform(MockMvcRequestBuilders.get("/weapons/add").with(csrf()))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.view().name("addWeapon"));
        }




    @Test
    @WithMockUser(username = "artist", authorities = {"ROLE_Artist"})
    void testAddWeaponT() throws Exception {
        ModelAndView model=new ModelAndView();
        model.addObject("addWeapon");

        AddWeaponDTO mockAddWeaponDTO = new AddWeaponDTO();
        mockAddWeaponDTO.setWeaponName("LOLO");
        WeaponEntity newWe=new WeaponEntity();
        newWe.setWeaponName("LOLO");
        WeaponViewDTO wep=new WeaponViewDTO();
        wep.setWeaponName("LOLO");

        List<WeaponViewDTO> all = mockWeaponService.getAll();
        all.isEmpty();
        // Set properties for mockAddWeaponDTO if needed
        mockWeaponService.addWeapon(mockAddWeaponDTO, "artist", mockAddWeaponDTO.getImgUrl());
        List<WeaponViewDTO> all1 = mockWeaponService.getAll();

         if(all1.size() >1 ) {

             mockMvc.perform(MockMvcRequestBuilders.post("/weapons/add").with(csrf())
                             .flashAttr("addWeaponDTO", mockAddWeaponDTO))
                     .andExpect(MockMvcResultMatchers.status().isOk())
                     .andExpect(MockMvcResultMatchers.redirectedUrl("/weapons"));
         }


        Assertions.assertEquals("LOLO", wep.getWeaponName());
    }


    @Test
    @WithMockUser(username = "user", authorities = {"ROLE_User"})
    void buyWeapon() throws Exception {

        Principal mockedUser=new Principal() {
            @Override
            public String getName() {
                return "user";
            }
        };

        WeaponViewDTO wep=new WeaponViewDTO();
        wep.setId(1L);

        List<WeaponViewDTO> allFirst = mockWeaponService.getAll();
        allFirst.add(wep);
        Long idW=wep.getId();
        List<WeaponViewDTO> all = mockWeaponService.getAll();

        if(all.size()>1){
            mockWeaponService.buyWeapon(1L, "user");

            List<WeaponViewDTO> all1 = mockWeaponService.getAll();
            mockWeaponService.buyWeapon(idW,"user");
            all1.isEmpty();

            ModelAndView model=new ModelAndView("shoppingBag");

            RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/weapons/buy/", idW).with(csrf());
            MockMvcResultMatchers MockMvcResultMatchers = null;
            mockMvc.perform(requestBuilder)
                    .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                    .andExpect(MockMvcResultMatchers.view().name("redirect:/shoppingBag"));

        }


        List<WeaponViewDTO> all1 = mockWeaponService.getAll();
        all1.isEmpty();


}
}