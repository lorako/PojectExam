package com.example.ProjectExam.repositories;

import com.example.ProjectExam.models.entities.ContactFormEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends JpaRepository<ContactFormEntity, Long> {

}
