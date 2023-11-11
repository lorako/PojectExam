package com.example.ProjectExam.repositories;


import com.example.ProjectExam.models.entities.ShopBagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopBagRepository  extends JpaRepository<ShopBagEntity, Long> {


}
