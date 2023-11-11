package com.example.ProjectExam.session;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
public class LoggedUser {


    private String username;

    private String email;
    private boolean isLogged;



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isLogged() {
        return isLogged;
    }

    public void setLogged(boolean logged) {
        isLogged = logged;

    }
    public void login(String username){
        this.username=username;
        this.isLogged=true;
    }
    public void logout(){
        this.username=null;
        this.isLogged=false;
    }


}
