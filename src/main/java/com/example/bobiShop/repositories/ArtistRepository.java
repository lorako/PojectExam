package com.example.bobiShop.repositories;

import com.example.bobiShop.models.entities.ArtistEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtistRepository extends JpaRepository<ArtistEntity,Long> {






    ArtistEntity findByUsername(String username);
}
