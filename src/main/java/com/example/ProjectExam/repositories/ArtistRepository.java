package com.example.ProjectExam.repositories;

import com.example.ProjectExam.models.entities.ArtistEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArtistRepository extends JpaRepository<ArtistEntity,Long> {


    Optional<ArtistEntity> findByUsername(String username);


    List<ArtistEntity> findAll();

    void save(Optional<ArtistEntity> creatorHero);


}
