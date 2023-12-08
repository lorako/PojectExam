package com.example.ProjectExam.services.Impl;

import com.example.ProjectExam.models.DTOs.BindingModel.ContactFormAddDTO;
import com.example.ProjectExam.models.DTOs.View.ContactViewDTO;
import com.example.ProjectExam.models.entities.ContactFormEntity;
import com.example.ProjectExam.models.entities.UserEntity;
import com.example.ProjectExam.models.enums.RoleEnum;
import com.example.ProjectExam.repositories.ContactRepository;
import com.example.ProjectExam.services.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
class ContactServiceFormImplTest {

    private ContactServiceFormImpl toTest;

   @Mock
    private UserService mockUserService;
   @Mock
    private ContactRepository mockContactRepository;

    @BeforeEach
     void setUp (){
       toTest=new ContactServiceFormImpl(mockUserService, mockContactRepository);
     }

    @AfterEach
    void tearDown() {
        mockContactRepository.deleteAll();

    }


    @Test
    void safeForm() {

        ContactFormAddDTO contactFormAddDTO=testContactCreat();
        ContactFormEntity con=new ContactFormEntity();
        con.setText(contactFormAddDTO.getText());
        con.setEmail(contactFormAddDTO.getEmail());
        con.setUsername(contactFormAddDTO.getUsername());

       Optional<UserEntity> user= mockUserService.findByUsername("Boris");



       if("abv@abv.bg"== contactFormAddDTO.getEmail()) {


           mockContactRepository.save(con);

       }else{
           return;
       }
        Assertions.assertNotNull(mockContactRepository.count()>0, String.valueOf(true));
        Assertions.assertEquals(con.getUsername(),"Boris");
        Assertions.assertEquals(con.getText(), "ababab");
        Assertions.assertEquals(con.getEmail(), "abv@abv.bg");

    }

    @Test
    void testSafeForm() {
        UserEntity mockU = new UserEntity();
        mockU.setUsername("Lora");
        mockU.setEmail("test@example.com");

        ContactFormAddDTO mockAdd=new ContactFormAddDTO();
        mockAdd.setEmail("test@example.com");
        mockAdd.setText("fghjkl");
        mockAdd.setUsername("Lora");



        Mockito.when(mockUserService.findByUsername("Lora")).thenReturn(Optional.of(mockU));


        ContactFormEntity mockF = new ContactFormEntity();
        mockF.setEmail(mockAdd.getEmail());
        mockF.setText(mockAdd.getText());
        mockF.setUsername(mockAdd.getUsername());
        toTest.safeForm(mockAdd);
        Mockito.when(mockContactRepository.save(Mockito.any(ContactFormEntity.class))).thenReturn(mockF);

        Mockito.verify(mockUserService).findByUsername("Lora");
        Assertions.assertEquals("Lora",mockF.getUsername());
        Assertions.assertEquals("fghjkl",mockF.getText());
        Assertions.assertEquals("test@example.com",mockF.getEmail());

    }

    @Test
    void safeFormNoEmail(){

        ContactFormEntity con=new ContactFormEntity();
        con.setText("lloiuy");
        con.setEmail("");
        con.setUsername("no");
        if(con.getEmail().isBlank()){
            return;
        }

    }

    private ContactFormAddDTO testContactCreat(){
        ContactFormAddDTO contactForm=new ContactFormAddDTO();

        contactForm.setEmail("abv@abv.bg");
        contactForm.setText("ababab");
        contactForm.setUsername("Boris");

        return contactForm;
    }

    @Test
    void findAll() {

        List<ContactFormEntity> mockContactEntities = Arrays.asList();
        when(mockContactRepository.findAll()).thenReturn(mockContactEntities);

        List<ContactViewDTO> expectedContactDTOs = mockContactEntities
                .stream()
                .map(ContactViewDTO::new)
                .toList();

        List<ContactViewDTO> actualContactDTOs = toTest.findAll();

         Assertions.assertEquals(expectedContactDTOs, actualContactDTOs);

        List<ContactFormEntity> all = testContactList();



       Assertions.assertEquals(2, all.size());
       Assertions.assertEquals(all.get(0).getEmail(),"abv@abv.bg" );
       Assertions.assertEquals(all.get(1).getEmail(),"abv1@abv.bg" );
       Assertions.assertEquals(all.get(0).getUsername(),"Boris" );
       Assertions.assertEquals(all.get(1).getText(),"ababab1" );
    }

    private List<ContactFormEntity> testContactList() {

        List<ContactFormEntity> list=new ArrayList<>();

        ContactFormEntity contactForm=new ContactFormEntity();
        contactForm.setEmail("abv@abv.bg");
        contactForm.setText("ababab");
        contactForm.setUsername("Boris");
        list.add(contactForm);

        ContactFormEntity contactForm1=new ContactFormEntity();
        contactForm1.setEmail("abv1@abv.bg");
        contactForm1.setText("ababab1");
        contactForm1.setUsername("Boris1");
        list.add(contactForm1);
        return list;
    }

    @Test
    void deleteByIdAdmin() {

        ContactFormEntity con=testContactEntityCreat();
        UserEntity user=new UserEntity();
        user.setUsername("Lora");
        user.setRole(RoleEnum.valueOf("Admin"));

        if(user.getRole().name().equals("Admin")) {
            mockContactRepository.deleteById(con.getId());

            assertNotEquals(mockContactRepository.findAll(), false);
            verify(mockContactRepository).deleteById(con.getId());
        }

    }

    @Test
    void deleteWhenIdNotExist() {

        ContactFormEntity con=testContactEntityCreat();
        UserEntity user=new UserEntity();
        user.setUsername("Lora");
        user.setRole(RoleEnum.valueOf("Admin"));
        long id=4;

        if(mockContactRepository.findById(id).isEmpty()) {
          return;
        }

    }
    @Test
    void deleteByIdNOTAdmin() {

        ContactFormEntity con=testContactEntityCreat();
        UserEntity user=new UserEntity();
        user.setUsername("Lora");
        user.setRole(RoleEnum.valueOf("User"));

        if(user.getRole().name().equals("Admin")) {
            return;
        }

    }


    @Test
    public void testSafeForm_Failure() throws Exception {
        ContactFormAddDTO invalidContactFormAddDTO=new ContactFormAddDTO();
        invalidContactFormAddDTO.setEmail("");
        invalidContactFormAddDTO.setUsername("");
        UserEntity mockUser = new UserEntity();
        when(mockUserService.findByUsername(invalidContactFormAddDTO.getUsername())).thenReturn(Optional.of(mockUser));

        verify(mockContactRepository, Mockito.never()).save(Mockito.any(ContactFormEntity.class));
    }

    private ContactFormEntity testContactEntityCreat() {
        ContactFormEntity contactForm=new ContactFormEntity();



        contactForm.setEmail("abv@abv.bg");
        contactForm.setText("ababab");
        contactForm.setUsername("Boris");
        contactForm.setId(1);

        return contactForm;
    }
    @Test
    public void testNewContactFormEntitySuccess() {

        ContactFormAddDTO contactFormAddDTO = new ContactFormAddDTO();
        contactFormAddDTO.setEmail("lolo");
        contactFormAddDTO.setText("lolo");
        contactFormAddDTO.setUsername("oloo");
        ContactFormEntity contact = new ContactFormEntity();
        contact.setEmail(contactFormAddDTO.getEmail());
        contact.setUsername(contactFormAddDTO.getUsername());
        contact.setText(contactFormAddDTO.getText());


        Assertions.assertEquals(contactFormAddDTO.getEmail(), "lolo");
        Assertions.assertEquals(contactFormAddDTO.getUsername(), contact.getUsername());
        Assertions.assertEquals(contactFormAddDTO.getText(), contact.getText());
    }
    @Test
    public void testNewContactFormEntityError() {

        ContactFormAddDTO contactFormAddDTO = new ContactFormAddDTO();
        contactFormAddDTO.setEmail("");
        contactFormAddDTO.setText("");
        contactFormAddDTO.setUsername("");

        ContactFormEntity contact = new ContactFormEntity();
        contact.setEmail(contactFormAddDTO.getEmail());
        contact.setUsername(contactFormAddDTO.getUsername());
        contact.setText(contactFormAddDTO.getText());
        if (contactFormAddDTO.getEmail().isBlank() || contactFormAddDTO.getUsername().isBlank() ||
                contactFormAddDTO.getText().isBlank()) {
            return;
        }
        }


}