package com.example.ProjectExam.controllers.Rest;

import com.example.ProjectExam.Exceptions.HeroNotFoundException;
import com.example.ProjectExam.models.DTOs.RestDTO.AddHeroRestDTO;
import com.example.ProjectExam.models.DTOs.RestDTO.HeroRestDTO;
import com.example.ProjectExam.models.entities.ArtistEntity;
import com.example.ProjectExam.models.entities.HeroEntity;
import com.example.ProjectExam.models.entities.UserEntity;
import com.example.ProjectExam.models.enums.RoleEnum;
import com.example.ProjectExam.repositories.HeroRepository;
import com.example.ProjectExam.services.HeroService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class HeroesRestControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Mock
    private HttpSession session;

    @Mock
    private HeroesRestController mockController;
    @Mock
    private HeroService mockHeroService;
    @Mock
    private HeroRepository mockHeroRepo;


    @Test
    public void entityNotFoundTest() throws Exception {

        Optional<HeroRestDTO> heroRestDTOOptional = Optional.empty();

        ResponseEntity<HeroRestDTO> responseEntity = heroRestDTOOptional
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }
    @Test
    public void entityFoundTest() throws Exception {

        Optional<HeroRestDTO> heroRestDTOOptional = Optional.of(new HeroRestDTO());

        ResponseEntity<HeroRestDTO> responseEntity = heroRestDTOOptional
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());


        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

    }
        @Test
    public void testGetAllHeroesEndpoint() throws Exception {
        // Mock the behavior of the heroService
        HeroRestDTO hero1 = new HeroRestDTO();
        hero1.setHeroName("Superman");

        HeroRestDTO hero2 = new HeroRestDTO();
        hero2.setHeroName("Batman");
        List<HeroRestDTO> dtoList=new ArrayList<>();
        dtoList.add(hero1);
        dtoList.add(hero2);
        when(mockHeroService.findAllHeroesForRest()).
                thenReturn(dtoList);


        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/heroes")
                .contentType(MediaType.APPLICATION_JSON);

        this.mockMvc.perform(get("/api/heroes"))
                .andDo(print()).andExpect(status().isOk());


    }
    @Test
    @WithMockUser(username = "admin", authorities = { "ROLE_Admin"})
    public void findHeroByIdEndpoint() throws Exception {

        HeroRestDTO hero = new HeroRestDTO();
        hero.setHeroName("Superman");

        when(mockHeroService.findHeroById(1L)).thenReturn(Optional.of(hero));

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/heroes/", hero.getId())
                .contentType(MediaType.APPLICATION_JSON);
        if(requestBuilder.equals(0)){
            mockMvc.perform((RequestBuilder) status().isNoContent());

        }else{
        mockMvc.perform(get("/api/heroes/", hero.getId())).andDo(print()).andExpect(status().is4xxClientError());


    }
}

    @Test
    @WithMockUser(username = "admin", authorities = { "ROLE_Admin"})
    public void deleteByIdRest() throws Exception {
    Long heroId = 1L;
        Optional<HeroRestDTO> heroById = mockHeroService.findHeroById(1L);
        if(heroById.isPresent()){
            doNothing().when(mockHeroService).deleteByIdRest(heroId);


            mockMvc.perform(MockMvcRequestBuilders.delete("/api/heroes/{id}", heroId)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isNoContent())
                    .andExpect(MockMvcResultMatchers.handler().handlerType(HeroesRestController.class))
                    .andExpect(MockMvcResultMatchers.handler().methodName("deleteByIdRest"));

            Optional<HeroRestDTO> heroByIdAfter = mockHeroService.findHeroById(1L);
            heroByIdAfter.isEmpty();

        }

}

        @Test
        @WithMockUser(username = "admin", authorities = { "ROLE_Admin"})
        public void testFindById() throws Exception {

         HeroRestDTO mockHeroRestDTO = new HeroRestDTO();
            mockHeroRestDTO.setId(1l);
            Long heroId = mockHeroRestDTO.getId();
            mockHeroRestDTO.setHeroName("Bobo");
            when(mockHeroService.findHeroById(heroId)).thenReturn(Optional.of(mockHeroRestDTO));


            when(mockHeroService.findHeroById(heroId)).thenThrow(new HeroNotFoundException("Hero not found"));

            mockMvc.perform(MockMvcRequestBuilders.get("/api/heroes/{id}", heroId)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isNotFound())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Hero not found"))
                    .andExpect(MockMvcResultMatchers.handler().handlerType(HeroesRestController.class))
                    .andExpect(MockMvcResultMatchers.handler().methodName("findById"));
        }

        @Test
        public void testFindByIdNotFound() throws Exception {

            Long heroId = 2L;
            HeroRestDTO mockHeroRestDTO = new HeroRestDTO();
            mockHeroRestDTO.setId(1l);
            Long heroId1 = mockHeroRestDTO.getId();
            mockHeroRestDTO.setHeroName("Bobo");

            when(mockHeroService.findHeroById(heroId)).thenReturn(Optional.empty());

            mockMvc.perform(MockMvcRequestBuilders.get("/api/heroes/{id}", heroId)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isNotFound())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Hero not found"))
                    .andExpect(MockMvcResultMatchers.handler().handlerType(HeroesRestController.class))
                    .andExpect(MockMvcResultMatchers.handler().methodName("findById"));
        }
    @Test
    @WithMockUser(username = "artist", authorities = { "ROLE_Artist" })
    public void testAddHeroRest() throws Exception {

        ArtistEntity mockedArtist=new ArtistEntity();
        mockedArtist.setUsername("artist");

        UserEntity mockUser=new UserEntity();
        mockUser.setRole(RoleEnum.Artist);
        mockUser.setUsername("artist");

        AddHeroRestDTO mockAddHeroRestDTO = new AddHeroRestDTO();
        mockAddHeroRestDTO.setId(1L);
        mockAddHeroRestDTO.setCreator(mockedArtist);

        UriComponentsBuilder mockUri=UriComponentsBuilder.newInstance();
        ResponseEntity mock=ResponseEntity.ofNullable(mockAddHeroRestDTO);



        long byId =mockHeroService.findById(1L);
        if(byId==0 && mockUser.getRole()==RoleEnum.Artist) {
            HeroEntity mockheroRest=new HeroEntity();
            mockheroRest.setId(mockAddHeroRestDTO.getId());

            mockUri.path("/api/heroes/add/**").build(mockAddHeroRestDTO.getId());

            mockHeroService.addRestHero(mockAddHeroRestDTO);
           mockHeroRepo.save(mockheroRest);
           long byIdAfter =mockHeroService.findById(1L);
           byIdAfter=1l;

        mockMvc.perform(MockMvcRequestBuilders.post("/api/heroes/add/**").with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(asJsonString(mockAddHeroRestDTO)))
            .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.handler().handlerType(HeroesRestController.class))
                .andExpect(MockMvcResultMatchers.handler().methodName("addHeroRest"));

           assertEquals(1, byIdAfter);
     }

    }


    private static String asJsonString(Object obj) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }
    @Test
    public void entityServiceExceptionTest() throws Exception {

        RuntimeException serviceException = new RuntimeException("Service exception");

        when(mockHeroService.findHeroById(anyLong())).thenThrow(serviceException);


    }
}

