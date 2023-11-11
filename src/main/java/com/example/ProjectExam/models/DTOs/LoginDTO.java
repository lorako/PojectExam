package com.example.ProjectExam.models.DTOs;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class LoginDTO {
    @NotEmpty(message = "Username must be between 3 and 15 characters!" )
    @Size(min=3, max=15)
    private String username;


    @NotEmpty(message = "Password must be between 4 and 12 characters!")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
