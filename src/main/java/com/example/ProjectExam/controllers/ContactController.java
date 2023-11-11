package com.example.ProjectExam.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
@Controller
public class ContactController {
    @GetMapping("/contacts")
    public ModelAndView weapon(){
        return new ModelAndView("contacts");
    }
}
