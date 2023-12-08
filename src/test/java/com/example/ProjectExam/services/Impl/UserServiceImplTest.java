package com.example.ProjectExam.services.Impl;

import com.example.ProjectExam.models.DTOs.BindingModel.RegisterDTO;
import com.example.ProjectExam.models.entities.ArtistEntity;
import com.example.ProjectExam.models.entities.UserEntity;
import com.example.ProjectExam.models.enums.RoleEnum;
import com.example.ProjectExam.repositories.ArtistRepository;
import com.example.ProjectExam.repositories.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserServiceImplTest {

    private UserServiceImpl toTest;
    @Mock
    private PasswordEncoder mockPasswordEncoder;
    @Mock
    private UserRepository mockUserRepository;
    @Mock
    private ArtistRepository mockArtistRepository;

    @BeforeEach
    void setUp(){
        toTest=new UserServiceImpl(mockPasswordEncoder,mockUserRepository,mockArtistRepository);
    }

    @AfterEach
    void tearDown(){
        mockArtistRepository.deleteAll();
        mockUserRepository.deleteAll();
    }
    @Test
    public void testRegisterUser() {

        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setUsername("testUser");
        registerDTO.setPassword("password");
        registerDTO.setConfirmPassword("password");
        registerDTO.setEmail("test@example.com");
        registerDTO.setCountry("USA");
        registerDTO.setAge(25);
        registerDTO.setRole("User");

        when(mockUserRepository.existsByUsernameOrEmail("testUser", "test@example.com")).thenReturn(false);

        when(mockPasswordEncoder.encode("password")).thenReturn("password");

        boolean result = toTest.register(registerDTO);


        assertTrue(result);


        verify(mockUserRepository).save(argThat(user -> {
            return user.getUsername().equals("testUser")
                    && user.getPassword().equals("password")
                    && user.getEmail().equals("test@example.com")
                    && user.getCountry().equals("USA")
                    && user.getAge() == 25
                    && user.getRole() == RoleEnum.User;
        }));
    }
    @Test
    public void testRegisterAdmin() {

        RegisterDTO registerDTO1 = new RegisterDTO();
        registerDTO1.setUsername("testUser1");
        registerDTO1.setPassword("password1");
        registerDTO1.setConfirmPassword("password1");
        registerDTO1.setEmail("test1@example.com");
        registerDTO1.setCountry("USA");
        registerDTO1.setAge(25);
        registerDTO1.setRole("Admin");

        when(mockUserRepository.existsByUsernameOrEmail("testUser1", "test1@example.com")).thenReturn(false);

        when(mockPasswordEncoder.encode("password1")).thenReturn("password1");

        boolean result1 = toTest.register(registerDTO1);


        assertTrue(result1);


        verify(mockUserRepository).save(argThat(user1 -> {
            return user1.getUsername().equals("testUser1")
                    && user1.getPassword().equals("password1")
                    && user1.getEmail().equals("test1@example.com")
                    && user1.getCountry().equals("USA")
                    && user1.getAge() == 25
                    && user1.getRole() == RoleEnum.Admin;
        }));



    }
    @Test
    public void testRegisterArtist() {

        RegisterDTO registerDTO1 = new RegisterDTO();
        registerDTO1.setUsername("testUser1");
        registerDTO1.setPassword("password1");
        registerDTO1.setConfirmPassword("password1");
        registerDTO1.setEmail("test1@example.com");
        registerDTO1.setCountry("USA");
        registerDTO1.setAge(25);
        registerDTO1.setRole("Artist");

        when(mockUserRepository.existsByUsernameOrEmail("testUser1", "test1@example.com")).thenReturn(false);

        when(mockPasswordEncoder.encode("password1")).thenReturn("password1");

        boolean result1 = toTest.register(registerDTO1);


        assertTrue(result1);


        verify(mockUserRepository).save(argThat(user1 -> {
            return user1.getUsername().equals("testUser1")
                    && user1.getPassword().equals("password1")
                    && user1.getEmail().equals("test1@example.com")
                    && user1.getCountry().equals("USA")
                    && user1.getAge() == 25
                    && user1.getRole() == RoleEnum.Artist;
        }));
      ArtistEntity mockA=new ArtistEntity();
      mockA.setUsername(registerDTO1.getUsername());
      mockA.setEmail(registerDTO1.getEmail());
      mockA.setTotalArt(0);
      mockA.setTotalLikes(0);
      mockArtistRepository.save(mockA);
     when(mockArtistRepository.findByUsername("testUser1")).thenReturn(Optional.of(mockA));



    }
    @Test
    void registerDTOTest() {
        Assertions.assertEquals(0, mockUserRepository.count());

        RegisterDTO mockedAdd=new RegisterDTO();
        mockedAdd.setEmail("abv@abv.bg");
        mockedAdd.setUsername("Boris");
        mockedAdd.setPassword("tested");
        mockedAdd.setConfirmPassword("123");
        mockedAdd.setCountry("BG");
        mockedAdd.setRole(String.valueOf(RoleEnum.valueOf("Artist")));
        mockedAdd.setAge(12);


        UserEntity user=new UserEntity();
        user.setUsername(mockedAdd.getUsername());
        user.setEmail(mockedAdd.getEmail());
        user.setPassword(mockedAdd.getPassword());
        user.setCountry(mockedAdd.getCountry());
        user.setRole(RoleEnum.valueOf("Artist"));

        mockUserRepository.save(user);
        Optional<UserEntity> userNew = mockUserRepository.findByUsername(user.getUsername());

        Assertions.assertEquals(userNew, mockUserRepository.findByUsername(user.getUsername()));

        Assertions.assertEquals(0, mockArtistRepository.count());
        if(user.getRole().toString() == "Artist"){
            ArtistEntity artist=new ArtistEntity();
            artist.setUsername(user.getUsername());
            artist.setEmail(user.getEmail());
            artist.setTotalLikes(0);
            artist.setTotalArt(0);
            mockArtistRepository.save(artist);
            Optional<ArtistEntity> newArtist=mockArtistRepository.findByUsername(artist.getUsername());
            Assertions.assertEquals(newArtist, mockArtistRepository.findByUsername(artist.getUsername()));
        }

    }
    @Test
    void testRegisterDTOAge(){
        RegisterDTO newDTO = new RegisterDTO();
        newDTO.setAge(3);
        UserEntity user=new UserEntity();

        if(newDTO.getAge() >0 ){

            user.setAge(newDTO.getAge());
        }
        Assertions.assertEquals(user.getAge(),newDTO.getAge());

    }
    @Test
    void testRegisterDTOAgeError(){
        RegisterDTO newDTO = new RegisterDTO();

        if(newDTO.getAge()==0){
            return;
        }

        Assertions.assertEquals(0,newDTO.getAge());

    }
    @Test
    void roleNotCorrect(){
        RegisterDTO newDTO = new RegisterDTO();
        String role= newDTO.setRole("Boss");
        if((role.toString().equals("ROLE_Artist") && (role.toString().equals("ROLE_user")
        && (role.toString().equals("ROLE_Admin"))))){
            Mockito.doThrow(IllegalStateException.class);
        }


    }
    @Test
    void artistRoleCorrectTest(){
        RegisterDTO newDTO = new RegisterDTO();
        newDTO.setUsername("Bo");
        String role= newDTO.setRole("Artist");
        if((role.toString().equals("ROLE_Artist") && (role.toString().equals("ROLE_user")
                && (role.toString().equals("ROLE_Admin"))))){

            toTest.register(newDTO);

            UserEntity user=new UserEntity();
            user.setUsername(newDTO.getUsername());

            mockUserRepository.save(user);
            Assertions.assertEquals(1, mockUserRepository.count());
            ArtistEntity artist=new ArtistEntity();
            artist.setUsername(newDTO.getUsername());

            mockArtistRepository.save(artist);
            Assertions.assertEquals(1, mockArtistRepository.count());
        }


    }

    @Test
    @WithMockUser(username = "user", authorities = { "ROLE_User"})
    public void testAuthenticate() {
        UserEntity mockedUser=new UserEntity();
        mockedUser.setUsername("user");
        mockedUser.setPassword("testPassword");
        when(mockUserRepository.findByUsername("user")).thenReturn(Optional.of(mockedUser));
        when(mockPasswordEncoder.matches("testPassword", mockedUser.getPassword())).thenReturn(true);


        Assertions.assertEquals("testPassword", mockedUser.getPassword());
    }

    @Test
    void testSetRole() {
        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setRole("Admin");

        UserEntity user = new UserEntity();

        String rolenew = registerDTO.getRole();

        RoleEnum role = switch (rolenew) {
            case "Admin" -> RoleEnum.Admin;
            case "Artist" -> RoleEnum.Artist;
            case "User" -> RoleEnum.User;
            default -> throw new IllegalStateException("Unexpected value: " + rolenew);
        };

        user.setRole(role);

        assertEquals(RoleEnum.Admin, user.getRole());
    }

    @Test
    public void testSetRole_Admin() {
            RegisterDTO registerDTO = new RegisterDTO();
            registerDTO.setRole("Admin");

            UserEntity user = new UserEntity();

            String rolenew = registerDTO.getRole();

            RoleEnum role = switch (rolenew) {
                case "Admin" -> RoleEnum.Admin;
                case "Artist" -> RoleEnum.Artist;
                case "User" -> RoleEnum.User;
                default -> throw new IllegalStateException("Unexpected value: " + rolenew);
            };

            user.setRole(role);

            assertEquals(RoleEnum.Admin, user.getRole());
        }

        @Test
        public void testSetRole_Artist() {
            RegisterDTO registerDTO = new RegisterDTO();
            registerDTO.setRole("Artist");

            UserEntity user = new UserEntity();

            String rolenew = registerDTO.getRole();

            RoleEnum role = switch (rolenew) {
                case "Admin" -> RoleEnum.Admin;
                case "Artist" -> RoleEnum.Artist;
                case "User" -> RoleEnum.User;
                default -> throw new IllegalStateException("Unexpected value: " + rolenew);
            };

            user.setRole(role);

            assertEquals(RoleEnum.Artist, user.getRole());
        }

        @Test
        public void testSetRole_User() {
            RegisterDTO registerDTO = new RegisterDTO();
            registerDTO.setRole("User");

            UserEntity user = new UserEntity();

            String rolenew = registerDTO.getRole();

            RoleEnum role = switch (rolenew) {
                case "Admin" -> RoleEnum.Admin;
                case "Artist" -> RoleEnum.Artist;
                case "User" -> RoleEnum.User;
                default -> throw new IllegalStateException("Unexpected value: " + rolenew);
            };

            user.setRole(role);

            assertEquals(RoleEnum.User, user.getRole());

    }

    @Test
    public void testSetRole_UnexpectedValue() {

        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setRole("UserpOL");

        UserEntity mockuser = new UserEntity();

        String rolenew = registerDTO.getRole();

        if(rolenew != "Admin" &&  rolenew != "Artist" && rolenew != "User"){
            new IllegalStateException("Unexpected value: " + rolenew);
        }
        return;
    }


    @Test
    void findByUsername() {
        UserEntity mockUser = new UserEntity();
        mockUser.setUsername("test");
        mockUser.setPassword("password");

        // mock the behavior of the userRepository
        when(mockUserRepository.findByUsername("test")).thenReturn(Optional.of(mockUser));

        // call the method being tested
        Optional<UserEntity> result = toTest.findByUsername("test");

        // assert the results
        assertThat(result).isPresent();
        assertThat(result.get().getUsername()).isEqualTo("test");
        assertThat(result.get().getPassword()).isEqualTo("password");
        Assertions.assertNotNull(mockUser);
        Assertions.assertNotEquals("", mockUser.getUsername());
       
    }
    @Test
    void existByUsernameOrEmailTest() {
        String nonexistentUsername = "nonexistentuser";
        String nonemail = "nonexistentuser@po.po";

        Boolean user = mockUserRepository.existsByUsernameOrEmail(nonexistentUsername, nonemail);

        if (user) {
            assertTrue(mockUserRepository.existsByUsernameOrEmail(nonexistentUsername, nonemail));
            return;
        }

    }
    @Test
    void testRegister_PasswordMismatch() throws Exception {
        RegisterDTO mismatchPasswordDTO = new RegisterDTO();
        mismatchPasswordDTO.setPassword("123");
        mismatchPasswordDTO.setConfirmPassword("12");

        if(!mismatchPasswordDTO.getPassword().equals(mismatchPasswordDTO.getConfirmPassword())){
        assertFalse(toTest.register(mismatchPasswordDTO));
         }
        }
    @Test
    void testRegister_UserAlreadyExists() throws Exception {
        RegisterDTO mismatchPasswordDTO = new RegisterDTO();
        mismatchPasswordDTO.setEmail("abv@abv.bg");
        RegisterDTO existingUserDTO = new RegisterDTO();
        existingUserDTO.setEmail("abv@abv.bg");
        if(mismatchPasswordDTO.getEmail().equals(mockUserRepository
                .existsByUsernameOrEmail(existingUserDTO.getEmail(), existingUserDTO.getUsername()))){
            assertFalse(toTest.register(existingUserDTO));
        }

    }

}