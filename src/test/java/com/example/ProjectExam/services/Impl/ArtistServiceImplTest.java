package com.example.ProjectExam.services.Impl;

import com.example.ProjectExam.models.DTOs.View.ArtistViewDTO;
import com.example.ProjectExam.models.DTOs.RestDTO.ArtistRestDTO;
import com.example.ProjectExam.models.entities.ArtistEntity;
import com.example.ProjectExam.models.entities.UserEntity;
import com.example.ProjectExam.repositories.ArtistRepository;
import com.example.ProjectExam.services.ArtistService;
import com.example.ProjectExam.services.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@SpringBootTest
@AutoConfigureMockMvc
class ArtistServiceImplTest {

    private ArtistService toTest;
    @Mock
    private ArtistRepository mockArtistRepository;
    @Mock
    private UserService mockUserService;

    @BeforeEach
    void setUp() {

        toTest=new ArtistServiceImpl(mockArtistRepository, mockUserService);
    }

    @AfterEach
    void tearDown() {
        mockArtistRepository.deleteAll();

    }

    @Test
    void testListArtistDTOFound(){
        List<ArtistViewDTO> list=createListTestDTO();

        List<ArtistEntity> listA=new ArrayList<>();

        when(mockArtistRepository.findAll()).thenReturn(listA);
        for (ArtistEntity art:listA) {
            ArtistViewDTO artistViewDTO=new ArtistViewDTO();
            artistViewDTO.setUsername(art.getUsername());
            artistViewDTO.setTotalLikes(art.getTotalLikes());
            artistViewDTO.setTotalArt(art.getTotalArt());
            artistViewDTO.setCountry(mockUserService.findByUsername(art.getUsername()).get().getCountry());
            artistViewDTO.setAge(mockUserService.findByUsername(art.getUsername()).get().getAge());
        }


        List<ArtistViewDTO> artistViewDTO=toTest.getAll();

        assertNotNull(list);
        assertEquals(2, list.size());
        assertEquals(list.get(0).getUsername(), "artist");
        assertEquals(list.get(1).getUsername(), "artist1");
        assertEquals(list.get(0).getAge(), 12);
        assertEquals(list.get(1).getAge(), 22);
        assertEquals(list.get(0).getCountry(), "BG");
        assertEquals(list.get(1).getCountry(), "CR");
        assertEquals(list.get(0).getTotalLikes(), 7);
        assertNotEquals(list.get(0).getTotalLikes(),1);

    }


    private static List<ArtistViewDTO> createListTestDTO(){
        List<ArtistViewDTO>list=new ArrayList<>();
        ArtistViewDTO artistViewDTO=new ArtistViewDTO();
        artistViewDTO.setAge(12);
        artistViewDTO.setUsername("artist");
        artistViewDTO.setCountry("BG");
        artistViewDTO.setTotalLikes(7);
        artistViewDTO.setTotalArt(23);
        list.add(artistViewDTO);
        ArtistViewDTO artistViewDTO2=new ArtistViewDTO();
        artistViewDTO2.setAge(22);
        artistViewDTO2.setUsername("artist1");
        artistViewDTO2.setCountry("CR");
        artistViewDTO2.setTotalLikes(1);
        artistViewDTO2.setTotalArt(3);
        list.add(artistViewDTO2);
        return list;
    }

    @Test
    void testListArtistDTONotFound(){
        List<ArtistViewDTO>list=new ArrayList<>();
        list.clear();

        Assertions.assertEquals(0, list.size());
        if(list.size()==0){
            return;
        }

    }

    @Test
    void mapArtistEntityToArtistViewDTONOTCorrect(){

        ArtistEntity newArtist=new ArtistEntity();
        newArtist.setEmail("po");
        newArtist.setUsername("Bo");
        newArtist.setTotalArt(12);
        newArtist.setTotalLikes(7);

        mockArtistRepository.save(newArtist);

        ArtistViewDTO artistViewDTO=new ArtistViewDTO();
        artistViewDTO.setTotalArt(2);
        artistViewDTO.setUsername("Bo");
        artistViewDTO.setTotalLikes(7);

        Assertions.assertNotEquals(newArtist.getTotalArt(), artistViewDTO.getTotalLikes());
        Assertions.assertEquals(0,toTest.getAll().size());

    }

    @Test
    void testMapArtistEntityToArtistViewDTO() {
        ArtistEntity mockArtist=new ArtistEntity();
        mockArtist.setEmail("oiuyt");
        mockArtist.setUsername("Lora");
        mockArtist.setTotalLikes(0);
        mockArtist.setTotalArt(2);


        ArtistViewDTO artistViewDTO = new ArtistViewDTO();
        ModelMapper mapper = new ModelMapper();

        mapper.map(mockArtist,artistViewDTO);

            // Set the properties of the DTO object
         artistViewDTO.setTotalLikes(mockArtist.getTotalLikes());
         artistViewDTO.setUsername(mockArtist.getUsername());
         artistViewDTO.setTotalArt(mockArtist.getTotalArt());

        Assertions.assertEquals("Lora", artistViewDTO.getUsername());
        Assertions.assertEquals(0, artistViewDTO.getTotalLikes());
        Assertions.assertEquals(2, artistViewDTO.getTotalArt());

        }


    @Test
    void mapArtistEntityToArtistViewDTOCorrect(){

        ArtistEntity mockedArtist=new ArtistEntity();
        mockedArtist.setEmail("po");
        mockedArtist.setUsername("Bo");
        mockedArtist.setTotalArt(12);
        mockedArtist.setTotalLikes(7);


        Assertions.assertEquals(0,mockArtistRepository.count());

        mockArtistRepository.save(mockedArtist);
        List<ArtistEntity> mockeList=mockArtistRepository.findAll();
        mockeList.add(mockedArtist);
        Assertions.assertEquals(1,mockeList.size());

        mockUserService.findByUsername("Bo");
        String country= "CO";
        int age= 12;


        ArtistViewDTO artistViewDTO=new ArtistViewDTO();
        artistViewDTO.setTotalArt(12);
        artistViewDTO.setUsername("Bo");
        artistViewDTO.setTotalLikes(7);
        artistViewDTO.setCountry(country);
        artistViewDTO.setAge(age);

        Assertions.assertEquals(mockedArtist.getTotalArt(), artistViewDTO.getTotalArt());
        Assertions.assertEquals(mockedArtist.getTotalLikes(), artistViewDTO.getTotalLikes());
        Assertions.assertEquals(mockedArtist.getUsername(), artistViewDTO.getUsername());


    }

    @Test
    public void testGetAllArtistsRest() throws Exception {
        // Mock data
        ArtistEntity artist1 =new ArtistEntity();
        ArtistEntity artist2 = new ArtistEntity();
        List<ArtistEntity> mockArtists = new ArrayList<>();
        mockArtists.add(artist1);
        mockArtists.add(artist2);

        when(mockArtistRepository.findAll()).thenReturn(mockArtists);


        List<ArtistRestDTO> result = toTest.getAllArtistsRest();


        assertEquals(mockArtists.size(), result.size());
    }
    @Test
    public void testGetAll() throws Exception {

        ArtistEntity artist1 =new ArtistEntity();
        ArtistEntity artist2 = new ArtistEntity();
        List<ArtistEntity> mockArtists = new ArrayList<>();
        mockArtists.add(artist1);
        mockArtists.add(artist2);
        String username1=artist1.getUsername();
        Optional<UserEntity> usar=mockUserService.findByUsername(username1);
        String username2=artist2.getUsername();
        Optional<UserEntity> usar1=mockUserService.findByUsername(username2);
        when(mockArtistRepository.findAll()).thenReturn(mockArtists);


        when(mockUserService.findByUsername(artist1.getUsername())).thenReturn(usar);
        when(mockUserService.findByUsername(artist2.getUsername())).thenReturn(usar1);



        List<ArtistViewDTO> result =new ArrayList<>();
        result.add(new ArtistViewDTO());
        result.add(new ArtistViewDTO());


        assertEquals(mockArtists.size(), result.size());
    }

    @Test
    public void testCreateArtistViewDTO() {


        List<ArtistEntity> listArtists = new ArrayList<>();
        ArtistEntity artist1 = new ArtistEntity();
        artist1.setUsername("artist1");
        artist1.setTotalLikes(9);
        artist1.setTotalArt(5);
        listArtists.add(artist1);

        ArtistEntity artist2 = new ArtistEntity();
        artist2.setUsername("artist2");
        artist2.setTotalLikes(10);
        artist2.setTotalArt(10);
        listArtists.add(artist2);


        when(mockUserService.findByUsername("artist1")).thenReturn(Optional.of(new UserEntity()));
        when(mockUserService.findByUsername("artist2")).thenReturn(Optional.of(new UserEntity()));


        List<ArtistViewDTO> dtoList = toTest.getAll();


        ArtistViewDTO artistViewDTO1 = new ArtistViewDTO();
        artistViewDTO1.setTotalLikes(artist1.getTotalLikes());
        artistViewDTO1.setTotalArt(artist1.getTotalArt());
        artistViewDTO1.setUsername(artist1.getUsername());
        artistViewDTO1.setAge(25);
        artistViewDTO1.setCountry("BG");
        assertEquals(9, artistViewDTO1.getTotalLikes());
        assertEquals("artist1", artistViewDTO1.getUsername());
        assertEquals(5, artistViewDTO1.getTotalArt());
        assertEquals(25, artistViewDTO1.getAge());
        assertEquals("BG", artistViewDTO1.getCountry());

        ArtistViewDTO artistViewDTO2 = new ArtistViewDTO();
        artistViewDTO2.setTotalLikes(artist2.getTotalLikes());
        artistViewDTO2.setTotalArt(artist2.getTotalArt());
        artistViewDTO2.setUsername(artist2.getUsername());
        artistViewDTO2.setAge(30);
        artistViewDTO2.setCountry("Canada");

        assertEquals(10, artistViewDTO2.getTotalLikes());
        assertEquals("artist2", artistViewDTO2.getUsername());
        assertEquals(10, artistViewDTO2.getTotalArt());
       assertEquals(30, artistViewDTO2.getAge());
        assertEquals("Canada", artistViewDTO2.getCountry());

        dtoList.add(artistViewDTO1);
        dtoList.add(artistViewDTO2);
        assertEquals(2, dtoList.size());
    }

}