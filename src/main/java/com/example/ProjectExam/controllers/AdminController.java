package com.example.ProjectExam.controllers;

import com.example.ProjectExam.models.DTOs.View.ContactViewDTO;
import com.example.ProjectExam.services.ContactFormService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class AdminController {

    private final ContactFormService contactFormService;

    public AdminController(ContactFormService contactFormService) {
        this.contactFormService = contactFormService;
    }

    @GetMapping("/admin")
    public ModelAndView onlyAdmin(){
       List<ContactViewDTO> all = contactFormService.findAll();

        return new ModelAndView("admin").addObject("all",all);
    }
    @GetMapping("/admin/delete/{id}")
    public ModelAndView deletePost(@PathVariable("id") Long id){


       contactFormService.deleteById(id);

        return new ModelAndView("redirect:/admin");
    }
}
