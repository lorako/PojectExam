package com.example.ProjectExam.models.event;

import org.springframework.context.ApplicationEvent;

public class ContactFormEvent extends ApplicationEvent {

   private final String adminEmail;


    public ContactFormEvent(Object source,String adminEmail) {
        super(source);
        this.adminEmail = adminEmail;
    }

    public String getAdminEmail(){
        return adminEmail;
    }


}
