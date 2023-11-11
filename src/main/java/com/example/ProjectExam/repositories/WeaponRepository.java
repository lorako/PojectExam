package com.example.ProjectExam.repositories;

import com.example.ProjectExam.models.entities.WeaponEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeaponRepository extends JpaRepository<WeaponEntity,Long> {
}
