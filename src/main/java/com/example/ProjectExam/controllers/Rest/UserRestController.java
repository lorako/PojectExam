package com.example.ProjectExam.controllers.Rest;

import com.example.ProjectExam.models.DTOs.RestDTO.UserRestDTO;
import com.example.ProjectExam.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/users")
public class UserRestController {

    private final UserService userService;


    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserRestDTO>> getAllUsers(){

        return ResponseEntity.ok(
                this.userService.getAllUsersRest());

    }
}
