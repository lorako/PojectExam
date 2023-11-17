package com.example.ProjectExam.services.Impl;

import com.example.ProjectExam.models.DTOs.ShopBagDTO;
import com.example.ProjectExam.models.entities.ShopBagEntity;
import com.example.ProjectExam.models.entities.UserEntity;
import com.example.ProjectExam.repositories.ShopBagRepository;
import com.example.ProjectExam.repositories.UserRepository;
import com.example.ProjectExam.services.ShopBagService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ShopBagServiceImpl implements ShopBagService {

    private final UserRepository userRepository;

    private final ShopBagRepository shopBagRepository;

    public ShopBagServiceImpl(UserRepository userRepository, ShopBagRepository shopBagRepository) {
        this.userRepository = userRepository;
        this.shopBagRepository = shopBagRepository;
    }


    @Override
    public List<ShopBagDTO> getAll(String username) {

        Optional<UserEntity> userBuyer = userRepository.findByUsername(username);



         return userBuyer.get().getMyShopBag()
                .stream()
                .map(ShopBagDTO::new)
                 .collect(Collectors.toList());

    }

    @Override
    public void pay(String username) {

        Optional<UserEntity> userBuyer = userRepository.findByUsername(username);

        userBuyer.get().setMyShopBag(new ArrayList<>());

        userRepository.save(userBuyer.get());

        shopBagRepository.deleteAll();

    }

    @Override
    public BigDecimal total(String username){

        BigDecimal totalBuy = BigDecimal.valueOf(0);

        Optional<UserEntity> userBuyer= userRepository.findByUsername(username);

        if(userBuyer.isPresent()) {

            List<ShopBagEntity> myBoughtCollections = userBuyer.get().getMyShopBag();

            if (!myBoughtCollections.isEmpty()) {

                for (ShopBagEntity item : myBoughtCollections) totalBuy = totalBuy.add(item.getPrice());

            }
            return totalBuy;
        }else{
            return null;
        }

    }

    public BigDecimal discountPrice(BigDecimal total, String username) {

        Optional<UserEntity> userBuyer= userRepository.findByUsername(username);

        List<ShopBagEntity> myBoughtCollections = userBuyer.get().getMyShopBag();


        if(myBoughtCollections.size()>=3){

           BigDecimal discountPrice;

            BigDecimal value=new BigDecimal(0.2);
            discountPrice=total.subtract(total.multiply(value));


           return discountPrice.setScale(2, RoundingMode.DOWN);


       }else{
            return null;
        }

    }



}
