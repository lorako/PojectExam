package com.example.bobiShop.services;

import com.example.bobiShop.models.DTOs.Item;
import com.example.bobiShop.models.DTOs.ShoppingBagsDTO;
import com.example.bobiShop.models.entities.HeroEntity;
import com.example.bobiShop.models.entities.UserEntity;
import com.example.bobiShop.repositories.UserRepository;
import com.example.bobiShop.session.LoggedUser;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShoppingServiceImpl implements ShoppingService{

    private final UserRepository userRepository;
    private final LoggedUser loggedUser;

    public ShoppingServiceImpl(UserRepository userRepository, LoggedUser loggedUser) {
        this.userRepository = userRepository;
        this.loggedUser = loggedUser;
    }




    @Override
    public List<Item> getAll() {

        UserEntity userBuyer= userRepository.findByUsername(loggedUser.getUsername());
        return userBuyer.getMyBoughtCollection();

    }
    @Override
    public BigDecimal total(){
        BigDecimal totalPriceItems = BigDecimal.valueOf(0);
        UserEntity userBuyer= userRepository.findByUsername(loggedUser.getUsername());
        List<Item> myBoughtCollections = userBuyer.getMyBoughtCollection();


     //  for (ItemDTO item :myBoughtCollections){
       //     totalPriceItems += item.getPrice();
     //  }


        return totalPriceItems;
    }
}
