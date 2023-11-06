package com.example.bobiShop.repositories;

import com.example.bobiShop.models.entities.WeaponEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeaponRepository extends JpaRepository<WeaponEntity,Long> {
}
