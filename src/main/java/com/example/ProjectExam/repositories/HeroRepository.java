package com.example.ProjectExam.repositories;

import com.example.ProjectExam.models.entities.HeroEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HeroRepository extends JpaRepository<HeroEntity,Long> {
    @Query(nativeQuery = true, value = "SELECT * FROM heroes")
    List<HeroEntity> getAll();

    List<HeroEntity> findAllHeroesByCreatorId(Long id);
    Optional<HeroEntity> findByHeroName(String newHeroName);


}
