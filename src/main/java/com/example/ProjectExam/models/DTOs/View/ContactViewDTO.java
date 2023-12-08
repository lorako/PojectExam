package com.example.ProjectExam.models.DTOs.View;

import com.example.ProjectExam.models.entities.ContactFormEntity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ContactViewDTO {

    private Long id;

    private String username;


    private String email;

    private String text;

    public ContactViewDTO(ContactFormEntity contactForm) {
        this.username = contactForm.getUsername();
        this.id= contactForm.getId();
        this.email = contactForm.getEmail();
        this.text = contactForm.getText();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
