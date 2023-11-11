package com.example.ProjectExam.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import org.hibernate.validator.constraints.Length;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="artists")
public class ArtistEntity extends BaseEntity{

    @Column(nullable = false, unique = true)
    @Length(min=3, max=15)
    private String username;
    @Column(nullable = false)
    @Email
    private String email;

    @OneToMany(fetch = FetchType.EAGER )
    private List<HeroEntity> heroesList;
    @OneToMany(fetch = FetchType.EAGER )
    private List<WeaponEntity> weaponsList;
    @OneToMany(fetch = FetchType.EAGER )
    private List<HabitatEntity> habitatList;

    public ArtistEntity() {
        heroesList=new ArrayList<>();
        weaponsList=new ArrayList<>();
        habitatList=new ArrayList<>();
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



    public List<HeroEntity> getHeroesList() {
        return heroesList;
    }

    public void setHeroesList(List<HeroEntity> heroesList) {
        this.heroesList = heroesList;
    }

    public List<WeaponEntity> getWeaponsList() {
        return weaponsList;
    }

    public void setWeaponsList(List<WeaponEntity> weaponsList) {
        this.weaponsList = weaponsList;
    }

    public List<HabitatEntity> getHabitatList() {
        return habitatList;
    }


    public void setHabitatList(List<HabitatEntity> habitatList) {
        this.habitatList = habitatList;
    }
}
