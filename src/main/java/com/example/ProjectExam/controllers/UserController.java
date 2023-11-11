package com.example.ProjectExam.controllers;

import com.example.ProjectExam.models.DTOs.LoginDTO;
import com.example.ProjectExam.models.DTOs.RegisterDTO;
import com.example.ProjectExam.models.entities.UserEntity;
import com.example.ProjectExam.services.UserService;
import com.example.ProjectExam.session.LoggedUser;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {

    private final LoggedUser loggedUser;
    private final UserService userService;

    public UserController(LoggedUser loggedUser, UserService userService) {
        this.loggedUser = loggedUser;
        this.userService = userService;
    }


    @GetMapping("/register")
    public ModelAndView register(@ModelAttribute("registerDTO") RegisterDTO registerDTO){

        return new ModelAndView("register", "user", new UserEntity());
    }

    @GetMapping("/login")
    public ModelAndView login(@ModelAttribute("loginDTO") LoginDTO loginDTO){

        return new ModelAndView("login");
    }
    @PostMapping("/register")
    public ModelAndView register(@ModelAttribute("registerDTO") @Valid RegisterDTO registerDTO, BindingResult bindingResult) {

        if (loggedUser.isLogged()) {
            return new ModelAndView("redirect:/home");
        }

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
    @PostMapping("/login")
    public ModelAndView login(@ModelAttribute("loginDTO") @Valid LoginDTO loginDTO, BindingResult bindingResult) {

        if (loggedUser.isLogged()) {
            return new ModelAndView("redirect:/home");
        }

        if (bindingResult.hasErrors()) {
            return new ModelAndView("login");
        }
        boolean hasSuccess = userService.login(loginDTO);
        if (!hasSuccess) {
            ModelAndView modelAndView = new ModelAndView("login");
            modelAndView.addObject("hasError", true);
            return modelAndView;
        }
        return new ModelAndView("redirect:/home");

    }

    @GetMapping("/logout")
    public ModelAndView  logout(){
        if (!loggedUser.isLogged()) {
            return new ModelAndView("redirect:/home");
        }
        userService.logout();

        return new ModelAndView("redirect:/");
    }

}
