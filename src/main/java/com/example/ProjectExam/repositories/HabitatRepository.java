package com.example.ProjectExam.repositories;

import com.example.ProjectExam.models.entities.HabitatEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HabitatRepository extends JpaRepository<HabitatEntity,Long> {
}
