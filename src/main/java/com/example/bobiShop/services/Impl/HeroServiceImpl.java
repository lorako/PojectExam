package com.example.bobiShop.services.Impl;

import com.example.bobiShop.models.DTOs.AddHeroDTO;
import com.example.bobiShop.models.DTOs.HeroViewDTO;

import com.example.bobiShop.models.entities.*;
import com.example.bobiShop.repositories.ArtistRepository;
import com.example.bobiShop.repositories.HeroRepository;
import com.example.bobiShop.repositories.ShopBagRepository;
import com.example.bobiShop.repositories.UserRepository;
import com.example.bobiShop.services.HeroService;
import com.example.bobiShop.services.ImageCloudService;
import com.example.bobiShop.session.LoggedUser;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class HeroServiceImpl implements HeroService {

    private final UserRepository userRepository;
    private final ShopBagRepository shopBagRepository;
    private final ArtistRepository artistRepository;
    private final LoggedUser loggedUser;
    private final HeroRepository heroRepository;

    private final ImageCloudService imageCloudService;

    public HeroServiceImpl(UserRepository userRepository, ShopBagRepository shopBagRepository, ArtistRepository artistRepository, LoggedUser loggedUser, HeroRepository heroRepository, ImageCloudService imageCloudService) {
        this.userRepository = userRepository;
        this.shopBagRepository = shopBagRepository;
        this.artistRepository = artistRepository;
        this.loggedUser = loggedUser;
        this.heroRepository = heroRepository;
        this.imageCloudService = imageCloudService;
    }



    @Override
    public void addHero(AddHeroDTO addHeroDTO,
                        MultipartFile imageFile) {

        UserEntity user = userRepository.findByUsername(loggedUser.getUsername());
        ArtistEntity artist = artistRepository.findByUsername(user.getUsername());

        HeroEntity hero = new HeroEntity();
        hero.setCreated(addHeroDTO.getCreated());

         String newHeroName=addHeroDTO.getHeroName();
        Optional<HeroEntity> oneHero = heroRepository.findByHeroName(newHeroName);
        if(oneHero.isPresent()){
            return;
        }
        hero.setHeroName(addHeroDTO.getHeroName());
        hero.setDescription(addHeroDTO.getDescription());
        hero.setPrice(addHeroDTO.getPrice());
        hero.setPower(addHeroDTO.getPower());
        hero.setCreator(artist);

        String pictureUrl=imageCloudService.saveImage(imageFile);
        hero.setPhotoUrl(pictureUrl);

        this.heroRepository.save(hero);

        }



    @Override
    public List<HeroViewDTO> getAllHeroes() {

        return  this.heroRepository.findAll().stream()
                .map(HeroViewDTO::new)
                .collect(Collectors.toList());

    }

    @Override
    public void likeHeroWithId(Long id, String loggedUserName) {


        String user=userRepository.findByUsername(loggedUserName).getUsername();

        HeroEntity hero=heroRepository.findById(id).orElse(null);
        final String username = hero.getCreator().getUsername();

        String  creatorHero=hero.getCreator().getUsername();

      if(!username.equals(user)){
          int likes=hero.getLikes();

          hero.setLikes(likes+=1);

          heroRepository.save(hero);
      }


    }

    @Override
    public void buyHero(Long id) {

           String loggedUserName= loggedUser.getUsername();

          UserEntity user= userRepository.findByUsername(loggedUserName);


            List<ShopBagEntity> myBoughtCollection = user.getMyShopBag();

            HeroEntity hero = heroRepository.findById(id).orElse(null);


            if( hero != null) {

                ShopBagEntity item = new ShopBagEntity();
                item.setImgUrl(hero.getPhotoUrl());
                item.setItemName(hero.getHeroName());
                item.setPrice(hero.getPrice());
                item.setBuyer(user);
                shopBagRepository.save(item);

                myBoughtCollection.add(item);

                userRepository.save(user);

                Long heroId = hero.getId();

                heroRepository.deleteById(heroId);

            }

        }


    @Override
    public String getTheMost() {


        List<HeroEntity> heroes = heroRepository.getAll().stream().max(Comparator.comparing(HeroEntity::getLikes)).stream().toList();

      if(heroes.size()>0){
         Optional<HeroEntity> hero= heroes.stream().findFirst();

        String heroMaxName=hero.get().getHeroName();
        return heroMaxName;
    }else{
          return "None";
      }

    }


}

