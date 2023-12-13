package com.example.ProjectExam.services;

import com.example.ProjectExam.models.event.ContactFormEvent;

public interface ContactFormEmailService {

    void contactFormReceived(ContactFormEvent contactFormEvent);
}
