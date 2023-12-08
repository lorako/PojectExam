package com.example.ProjectExam.controllers;

import com.example.ProjectExam.models.DTOs.View.ShopBagDTO;
import com.example.ProjectExam.models.entities.UserEntity;
import com.example.ProjectExam.services.Impl.ShopBagServiceImpl;
import com.example.ProjectExam.services.UserService;
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

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@SpringBootTest
@AutoConfigureMockMvc
class ShopBagControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Mock
    private ShopBagController shopBagController;

    @Mock
    private UserService mockUserService;

    @Mock
    private ShopBagServiceImpl mockShopService;



    @Test
    @WithMockUser(username = "testuser", authorities = { "ROLE_Admin"})
    void shop() throws Exception {

        Principal mockPrincipal = new Principal() {
            @Override
            public String getName() {
                return "testuser";
            }
        };

        Optional<UserEntity> testuser = mockUserService.findByUsername("testuser");
        if (testuser.isPresent()) {
            List<ShopBagDTO> all1 = mockShopService.getAll(testuser.get().getUsername());

            ShopBagDTO mockshop = new ShopBagDTO();
            all1.add(mockshop);


            mockShopService.getAll("testuser").set(1, mockshop);


            if (all1.size() >= 3) {
                ModelAndView model = new ModelAndView("shoppingBag");
                BigDecimal totalPrice = new BigDecimal("100.00");

                model.addObject("all1", all1);
                model.addObject("totalPrice", new BigDecimal("100.00"));
                model.addObject("discountPrice", new BigDecimal("90.00"));

                RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/shoppingBag").with(csrf());
                MockMvcResultMatchers MockMvcResultMatchers = null;
                mockMvc.perform(requestBuilder)
                        .andExpect(MockMvcResultMatchers.status().isOk())
                        .andExpect(MockMvcResultMatchers.view().name("shoppingBag"))
                        .andExpect(MockMvcResultMatchers.model().attribute("all1", all1))
                        .andExpect(MockMvcResultMatchers.model().attribute("totalPrice", new BigDecimal("100.00")))
                        .andExpect(MockMvcResultMatchers.model().attribute("discountPrice", new BigDecimal("90.00")));

                Assertions.assertEquals(3, all1.size());
                Assertions.assertEquals("totalPrice", new BigDecimal("100.00"));
                Assertions.assertEquals("discountPrice", new BigDecimal("90.00"));
            } else {
                ModelAndView model = new ModelAndView();

                BigDecimal totalPrice = new BigDecimal("100.00");

                model.addObject("all1", all1);
                model.addObject("totalPrice", new BigDecimal("100.00"));
                model.addObject("discountPrice", null);


                mockMvc.perform(MockMvcRequestBuilders.get("/shoppingBag").principal(mockPrincipal).with(csrf()))
                        .andExpect(MockMvcResultMatchers.status().isOk())
                        .andExpect(MockMvcResultMatchers.view().name("shoppingBag"))
                        .andExpect(MockMvcResultMatchers.model().attribute("all1", all1))
                        .andExpect(MockMvcResultMatchers.model().attribute("totalPrice", new BigDecimal("100.00")))
                        .andExpect(MockMvcResultMatchers.model().attribute("discountPrice", null));

                Assertions.assertEquals(1, all1.size());

            }
        }

    }

    @Test
    @WithMockUser(username = "testuser")
    void pay() throws Exception {


        Principal mockPrincipal = new Principal() {
            @Override
            public String getName() {
                return "testuser";
            }
        };

        List<ShopBagDTO> all = mockShopService.getAll("testuser");
        if(all.size()>0){
            mockShopService.pay("testuser");
            ModelAndView model = new ModelAndView("payPage");
            all.clear();
            List<ShopBagDTO> all1 = mockShopService.getAll("testuser");
            
            mockMvc.perform(MockMvcRequestBuilders.get("/shoppingBag/pay").with(csrf())
                        .principal(mockPrincipal))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("payPage"));

         }
    }



}




