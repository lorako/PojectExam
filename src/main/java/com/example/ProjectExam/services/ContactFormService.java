package com.example.ProjectExam.services;

import com.example.ProjectExam.models.DTOs.ContactFormAddDTO;
import com.example.ProjectExam.models.DTOs.ContactViewDTO;

import java.util.List;

public interface ContactFormService {
    void safeForm(ContactFormAddDTO contactFormAddDTO);

    List<ContactViewDTO> findAll();
}
