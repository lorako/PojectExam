package com.example.ProjectExam.services.Impl;

import com.example.ProjectExam.Exceptions.ObjectNotFoundException;
import com.example.ProjectExam.Exceptions.UserNotFoundException;
import com.example.ProjectExam.models.DTOs.BindingModel.AddWeaponDTO;
import com.example.ProjectExam.models.DTOs.View.WeaponViewDTO;
import com.example.ProjectExam.models.entities.ArtistEntity;
import com.example.ProjectExam.models.entities.ShopBagEntity;
import com.example.ProjectExam.models.entities.UserEntity;
import com.example.ProjectExam.models.entities.WeaponEntity;
import com.example.ProjectExam.repositories.ArtistRepository;
import com.example.ProjectExam.repositories.ShopBagRepository;
import com.example.ProjectExam.repositories.UserRepository;
import com.example.ProjectExam.repositories.WeaponRepository;
import com.example.ProjectExam.services.ImageCloudService;
import com.example.ProjectExam.services.WeaponService;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
class WeaponServiceImplTest {

    private WeaponService toTest;
    @Mock
    private WeaponRepository mockWeaponRepository;
    @Mock
    private ShopBagRepository mockShopBagRepository;
    @Mock
    private UserRepository mockUserRepository;
    @Mock
    private ArtistRepository mockArtistRepository;
    @Mock
    private ImageCloudService mockImageCloudService;


    @BeforeEach
    void setUp() {
     toTest=new WeaponServiceImpl(mockWeaponRepository, mockShopBagRepository,
             mockUserRepository, mockArtistRepository, mockImageCloudService);
    }

    @AfterEach
    void tearDown() {
        mockWeaponRepository.deleteAll();
        mockUserRepository.deleteAll();
        mockArtistRepository.deleteAll();
    }


    @Test
    void testAddWeapon() {

        AddWeaponDTO addWeaponDTO = new AddWeaponDTO();
        addWeaponDTO.setWeaponName("Sword");
        addWeaponDTO.setPrice(BigDecimal.valueOf(100));

        String username = "testUser";
        MultipartFile imageFile = Mockito.mock(MultipartFile.class);

        WeaponEntity existingWeaponEntity = new WeaponEntity();
        existingWeaponEntity.setWeaponName(addWeaponDTO.getWeaponName());

        ArtistEntity artistEntity = new ArtistEntity();
        artistEntity.setUsername(username);
        artistEntity.setTotalArt(5);

        Mockito.when(mockWeaponRepository.findByWeaponName(addWeaponDTO.getWeaponName()))
                .thenReturn(Optional.empty())
                .thenReturn(Optional.of(existingWeaponEntity));


        Mockito.when(mockArtistRepository.findByUsername(username))
                .thenReturn(Optional.of(artistEntity));

        MultipartFile mockedI=mock(MultipartFile.class);


        toTest.addWeapon(addWeaponDTO, username, mockedI);


        Mockito.verify(mockWeaponRepository, Mockito.times(1)).save(Mockito.any(WeaponEntity.class));
        Mockito.verify(mockArtistRepository, Mockito.times(1)).save(artistEntity);
        Assert.assertEquals(6, artistEntity.getTotalArt());

        toTest.addWeapon(addWeaponDTO, username, imageFile);


        Mockito.verify(mockWeaponRepository, Mockito.times(1)).save(Mockito.any(WeaponEntity.class));
        Mockito.verify(mockArtistRepository, Mockito.times(1)).save(artistEntity);
        Assert.assertEquals(6, artistEntity.getTotalArt());


        String invalidUsername = "invalidUser";
        Mockito.when(mockArtistRepository.findByUsername(invalidUsername))
                .thenReturn(Optional.empty());
        try {
            toTest.addWeapon(addWeaponDTO, invalidUsername, imageFile);
            Assert.fail("UserNotFoundException not thrown");
        } catch (UserNotFoundException e) {
            new UserNotFoundException("This user does not exist!");
        }


        toTest.addWeapon(addWeaponDTO, username, null);

    }



    @Test
    @WithMockUser(username = "artist", authorities = { "ROLE_Artist"})
    void addWeapon() {
        WeaponEntity testWeapon=new WeaponEntity();
        when(mockWeaponRepository.findByWeaponName(testWeapon.getWeaponName())).thenReturn(Optional.of(testWeapon));

        if(mockWeaponRepository.findByWeaponName(testWeapon.getWeaponName()).isEmpty()) {
            testWeapon=testWeaponCreated();
            mockWeaponRepository.save(testWeapon);
            int items=testWeapon.getCreator().getTotalArt();
            items++;
            testWeapon.getCreator().setTotalArt(items);
            mockArtistRepository.save(testWeapon.getCreator());

            assertNotNull(testWeapon);
            assertEquals(testWeapon.getCreator().getUsername(), "Boris");
            assertEquals(testWeapon.getPrice(), BigDecimal.valueOf(12));
            assertEquals(testWeapon.getWeaponName(), "weapon");
            assertEquals(testWeapon.getImgUrl(), "imageLo");
            assertNotSame(testWeapon.getWeaponName(), "polo");



        }else{
            return;
        }

    }
    @Test
    void testAddWeapon1() {
        ArtistEntity mockartist = new ArtistEntity();
        mockartist.setUsername("testuser");
        mockartist.setTotalArt(0);


        AddWeaponDTO mockWeapon = new AddWeaponDTO();
        MultipartFile mockedI=mock(MultipartFile.class);
        mockWeapon.setWeaponName("Sword");
        mockWeapon.setImgUrl(mockedI);
        mockWeapon.setCreator(mockartist);
        mockWeapon.setPrice(BigDecimal.valueOf(100));

        WeaponEntity weapon = new WeaponEntity();
        weapon.setWeaponName(mockWeapon.getWeaponName());
        weapon.setPrice(mockWeapon.getPrice());
        weapon.setCreator(mockWeapon.getCreator());
        weapon.setPrice(mockWeapon.getPrice());
        weapon.setImgUrl(mockWeapon.getImgUrl().getOriginalFilename());


        Optional<WeaponEntity> weaponOptional = Optional.of(weapon);


        Mockito.when(mockWeaponRepository.findByWeaponName("Sword")).thenReturn(weaponOptional);
        Mockito.when(mockArtistRepository.findByUsername("testuser")).thenReturn(Optional.of(mockartist));

        toTest.addWeapon(mockWeapon, "testuser", mockedI);

        mockartist.setTotalArt(1);

        Assertions.assertEquals("Sword", weapon.getWeaponName());
        Assertions.assertEquals(BigDecimal.valueOf(100), weapon.getPrice());
        Assertions.assertEquals("testuser", weapon.getCreator().getUsername());
        Assertions.assertEquals(1, weapon.getCreator().getTotalArt());

    }


    @Test
    void testWeaponNotFound(){
        WeaponEntity weaponEntity=testWeaponCreated();
        Optional<UserEntity> lora = mockUserRepository.findByUsername("Lora");
        assertThrows(UserNotFoundException.class,
                ()-> toTest.buyWeapon(Long.valueOf(23), "Lo"));
    }

    private static WeaponEntity testWeaponCreated(){
        WeaponEntity testWeapon=new WeaponEntity();
        ArtistEntity creator=new ArtistEntity();
        creator.setUsername("Boris");

        UserEntity user=new UserEntity();
        user.setUsername("Lora");

        testWeapon.setCreator(creator);
        testWeapon.setPrice(BigDecimal.valueOf(12));
        testWeapon.setWeaponName("weapon");
        testWeapon.setImgUrl("imageLo");
        testWeapon.setId(1);
        return testWeapon;

    }
    private static List<WeaponViewDTO> testListWeaponCreated(){
        List<WeaponViewDTO> listWeapons= new ArrayList<>();
        WeaponViewDTO testWeapon=new WeaponViewDTO();
        ArtistEntity creator=new ArtistEntity();
        creator.setUsername("Boris");

        testWeapon.setCreator(creator);
        testWeapon.setPrice(BigDecimal.valueOf(12));
        testWeapon.setWeaponName("weapon");
        testWeapon.setImgUrl("imageLo");
        listWeapons.add(testWeapon);

        WeaponViewDTO testWeapon1=new WeaponViewDTO();
        ArtistEntity creator1=new ArtistEntity();
        creator1.setUsername("Angy");

        testWeapon1.setCreator(creator1);
        testWeapon1.setPrice(BigDecimal.valueOf(22));
        testWeapon1.setWeaponName("weapon33");
        testWeapon1.setImgUrl("imageLoolo");
        listWeapons.add(testWeapon1);
        return listWeapons;

    }


    @Test
    void getAll() {

        List<WeaponViewDTO> listWeap  = new ArrayList<>();
        List<WeaponEntity>listEn  = testListWeaponEntity();

        when(mockWeaponRepository.findAll()).thenReturn(listEn);

        for (WeaponEntity we:listEn) {

            WeaponViewDTO weaponViewDTO=new WeaponViewDTO();
            weaponViewDTO.setCreator(we.getCreator());
            weaponViewDTO.setPrice(we.getPrice());
            weaponViewDTO.setImgUrl(we.getImgUrl());
            weaponViewDTO.setWeaponName(we.getWeaponName());
            listWeap.add(weaponViewDTO);

        }
        assertNotNull(listWeap);

        assertEquals(2, listWeap.size());
        assertEquals(listWeap.get(0).getWeaponName(), "weapon");
        assertEquals(listWeap.get(1).getWeaponName(), "weapon33");
        assertEquals(listWeap.get(0).getImgUrl(), "imageLo");
        assertEquals(listWeap.get(1).getImgUrl(), "imageLoolo");
        assertEquals(listWeap.get(0).getCreator().getUsername(), "Boris");
        assertEquals(listWeap.get(1).getCreator().getUsername(), "Angy");
        assertEquals(listWeap.get(1).getPrice(), (BigDecimal.valueOf(22)));
        assertEquals(listWeap.get(0).getPrice(), (BigDecimal.valueOf(12)));


    }

    private List<WeaponEntity> testListWeaponEntity() {

        List<WeaponEntity> listWeapons= new ArrayList<>();
        WeaponEntity testWeapon=new WeaponEntity();
        ArtistEntity creator=new ArtistEntity();
        creator.setUsername("Boris");

        testWeapon.setCreator(creator);
        testWeapon.setPrice(BigDecimal.valueOf(12));
        testWeapon.setWeaponName("weapon");
        testWeapon.setImgUrl("imageLo");
        listWeapons.add(testWeapon);

        WeaponEntity testWeapon1=new WeaponEntity();
        ArtistEntity creator1=new ArtistEntity();
        creator1.setUsername("Angy");

        testWeapon1.setCreator(creator1);
        testWeapon1.setPrice(BigDecimal.valueOf(22));
        testWeapon1.setWeaponName("weapon33");
        testWeapon1.setImgUrl("imageLoolo");
        listWeapons.add(testWeapon1);
        return listWeapons;
    }

    @Test
    @WithMockUser(username = "user", authorities = { "ROLE_User"})
    void buyWeapon() {
        WeaponEntity testWeapon=testWeaponCreated();
        UserEntity mockuser=new UserEntity();

        when(mockWeaponRepository.findById(testWeapon.getId())).thenReturn(Optional.of(testWeapon));


        ShopBagEntity mockshopE=new ShopBagEntity();
        mockshopE.setPrice(testWeapon.getPrice());
        mockshopE.setItemName(testWeapon.getWeaponName());
        mockshopE.setImgUrl(testWeapon.getImgUrl());
        mockshopE.setBuyer(mockuser);

        List<ShopBagEntity> myShopBag = mockuser.getMyShopBag();
        myShopBag.add(mockshopE);
        mockuser.setMyShopBag(myShopBag);
        mockUserRepository.save(mockuser);

        mockShopBagRepository.save(mockshopE);
        mockWeaponRepository.deleteById(testWeapon.getId());
        Optional<WeaponEntity> byWeaponName = mockWeaponRepository.findByWeaponName(testWeapon.getWeaponName());

        verify(mockShopBagRepository).save(mockshopE);

       assertEquals(mockshopE.getPrice(),testWeapon.getPrice());
       assertEquals(mockshopE.getItemName(),testWeapon.getWeaponName());
       assertEquals(mockshopE.getImgUrl(),testWeapon.getImgUrl());
       assertEquals(mockshopE.getPrice(),testWeapon.getPrice());
       assertEquals(1,myShopBag.size());
       assertEquals(Optional.empty(),byWeaponName);


        assertNotEquals(mockWeaponRepository.findByWeaponName(String.valueOf(testWeapon)), false);



    }
    @Test
    public void testBuyWeapon_UserNotFound() {
        Long weaponIdToBuy = 1L;
        String username = "nonexistentuser";

        when(mockUserRepository.findByUsername(username)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> toTest.buyWeapon(weaponIdToBuy, username));
    }

    @Test
    public void testBuyWeapon_WeaponNotFound() {
        Long nonexistentWeaponId = 99L;
        String username = "testuser";

        UserEntity mockUser = new UserEntity();
        when(mockUserRepository.findByUsername(username)).thenReturn(Optional.of(mockUser));

        when(mockWeaponRepository.findById(nonexistentWeaponId)).thenReturn(Optional.empty());

        assertThrows(ObjectNotFoundException.class, () -> toTest.buyWeapon(nonexistentWeaponId, username));
    }


}