package com.example.ProjectExam.services.Impl;

import com.example.ProjectExam.Exceptions.UserNotFoundException;
import com.example.ProjectExam.models.DTOs.BindingModel.ContactFormAddDTO;
import com.example.ProjectExam.models.DTOs.View.ContactViewDTO;
import com.example.ProjectExam.models.entities.ContactFormEntity;
import com.example.ProjectExam.models.entities.UserEntity;
import com.example.ProjectExam.repositories.ContactRepository;
import com.example.ProjectExam.services.ContactFormService;
import com.example.ProjectExam.services.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactServiceFormImpl implements ContactFormService {
    private final UserService userService;
    private final ContactRepository contactRepository;

    public ContactServiceFormImpl(UserService userService, ContactRepository contactRepository) {
        this.userService = userService;
        this.contactRepository = contactRepository;
    }

    @Override
    public boolean safeForm(ContactFormAddDTO contactFormAddDTO) {

        String username= contactFormAddDTO.getUsername();

        UserEntity user= userService.findByUsername(username).orElseThrow(()->new UserNotFoundException("Not found user!"));


        String email =user.getEmail();
        if(!email.equals(contactFormAddDTO.getEmail())){
            return false;
        }

        ContactFormEntity contact=new ContactFormEntity();
        contact.setEmail(contactFormAddDTO.getEmail());
        contact.setUsername(contactFormAddDTO.getUsername());
        contact.setText(contactFormAddDTO.getText());

        contactRepository.save(contact);
        return true;
    }

    @Override
    public List<ContactViewDTO> findAll() {
        List<ContactFormEntity> contacts= contactRepository.findAll();

       return contacts
               .stream()
               .map(ContactViewDTO::new).toList();


    }

    @Override
    public void deleteById(Long id) {
        contactRepository.deleteById(id);
    }
}
