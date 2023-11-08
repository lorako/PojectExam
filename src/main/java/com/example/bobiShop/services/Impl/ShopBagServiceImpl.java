package com.example.bobiShop.services.Impl;

import com.example.bobiShop.models.DTOs.ShopBagDTO;
import com.example.bobiShop.models.entities.ShopBagEntity;
import com.example.bobiShop.models.entities.UserEntity;
import com.example.bobiShop.repositories.ShopBagRepository;
import com.example.bobiShop.repositories.UserRepository;
import com.example.bobiShop.services.ShopBagService;
import com.example.bobiShop.session.LoggedUser;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShopBagServiceImpl implements ShopBagService {

    private final UserRepository userRepository;

    private final ShopBagRepository shopBagRepository;
    private final LoggedUser loggedUser;

    public ShopBagServiceImpl(UserRepository userRepository, ShopBagRepository shopBagRepository, LoggedUser loggedUser) {
        this.userRepository = userRepository;
        this.shopBagRepository = shopBagRepository;
        this.loggedUser = loggedUser;
    }


    @Override
    public List<ShopBagDTO> getAll() {

        UserEntity userBuyer = userRepository.findByUsername(loggedUser.getUsername());
         return userBuyer.getMyShopBag()
                .stream()
                .map(ShopBagDTO::new).collect(Collectors.toList());


    }

    @Override
    public void pay() {

        UserEntity userBuyer = userRepository.findByUsername(loggedUser.getUsername());

        userBuyer.setMyShopBag(new ArrayList<>());
        userRepository.save(userBuyer);

        shopBagRepository.deleteAll();

    }


    @Override
    public BigDecimal total(){
        BigDecimal totalBuy = BigDecimal.valueOf(0);

        UserEntity userBuyer= userRepository.findByUsername(loggedUser.getUsername());
        List<ShopBagEntity> myBoughtCollections = userBuyer.getMyShopBag();
        if(!myBoughtCollections.isEmpty()){

         for (ShopBagEntity item :myBoughtCollections) totalBuy = totalBuy.add(item.getPrice());

         }

       return totalBuy;
    }

    public BigDecimal discountPrice(BigDecimal total) {
        UserEntity userBuyer= userRepository.findByUsername(loggedUser.getUsername());
        List<ShopBagEntity> myBoughtCollections = userBuyer.getMyShopBag();


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
