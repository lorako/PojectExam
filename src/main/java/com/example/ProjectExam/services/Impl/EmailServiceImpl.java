package com.example.ProjectExam.services.Impl;

import com.example.ProjectExam.services.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;


@Service
public class EmailServiceImpl implements EmailService {

    private final TemplateEngine templateEngine;

    private final JavaMailSender javaMailSender;

    private final String projectShopEmail;

    public EmailServiceImpl(TemplateEngine templateEngine,
                            JavaMailSender javaMailSender,
                            @Value("${mail.projectExam}") String projectShopEmail) {
        this.templateEngine = templateEngine;
        this.javaMailSender = javaMailSender;
        this.projectShopEmail=projectShopEmail;
    }


    @Override
    public void contactFormReceivedEmail(String adminEmail) {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);

        try {

            mimeMessageHelper.setTo(adminEmail);
            mimeMessageHelper.setFrom(projectShopEmail);
            mimeMessageHelper.setReplyTo(projectShopEmail);
            mimeMessageHelper.setSubject("New form arrived!");
            mimeMessageHelper.setText(generatingBodyEmail(), true);

            javaMailSender.send(mimeMessageHelper.getMimeMessage());
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    private String generatingBodyEmail() {
        Context context = new Context();

        return templateEngine.process("emailAdminForm.html", context);

    }
}