package com.example.ProjectExam.services.Impl;

import com.example.ProjectExam.models.entities.UserEntity;
import com.example.ProjectExam.models.enums.RoleEnum;
import com.example.ProjectExam.repositories.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProjectUserDetailServiceTest {

    private ProjectUserDetailService toTest;
    @Mock
    private UserRepository moockUserRepository;


    @BeforeEach
    void setUp(){
       toTest=new ProjectUserDetailService(
               moockUserRepository);
   }
    @AfterEach
    void tearDown(){

        moockUserRepository.deleteAll();
    }
   @Test
   void testUserNotFound(){
       assertThrows(UsernameNotFoundException.class,
               ()-> toTest.loadUserByUsername("pesho"));
   }

   @Test
    void testUserFoundException(){
        UserEntity testUser=createTestUser();
        when(moockUserRepository.findByUsername(testUser.getUsername()))
                .thenReturn(Optional.of(testUser));
       UserDetails userDetails=toTest.loadUserByUsername(testUser.getUsername());

       Assertions.assertNotNull(userDetails);
       Assertions.assertEquals(testUser.getUsername(), userDetails.getUsername());
        Assertions.assertEquals(testUser.getPassword(),userDetails.getPassword());
        Assertions.assertEquals(1, userDetails.getAuthorities().size());
        Assertions.assertEquals(testUser.getRole(),RoleEnum.valueOf("Admin"));
   }

   private static UserEntity createTestUser(){
       UserEntity user=new UserEntity();
       user.setEmail("user@abv.bg");
       user.setUsername("user");
       user.setPassword("123");
       user.setRole(RoleEnum.valueOf("Admin"));
       return user;
   }
}
