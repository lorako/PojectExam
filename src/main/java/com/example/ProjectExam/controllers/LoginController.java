package com.example.ProjectExam.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {


    @GetMapping("/login")
    public ModelAndView login(){

        return new ModelAndView("login");
    }
    @PostMapping("/login-error")
    public ModelAndView onFailure(ModelAndView modelAndView) {

       return  new ModelAndView("login").addObject("hasError", "true");


    }
}
