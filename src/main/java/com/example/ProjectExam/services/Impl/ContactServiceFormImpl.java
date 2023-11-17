package com.example.ProjectExam.services.Impl;

import com.example.ProjectExam.models.DTOs.ContactFormAddDTO;
import com.example.ProjectExam.models.DTOs.ContactViewDTO;
import com.example.ProjectExam.models.entities.ContactFormEntity;
import com.example.ProjectExam.models.entities.UserEntity;
import com.example.ProjectExam.repositories.ContactRepository;
import com.example.ProjectExam.repositories.UserRepository;
import com.example.ProjectExam.services.ContactFormService;
import com.example.ProjectExam.services.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
public class ContactServiceFormImpl implements ContactFormService {
    private final UserService userService;
    private final ContactRepository contactRepository;

    public ContactServiceFormImpl(UserService userService, ContactRepository contactRepository) {
        this.userService = userService;
        this.contactRepository = contactRepository;
    }

    @Override
    public void safeForm(ContactFormAddDTO contactFormAddDTO) {

        String username= contactFormAddDTO.getUsername();

        Optional<UserEntity> user=userService.findByUsername(username);

        if(user.isEmpty()){
            return;
        }

        String email =user.get().getEmail();
        if(!email.equals(contactFormAddDTO.getEmail())){
            return;
        }

        ContactFormEntity contact=new ContactFormEntity();
        contact.setEmail(contactFormAddDTO.getEmail());
        contact.setUsername(contactFormAddDTO.getUsername());
        contact.setText(contactFormAddDTO.getText());

        contactRepository.save(contact);
    }

    @Override
    public List<ContactViewDTO> findAll() {
        List<ContactFormEntity> contacts= contactRepository.findAll();

       return contacts
               .stream()
               .map(ContactViewDTO::new).toList();

    }
}
