package com.example.ProjectExam.repositories;

import com.example.ProjectExam.models.entities.ArtistEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtistRepository extends JpaRepository<ArtistEntity,Long> {






    ArtistEntity findByUsername(String username);
}
