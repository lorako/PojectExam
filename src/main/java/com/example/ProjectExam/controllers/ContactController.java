package com.example.ProjectExam.controllers;

import com.example.ProjectExam.models.DTOs.BindingModel.ContactFormAddDTO;
import com.example.ProjectExam.services.ContactFormService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
public class ContactController {

    private final ContactFormService contactFormService;

    public ContactController(ContactFormService contactFormService) {
        this.contactFormService = contactFormService;
    }



    @GetMapping("/contacts")
    public ModelAndView contact(){
        return new ModelAndView("contacts");
    }

    @PostMapping("/contacts")
    public String submitForm(ContactFormAddDTO contactFormAddDTO, Principal principal){

        contactFormService.safeForm(contactFormAddDTO);

        return ("redirect:/thanks");

    }
    @GetMapping("/thanks")
    public ModelAndView thanks(){
        return new ModelAndView("thanks");
    }
}
