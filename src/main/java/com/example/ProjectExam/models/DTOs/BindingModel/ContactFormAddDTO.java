package com.example.ProjectExam.models.DTOs.BindingModel;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ContactFormAddDTO {
    @Size(min=3, max=15)
    @NotEmpty(message = "Name must be between 3 and 15 characters!")
    private String username;

    @Email
    private String email;
    @Size(min=3)
    @NotNull(message = "Description must be min 3 characters!")
    private String text;

    public ContactFormAddDTO() {
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
