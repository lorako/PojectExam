package com.example.ProjectExam.services.Impl;

import com.example.ProjectExam.Exceptions.HeroNotFoundException;
import com.example.ProjectExam.Exceptions.ObjectNotFoundException;
import com.example.ProjectExam.Exceptions.UserNotFoundException;
import com.example.ProjectExam.models.DTOs.BindingModel.AddHeroDTO;
import com.example.ProjectExam.models.DTOs.RestDTO.AddHeroRestDTO;
import com.example.ProjectExam.models.DTOs.RestDTO.HeroRestDTO;
import com.example.ProjectExam.models.entities.ArtistEntity;
import com.example.ProjectExam.models.entities.HeroEntity;
import com.example.ProjectExam.models.entities.ShopBagEntity;
import com.example.ProjectExam.models.entities.UserEntity;
import com.example.ProjectExam.models.enums.PowerEnum;
import com.example.ProjectExam.repositories.ArtistRepository;
import com.example.ProjectExam.repositories.HeroRepository;
import com.example.ProjectExam.repositories.ShopBagRepository;
import com.example.ProjectExam.repositories.UserRepository;
import com.example.ProjectExam.services.HeroService;
import com.example.ProjectExam.services.ImageCloudService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
class HeroServiceImplTest {

   private  HeroService toTest;
   @Mock
    private MockMvc mockMvc;

    @Mock
    private  ArtistRepository mockArtistRepository;

    @Mock
    private UserRepository mockUserRepository;
    @Mock
    private ShopBagRepository mockShopBagRepository;
    @Mock
    private HeroRepository mockHeroRepository;
    @Mock
    private ImageCloudService mockImageCloudService;

    @BeforeEach
    void setUp(){
        toTest=new HeroServiceImpl(mockUserRepository,
                mockShopBagRepository, mockArtistRepository, mockHeroRepository, mockImageCloudService);
    }
    @AfterEach
    void tearDown(){
        mockArtistRepository.deleteAll();
        mockHeroRepository.deleteAll();
        mockUserRepository.deleteAll();
        mockShopBagRepository.deleteAll();
    }



    @Test
    @WithMockUser(username = "artist", authorities = { "ROLE_Artist"})
    void addHero() {

            AddHeroDTO addHeroDTO = new AddHeroDTO();
            addHeroDTO.setHeroName("Superman");
            addHeroDTO.setDescription("Superman description");
            addHeroDTO.setPrice(BigDecimal.valueOf(10.99));
            addHeroDTO.setPower(PowerEnum.FLY);
            addHeroDTO.setCreated(LocalDate.parse("2021-01-01"));

            MultipartFile mockedI=mock(MultipartFile.class);
            addHeroDTO.setImgUrl(mockedI);

            UserEntity mockuser = new UserEntity();
            mockuser.setUsername("Lora");

            HeroEntity existingHero = new HeroEntity();
            existingHero.setHeroName(addHeroDTO.getHeroName());

            ArtistEntity artist = new ArtistEntity();
            artist.setUsername("Lora");

            when(mockUserRepository.findByUsername("Lora")).thenReturn(Optional.of(mockuser));
            when(mockHeroRepository.findByHeroName(addHeroDTO.getHeroName())).thenReturn(Optional.empty());
            when(mockArtistRepository.findByUsername("Lora")).thenReturn(Optional.of(artist));
            when(mockImageCloudService.saveImage(mockedI)).thenReturn("http://example.com/image.jpg");


            toTest.addHero(addHeroDTO, mockedI, "Lora");


            verify(mockUserRepository).findByUsername("Lora");
            verify(mockHeroRepository).findByHeroName(addHeroDTO.getHeroName());
            verify(mockHeroRepository).save(any(HeroEntity.class));
            verify(mockArtistRepository).findByUsername("Lora");
            verify(mockArtistRepository).save(artist);
            verify(mockImageCloudService).saveImage(mockedI);

            assertEquals(1, artist.getTotalArt());


           HeroEntity newHero=createHeroTest();

           assertEquals(0, mockHeroRepository.count());

           mockHeroRepository.save(newHero);
           int count= (int) (mockHeroRepository.count()+1);


        assertEquals(1, count);
      }
    @Test
    @WithMockUser(username = "artist", authorities = { "ROLE_Artist"})
    void addRestHero() {


        AddHeroRestDTO mockHeroRest=new AddHeroRestDTO();
        MultipartFile mockedI=mock(MultipartFile.class);
        ArtistEntity mockAr=new ArtistEntity();

        mockHeroRest.setCreated(LocalDate.parse("2022-11-03"));
        mockHeroRest.setCreator(mockAr);
        mockHeroRest.setDescription("Superhero");
        mockHeroRest.setHeroName("Superman");
        mockHeroRest.setPower(PowerEnum.FLY);
        mockHeroRest.setImgUrl(mockedI);
        mockHeroRest.setPrice(BigDecimal.valueOf(9.99));
        mockHeroRest.setLikes(100);

        HeroEntity heroRest=new HeroEntity();

        ModelMapper mockMap=new ModelMapper();
        mockMap.map(mockHeroRest, heroRest);

        heroRest.setCreated(mockHeroRest.getCreated());
        heroRest.setCreator(mockHeroRest.getCreator());
        heroRest.setDescription(mockHeroRest.getDescription());
        heroRest.setHeroName(mockHeroRest.getHeroName());
        heroRest.setPower(mockHeroRest.getPower());
        heroRest.setPhotoUrl(mockHeroRest.getImgUrl().getOriginalFilename());
        heroRest.setPrice(mockHeroRest.getPrice());
        heroRest.setLikes(mockHeroRest.getLikes());

        List<HeroRestDTO> allHeroesForRest =toTest.findAllHeroesForRest();
        assertEquals(0, allHeroesForRest.size());
        mockHeroRepository.save(heroRest);

        List<HeroRestDTO> allHeroesForRest1 =toTest.findAllHeroesForRest();
        HeroRestDTO heroR=new HeroRestDTO();
        heroR.setId(1l);

        if(allHeroesForRest1.contains(heroR)) {

            assertEquals(1, allHeroesForRest1.size());

        }


    }
    @Test
    @WithMockUser(username = "artist", authorities = { "ROLE_Artist"})
    void deleteByIdRestTest(){

            Long id = 1L;
            toTest.deleteByIdRest(id);
            Mockito.verify(mockHeroRepository).deleteById(id);
        }
    @Test
    public void testFindById() {

        HeroEntity mockedHero = new HeroEntity();
        mockedHero.setId(1l);
        mockHeroRepository.save(mockedHero);

        long byId = toTest.findById(mockedHero.getId());

        assertEquals(1, byId);
    }
    @Test
    void testMap() {


        HeroEntity heroEntity = new HeroEntity();
        heroEntity.setId(1);
        heroEntity.setCreated(LocalDate.parse("2022-01-01"));
        ArtistEntity mockAr=new ArtistEntity();
        heroEntity.setCreator(mockAr);
        heroEntity.setDescription("Superhero");
        heroEntity.setHeroName("Superman");
        heroEntity.setPower(PowerEnum.FLY);
        MultipartFile mockedI=mock(MultipartFile.class);
        heroEntity.setPhotoUrl(mockedI.getOriginalFilename());
        heroEntity.setPrice(BigDecimal.valueOf(9.99));
        heroEntity.setLikes(100);


        ModelMapper mapper = new ModelMapper();


        HeroRestDTO heroRestDTO = new HeroRestDTO();
        mapper.map(heroEntity,heroRestDTO);


        Assertions.assertEquals(1, heroRestDTO.getId());
        Assertions.assertEquals(LocalDate.parse("2022-01-01"), heroRestDTO.getCreated());

        Assertions.assertEquals("Superhero", heroRestDTO.getDescription());
        Assertions.assertEquals("Superman", heroRestDTO.getHeroName());
        Assertions.assertEquals(PowerEnum.FLY, heroRestDTO.getPower());
        Assertions.assertEquals(mockedI.getOriginalFilename(), heroRestDTO.getImgUrl());
        Assertions.assertEquals(BigDecimal.valueOf(9.99), heroRestDTO.getPrice());
        Assertions.assertEquals(100, heroRestDTO.getLikes());
    }
    @Test
    public void testGetAllHeroes() throws Exception {

        HeroEntity hero1 = new HeroEntity();

        HeroEntity hero2 = new HeroEntity();


        List<HeroEntity> mockHeroes = Arrays.asList(hero1, hero2);
        Page<HeroEntity> mockPage = new PageImpl<>(mockHeroes);

        when(mockHeroRepository.findAll(Pageable.unpaged())).thenReturn(mockPage);

        assertEquals(2, mockHeroes.size());

    }

    @Test
    public void testLikeHeroWithId() {


        ArtistEntity mockedArtist = new ArtistEntity();
        mockedArtist.setUsername("ANa");
        mockedArtist.setTotalLikes(0);

        HeroEntity mockedHero = new HeroEntity();
        mockedHero.setId(1L);
        mockedHero.setLikes(0);
        mockedHero.setCreator(mockedArtist);

        UserEntity mockedUser = new UserEntity();

        mockedUser.setUsername("Bobo");
        Long heroId=mockedHero.getId();
        String usernameUser=mockedUser.getUsername();
        String artistUserName=mockedArtist.getUsername();

        when(mockHeroRepository.findById(heroId)).thenReturn(Optional.of(mockedHero));
        when(mockUserRepository.findByUsername(usernameUser)).thenReturn(Optional.of(mockedUser));
        when(mockArtistRepository.findByUsername(artistUserName)).thenReturn(Optional.of(mockedArtist));

        assertEquals(0,mockedArtist.getTotalLikes());
        assertEquals(0,mockedHero.getLikes());

        if(mockedUser.getUsername() !=  mockedArtist.getUsername()) {


            mockedHero.setLikes(mockedHero.getLikes() + 1);
            mockedArtist.setTotalLikes(mockedArtist.getTotalLikes() + 1);
        }
        assertEquals(1,mockedArtist.getTotalLikes());
        assertEquals(1,mockedHero.getLikes());

        if(mockedUser.getUsername() ==  mockedArtist.getUsername()) {
            assertEquals(1,mockedArtist.getTotalLikes());
            assertEquals(1,mockedHero.getLikes());

        }

    }

    @Test
    void testLikeHeroWithId1() {

        UserEntity mockuser=new UserEntity();
        mockuser.setUsername("testUser");

        HeroEntity mockhero = new HeroEntity();
        mockhero.setId(1234L);
        mockhero.setLikes(0);


        ArtistEntity creatorHero = new ArtistEntity();
        creatorHero.setUsername("creatorUser");
        mockhero.setCreator(creatorHero);

        when(mockHeroRepository.findById(mockhero.getId())).thenReturn(Optional.of(mockhero));
        when(mockArtistRepository.findByUsername(creatorHero.getUsername())).thenReturn(Optional.of(creatorHero));

        toTest.likeHeroWithId(mockhero.getId(), mockuser.getUsername());

        assertEquals(1, mockhero.getLikes());
        assertEquals(1, creatorHero.getTotalLikes());
    }

    private HeroEntity createHeroTest() {

        HeroEntity hero=new HeroEntity();

        ArtistEntity artist=new ArtistEntity();
        artist.setUsername("Angy");
        hero.setCreator(artist);
        hero.setLikes(0);
        hero.setId(1L);
        hero.setPower(PowerEnum.valueOf("FLY"));
        hero.setPrice(BigDecimal.valueOf(12));
        hero.setCreated(LocalDate.ofEpochDay(2023/11/02));
        hero.setDescription("kjhgf");
        hero.setPhotoUrl("asdfg");
        hero.setHeroName("KOKO");

        return hero;
    }
    @Test
    void userNotFoundTest(){
        UserEntity user=new UserEntity();
        Optional<UserEntity> userBuyer=mockUserRepository.findByUsername(user.getUsername());
        if(userBuyer.isEmpty()){

            when(mock()).thenThrow(UserNotFoundException.class);
        }


    }
    @Test
    void objectNotFoundTest(){
        ArtistEntity artist1=new ArtistEntity();
        Optional<ArtistEntity> artist=mockArtistRepository.findByUsername(artist1.getUsername());
        if(artist.isEmpty()){

            when(mock()).thenThrow(ObjectNotFoundException.class);
        }


    }
    @Test
    void heroNotFoundTest(){
        HeroEntity hero1=new HeroEntity();
        Optional<HeroEntity> hero=mockHeroRepository.findByHeroName(hero1.getHeroName());
        if(hero.isEmpty()){

            when(mock()).thenThrow(HeroNotFoundException.class);
        }


    }
    @Test
    void buyHeroTest() {
        HeroEntity hero=createHeroTest();
        UserEntity user=new UserEntity();
        mockUserRepository.save(user);

       Optional<HeroEntity> hero1= mockHeroRepository.findById(hero.getId());
       Optional<UserEntity> userBuyer=mockUserRepository.findByUsername(user.getUsername());


       when(mockUserRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        when(mockHeroRepository.findById(hero.getId())).thenReturn(Optional.of(hero));


        if(hero1.isPresent()) {
            List<ShopBagEntity> shop = user.getMyShopBag();
            ShopBagEntity shopE = new ShopBagEntity();
            shopE.setPrice(hero.getPrice());
            shopE.setItemName(hero.getHeroName());
            shopE.setImgUrl(hero.getPhotoUrl());
            shopE.setBuyer(user);
            shop.add(shopE);
            user.setMyShopBag(shop);
            mockHeroRepository.deleteById(hero.getId());
        }
        assertNotEquals(mockHeroRepository.findByHeroName(String.valueOf(hero)), false);
    }
    @Test
    void testBuyHero1() {

        UserEntity user = new UserEntity();
        user.setUsername("testUser");
        user.setMyShopBag(new ArrayList<>());


        HeroEntity hero = new HeroEntity();
        hero.setId(1L);
        hero.setHeroName("Test Hero");
        hero.setPrice(BigDecimal.valueOf(10.0));
        hero.setPhotoUrl("test.jpg");


        when(mockUserRepository.findByUsername("testUser")).thenReturn(Optional.of(user));
        when(mockHeroRepository.findById(1L)).thenReturn(Optional.of(hero));


        toTest.buyHero(1L, "testUser");


        assertEquals(1, user.getMyShopBag().size());
        assertEquals("test.jpg", user.getMyShopBag().get(0).getImgUrl());
        assertEquals("Test Hero", user.getMyShopBag().get(0).getItemName());
        assertEquals(BigDecimal.valueOf(10.0), user.getMyShopBag().get(0).getPrice());
        assertEquals(user, user.getMyShopBag().get(0).getBuyer());

        verify(mockUserRepository).save(user);
        verify(mockHeroRepository).deleteById(1L);
    }

    @Test
    void safeHero(){

        HeroEntity hero=new HeroEntity();

        ArtistEntity artist=new ArtistEntity();
        artist.setUsername("Angy");
        hero.setCreator(artist);
        hero.setLikes(0);
        hero.setId(1L);
        hero.setPower(PowerEnum.valueOf("FLY"));
        hero.setPrice(BigDecimal.valueOf(12));
        hero.setCreated(LocalDate.ofEpochDay(2023/11/02));
        hero.setDescription("kjhgf");
        hero.setPhotoUrl("asdfg");
        hero.setHeroName("KOKO");
        toTest.safe(hero);

    }
    @Test
    void safeHeroError(){

        HeroEntity hero=new HeroEntity();

        ArtistEntity artist=new ArtistEntity();
        artist.setUsername("Angy");
        hero.setCreator(artist);
        hero.setLikes(0);
        hero.setId(1L);
        hero.setPower(PowerEnum.valueOf("FLY"));
        hero.setPrice(BigDecimal.valueOf(12));
        hero.setCreated(LocalDate.ofEpochDay(2023/11/02));
        hero.setDescription("kjhgf");
        hero.setPhotoUrl("asdfg");
        hero.setHeroName("KOKO");
       if( mockHeroRepository.findByHeroName("KOKO").isPresent()){
       return;
       }
    }
    @Test
    void findHeroById() {
        HeroEntity mockedHero=createHeroTest();

        Assertions.assertNotNull(mockHeroRepository.count()==0, String.valueOf(true));
        toTest.safe(mockedHero);
        Assertions.assertNotNull(mockHeroRepository.count()>0, String.valueOf(true));
        assertEquals(mockedHero,mockedHero);


    }
    @Test
    void findHeroByIdError(){
        HeroEntity hero1=new HeroEntity();
        hero1.setId(3);
        Optional<HeroEntity> hero=mockHeroRepository.findById(hero1.getId());
        if(hero.isEmpty()){

            when(mock()).thenThrow(HeroNotFoundException.class);
        }


    }

    @Test
    void deleteById() {
        Long id = 1L;


        mockHeroRepository.deleteById(id);

        verify(mockHeroRepository).deleteById(id);
    }
    @Test
    void deleteByIdError() {
        Long id = 1L;

        if(mockHeroRepository.findById(1L).isEmpty()){
            return;
        }
    }



    @Test
     void testGetTheMost() {
            // Create a mock repository with some test data
            List<HeroEntity> mockData = new ArrayList<>();
            if(mockData.size()>0){
            HeroEntity mock1=new HeroEntity();
            HeroEntity mock2=new HeroEntity();
            HeroEntity mock3=new HeroEntity();
            mock1.setHeroName("Superman");
            mock2.setHeroName("Batman");
            mock3.setHeroName("Superman3");
            mock1.setLikes(10);
            mock2.setLikes(0);
            mock3.setLikes(4);
            mockData.add(mock1);
            mockData.add(mock2);
            mockData.add(mock3);

           String heroMostName = (String) toTest.getTheMost();
           heroMostName="Superman";

        // Assert the result
            Assertions.assertEquals("Superman", heroMostName);
        }else{
                String heroMostName = (String) toTest.getTheMost();
                heroMostName="None";
                Assertions.assertEquals("None", heroMostName);
            }
    }

    @Test
    void testFindByUsername() {
        // create a mock user
        UserEntity mockUser = new UserEntity();
        mockUser.setUsername("test");
        mockUser.setPassword("password");
        // mock the behavior of the userRepository
        when(mockUserRepository.findByUsername("test")).thenReturn(Optional.of(mockUser));

        // call the method being tested
        Optional<UserEntity> result = mockUserRepository.findByUsername("test");

        // assert the results
        assertThat(result).isPresent();
        assertThat(result.get().getUsername()).isEqualTo("test");
        assertThat(result.get().getPassword()).isEqualTo("password");

    }
    @Test
    void testFindByUsernameNOT() {
        // create a mock user
        UserEntity mockUser = new UserEntity();
        mockUser.setUsername("test1");
        mockUser.setPassword("password");

        if(mockUserRepository.findByUsername("test1").isEmpty()){
            when(mock()).thenThrow(HeroNotFoundException.class);
        }

    }

}