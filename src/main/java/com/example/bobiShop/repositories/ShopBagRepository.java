package com.example.bobiShop.repositories;


import com.example.bobiShop.models.entities.ShopBagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShopBagRepository  extends JpaRepository<ShopBagEntity, Long> {


}
