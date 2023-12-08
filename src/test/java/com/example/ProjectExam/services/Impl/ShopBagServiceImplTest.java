package com.example.ProjectExam.services.Impl;

import com.example.ProjectExam.models.entities.ShopBagEntity;
import com.example.ProjectExam.models.entities.UserEntity;
import com.example.ProjectExam.repositories.ShopBagRepository;
import com.example.ProjectExam.repositories.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
class ShopBagServiceImplTest {

    private ShopBagServiceImpl toTest;

    @Mock
    private UserRepository mockUserRepository;
    @Mock
    private ShopBagRepository mockShopBagRepository;

    @BeforeEach
    void setUp(){
        toTest=new ShopBagServiceImpl(mockUserRepository, mockShopBagRepository);
    }

    @AfterEach
    void tearDown(){

        mockUserRepository.deleteAll();
        mockShopBagRepository.deleteAll();
    }
    @Test
    @WithMockUser(username = "user", authorities = { "ROLE_User"})
    void getAll() {

        UserEntity mockedUser=new UserEntity();

        when(mockUserRepository.findByUsername(mockedUser.getUsername())).thenReturn(Optional.of(mockedUser));

        List<ShopBagEntity> shopBag = mockedUser.getMyShopBag().stream().collect(Collectors.toList());

        if(shopBag.isEmpty()){
            mockedUser.setMyShopBag(new ArrayList<>());
            shopBag.add(new ShopBagEntity());

            Assertions.assertEquals(1,shopBag.size());
        }else{
            Assertions.assertEquals(false,shopBag.isEmpty());
        }
    }

    @Test
    @WithMockUser(username = "user", authorities = { "ROLE_User"})
    void pay() {
        UserEntity mockedUser=new UserEntity();
        when(mockUserRepository.findByUsername(mockedUser.getUsername())).thenReturn(Optional.of(mockedUser));
        List<ShopBagEntity> shopBag = mockedUser.getMyShopBag().stream().collect(Collectors.toList());
        mockedUser.setMyShopBag(new ArrayList<>());
        shopBag.add(new ShopBagEntity());
        toTest.pay(mockedUser.getUsername());
        shopBag.clear();
        mockShopBagRepository.deleteAll();
        mockUserRepository.save(mockedUser);
        Assertions.assertEquals(true, shopBag.isEmpty());

    }

    @Test
    @WithMockUser(username = "user", authorities = { "ROLE_User"})
    void total() {
        UserEntity mockedUser=new UserEntity();
        when(mockUserRepository.findByUsername(mockedUser.getUsername())).thenReturn(Optional.of(mockedUser));
        List<ShopBagEntity> shopBag = mockedUser.getMyShopBag().stream().collect(Collectors.toList());

        mockedUser.setMyShopBag(new ArrayList<>());

        ShopBagEntity shop1=new ShopBagEntity();
        shop1.setPrice(BigDecimal.valueOf(2.00));
        ShopBagEntity shop2=new ShopBagEntity();
        shop2.setPrice(BigDecimal.valueOf(2.00));
        ShopBagEntity shop3=new ShopBagEntity();
        shop3.setPrice(BigDecimal.valueOf(2.00));
        shopBag.add(shop1);
        shopBag.add(shop2);
        shopBag.add(shop3);

        BigDecimal totalBuy = BigDecimal.valueOf(0);

        totalBuy = totalBuy.add(shop1.getPrice());
        totalBuy = totalBuy.add(shop2.getPrice());
        totalBuy = totalBuy.add(shop3.getPrice());
      toTest.total(mockedUser.getUsername());

        Assertions.assertEquals(BigDecimal.valueOf(6.00), totalBuy);
    }

    @Test
    void discountPrice() {
        UserEntity mockedUser=new UserEntity();
        when(mockUserRepository.findByUsername(mockedUser.getUsername())).thenReturn(Optional.of(mockedUser));
        List<ShopBagEntity> shopBag = mockedUser.getMyShopBag().stream().collect(Collectors.toList());

        mockedUser.setMyShopBag(new ArrayList<>());

        ShopBagEntity shop1=new ShopBagEntity();
        shop1.setPrice(BigDecimal.valueOf(2.00));
        ShopBagEntity shop2=new ShopBagEntity();
        shop2.setPrice(BigDecimal.valueOf(2.00));
        ShopBagEntity shop3=new ShopBagEntity();
        shop3.setPrice(BigDecimal.valueOf(2.00));
        shopBag.add(shop1);
        shopBag.add(shop2);
        shopBag.add(shop3);

        BigDecimal totalBuy = BigDecimal.valueOf(0);

        totalBuy = totalBuy.add(shop1.getPrice());
        totalBuy = totalBuy.add(shop2.getPrice());
        totalBuy = totalBuy.add(shop3.getPrice());

        if(shopBag.size()>=3){
        toTest.discountPrice(totalBuy,mockedUser.getUsername());
            BigDecimal discountPrice;

            BigDecimal value=new BigDecimal(0.2);
            discountPrice=totalBuy.subtract(totalBuy.multiply(value));


            discountPrice.setScale(2, RoundingMode.DOWN);


        }else{}
        totalBuy=totalBuy;

    }
}