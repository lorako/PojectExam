package com.example.ProjectExam.repositories;

import com.example.ProjectExam.models.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long> {
    boolean existsByUsernameOrEmail(String username, String email);

    UserEntity findByUsername(String username);


}
