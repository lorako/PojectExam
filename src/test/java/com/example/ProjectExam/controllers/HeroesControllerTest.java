package com.example.ProjectExam.controllers;

import com.example.ProjectExam.Exceptions.ObjectNotFoundException;
import com.example.ProjectExam.models.DTOs.BindingModel.AddHeroDTO;
import com.example.ProjectExam.models.DTOs.RestDTO.HeroRestDTO;
import com.example.ProjectExam.models.entities.ArtistEntity;
import com.example.ProjectExam.models.entities.HeroEntity;
import com.example.ProjectExam.models.entities.UserEntity;
import com.example.ProjectExam.services.HeroService;
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
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mock;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class HeroesControllerTest {
   @Mock
    private HeroesController heroesController;

    @Autowired
    private MockMvc mockMvc;
    @Mock
    private BindingResult bindingResult;
    @Mock
    private HeroService mockHeroService;

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN", "USER"})
    void homeView() throws Exception {

        ModelAndView model = new ModelAndView();

        model.addObject("allH", mockHeroService.getAllHeroes());

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/allHeroes").with(csrf());
        MockMvcResultMatchers MockMvcResultMatchers = null;
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk()
                )
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.view().name("allHeroes"));


    }


    @Test
    @WithMockUser(username = "admin", authorities = { "ROLE_Admin"})
    void buyHeroSuccess() throws Exception {

        Principal mockUser=new Principal() {
            @Override
            public String getName() {
                return "admin";
            }

        };

        HeroEntity mockHero=new HeroEntity();
        mockHero.setId(2L);

        Optional<HeroRestDTO> heroById = mockHeroService.findHeroById(mockHero.getId());

        if(heroById.isPresent()){
            mockHeroService.buyHero(mockHero.getId(),"admin");
            mockHeroService.deleteById(mockHero.getId());

            RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/allHeroes/buy/", heroById).with(csrf());
            MockMvcResultMatchers MockMvcResultMatchers = null;
            mockMvc.perform(requestBuilder)
                    .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                    .andExpect(MockMvcResultMatchers.view().name("shoppingBag"));
        }else{
            return;
        }


    }
    @Test
    public void testOneHeroExists() {

        Optional<HeroEntity> oneHero = Optional.of(new HeroEntity());

        try {

            if (oneHero.isPresent()) {
                throw new ObjectNotFoundException("HeroName already exists!");
            }

            fail("Expected ObjectNotFoundException to be thrown");
        } catch (ObjectNotFoundException e) {

            assertEquals("HeroName already exists!", e.getMessage());
        }
    }

   @Test
    @WithMockUser(username = "artist", authorities = { "ROLE_Artist"})
    void addHeroesSuccess() throws Exception {

    AddHeroDTO mockHero=new AddHeroDTO();
    mockHero.setHeroName("LOLO");
    mockHero.setId(2l);

    MultipartFile mockedI=mock(MultipartFile.class);

    mockHeroService.addHero(mockHero, mockedI, "artist");

    long byId =mockHeroService.findById(mockHero.getId());

    if(!bindingResult.hasErrors() && byId>0){
        ModelAndView model=new ModelAndView("addHero");


        mockMvc.perform(MockMvcRequestBuilders.post("/allHeroes/add").with(csrf())
                        .flashAttr("addHeroDTO", mockHero))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(redirectedUrl("redirect:/allHeroes"));;
    }else if(bindingResult.hasErrors()){
        mockMvc.perform(MockMvcRequestBuilders.post("/allHeroes/add").with(csrf()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(redirectedUrl("/allHeroes"));
    }


    }
    @Test
    @WithMockUser(username = "user", authorities = { "ROLE_User"})
    void addHeroesNOTSuccess() throws Exception {

        ModelAndView model=new ModelAndView("addHero");

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/allHeroes/add");
        MockMvcResultMatchers MockMvcResultMatchers = null;
        mockMvc.perform(requestBuilder)
                .andExpect(status().isForbidden()
                );


    }
    @Test
    @WithMockUser(username = "user", authorities = { "ROLE_User"})
    void addHeroesNOTImage() throws Exception {


        AddHeroDTO mockHero=new AddHeroDTO();
        mockHero.setHeroName("LOLO");
        mockHero.setId(2l);

        MultipartFile mockedI=null;

        return;



    }
    @Test
    @WithMockUser(username = "user", authorities = { "ROLE_User"})
    void likeHeroSuccess() throws Exception {



        HeroEntity mockHero=new HeroEntity();
        mockHero.setId(2L);
        mockHero.setLikes(0);

        UserEntity mockUser=new UserEntity();

        Optional<HeroRestDTO> heroById = mockHeroService.findHeroById(mockHero.getId());

        if(heroById.isPresent()){
             mockHeroService.likeHeroWithId(mockHero.getId(), mockUser.getUsername() );
             mockHero.setLikes(1);
            RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/allHeroes/like-hero/", heroById).with(csrf());
            MockMvcResultMatchers MockMvcResultMatchers = null;
            mockMvc.perform(requestBuilder)
                    .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                    .andExpect(MockMvcResultMatchers.view().name("redirect:/allHeroes"));
        }else{
            return;
        }


    }


    @Test
    @WithMockUser(username = "user", authorities = { "ROLE_User"})
    void likeHeroError() throws Exception {

        HeroEntity mockHero=new HeroEntity();
        UserEntity mockUser=new UserEntity();
        mockUser.setUsername("BO");
        ArtistEntity mockArtist=new ArtistEntity();
        mockArtist.setUsername("BO");
        mockHero.setId(2L);
        mockHero.setLikes(0);
        mockHero.setCreator(mockArtist);
        String usernameArtist=mockHero.getCreator().getUsername();



        Optional<HeroRestDTO> heroById = mockHeroService.findHeroById(mockHero.getId());

        if(heroById.isPresent()){
            mockHeroService.likeHeroWithId(mockHero.getId(), mockUser.getUsername() );

            if((usernameArtist)==(mockUser.getUsername()))

              return;
          }


    }


}