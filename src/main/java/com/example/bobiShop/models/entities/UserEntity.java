package com.example.bobiShop.models.entities;


import com.example.bobiShop.models.DTOs.Item;
import com.example.bobiShop.models.enums.RoleEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.Length;

import java.util.ArrayList;
import java.util.List;

@Table(name="users")
@Entity
public class UserEntity extends BaseEntity{

    @Column(nullable = false, unique = true)
    @Length(min=3, max=15)
    private String username;
    @Column(nullable = false)
    @Email
    private String email;
    @Column
    private String password;
    @Column
    @Positive
    private int age;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoleEnum role;
    @Column(nullable = false)
    private String country;
    @OneToMany(fetch = FetchType.EAGER)
    private List<Item> myBoughtCollection;


    public UserEntity() {
    myBoughtCollection=new ArrayList<Item>();
    }

    public String getUsername() {
        return username;
    }

    public RoleEnum getRole() {
        return role;
    }

    public void setRole(RoleEnum role) {
        this.role = role;
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

    public List<Item> getMyBoughtCollection() {
        return myBoughtCollection;
    }

    public void setMyBoughtCollection(List<Item> myBoughtCollection) {
        this.myBoughtCollection = myBoughtCollection;
    }
}
