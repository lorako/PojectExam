package com.example.ProjectExam.repositories;

import com.example.ProjectExam.models.entities.WeaponEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WeaponRepository extends JpaRepository<WeaponEntity,Long> {




    Optional<WeaponEntity> findByWeaponName(String weaponName);
}
