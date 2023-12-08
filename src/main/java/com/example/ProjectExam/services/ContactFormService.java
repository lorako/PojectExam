package com.example.ProjectExam.services;

import com.example.ProjectExam.models.DTOs.BindingModel.ContactFormAddDTO;
import com.example.ProjectExam.models.DTOs.View.ContactViewDTO;

import java.util.List;

public interface ContactFormService {
    boolean safeForm(ContactFormAddDTO contactFormAddDTO);

    List<ContactViewDTO> findAll();

    void deleteById(Long id);
}
