package com.example.bobiShop.repositories;

import com.example.bobiShop.models.entities.PowerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PowerRepository extends JpaRepository<PowerEntity,Long> {
}
