package com.example.ProjectExam.services.Impl;

import com.example.ProjectExam.models.event.ContactFormEvent;
import com.example.ProjectExam.services.ContactFormEmailService;
import com.example.ProjectExam.services.EmailService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class ContactFormEmailServiceImpl implements ContactFormEmailService {

    private final EmailService emailService;

    public ContactFormEmailServiceImpl(EmailService emailService) {
        this.emailService = emailService;
    }

    @Override
    @EventListener(ContactFormEvent.class)
    public void contactFormReceived(ContactFormEvent contactFormEvent) {

    emailService.contactFormReceivedEmail(contactFormEvent.getAdminEmail());



    }
}
