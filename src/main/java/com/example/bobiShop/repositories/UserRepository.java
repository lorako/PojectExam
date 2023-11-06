package com.example.bobiShop.repositories;

import com.example.bobiShop.models.entities.ArtistEntity;
import com.example.bobiShop.models.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long> {
    boolean existsByUsernameOrEmail(String username, String email);

    UserEntity findByUsername(String username);
}
