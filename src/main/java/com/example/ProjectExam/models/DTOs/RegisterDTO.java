package com.example.ProjectExam.models.DTOs;

import com.example.ProjectExam.models.enums.RoleEnum;
import jakarta.validation.constraints.*;

public class RegisterDTO {
    @NotEmpty(message = "Username must be between 3 and 15 characters!" )
    @Size(min=3, max=15)
    private String username;

    @Email(message = "Must be a valid email format!")
    @NotEmpty
    private String email;
    @NotEmpty(message = "Password must be between 4 and 12 characters!")
    private String password;
    @NotEmpty(message = "Passwords must match!")
    private String confirmPassword;
    @Positive
    private int age;

    @NotNull(message = "You must select a role")
    private RoleEnum role;
    @NotEmpty
    private String country;

    public RoleEnum getRole() {
        return role;
    }

    public void setRole(RoleEnum role) {
        this.role = role;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
