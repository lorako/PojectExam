package com.example.ProjectExam.repositories;

import com.example.ProjectExam.models.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long> {
    Boolean existsByUsernameOrEmail(String username, String email);

    Optional<UserEntity> findByUsername(String username);


}
