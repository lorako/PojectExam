package com.example.ProjectExam.controllers;


import com.example.ProjectExam.models.DTOs.BindingModel.RegisterDTO;
import com.example.ProjectExam.services.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RegisterController {


    private final UserService userService;

    public RegisterController(UserService userService) {

        this.userService = userService;
    }


    @GetMapping("/register")
    public ModelAndView register(@ModelAttribute("registerDTO") RegisterDTO registerDTO){

        return new ModelAndView();
    }

    @PostMapping("/register")
    public ModelAndView register(@ModelAttribute("registerDTO") @Valid RegisterDTO registerDTO, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return new ModelAndView("register");
        }

        boolean hasSuccess = userService.register(registerDTO);
        if (!hasSuccess) {
            ModelAndView modelAndView = new ModelAndView("register");
            modelAndView.addObject("hasError", true);
            return modelAndView;
        }
        return new ModelAndView("redirect:/login");

    }



}
