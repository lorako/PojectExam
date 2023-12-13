package com.example.ProjectExam.models.DTOs.RestDTO;

import com.example.ProjectExam.models.entities.UserEntity;

public class UserRestDTO {



    private String username;

    private String email;


    private int age;

    private String country;

    public UserRestDTO() {
    }

    public UserRestDTO(UserEntity userEntity) {
        this.username = userEntity.getUsername();
        this.email = userEntity.getEmail();
        this.age = userEntity.getAge();
        this.country = userEntity.getCountry();
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
