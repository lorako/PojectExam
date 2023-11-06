package com.example.bobiShop.services;

import com.example.bobiShop.models.DTOs.AddHeroDTO;
import com.example.bobiShop.models.DTOs.HeroViewDTO;
import com.example.bobiShop.models.DTOs.Item;
import com.example.bobiShop.models.entities.ArtistEntity;
import com.example.bobiShop.models.entities.HeroEntity;
import com.example.bobiShop.models.entities.ImageEntity;
import com.example.bobiShop.models.entities.UserEntity;
import com.example.bobiShop.repositories.ArtistRepository;
import com.example.bobiShop.repositories.HeroRepository;
import com.example.bobiShop.repositories.UserRepository;
import com.example.bobiShop.session.LoggedUser;
import org.hibernate.cache.spi.support.AbstractReadWriteAccess;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class HeroServiceImpl implements HeroService {

    private final UserRepository userRepository;
    private final ArtistRepository artistRepository;
    private final LoggedUser loggedUser;
    private final HeroRepository heroRepository;

    private final ImageCloudService imageCloudService;

    public HeroServiceImpl(UserRepository userRepository, ArtistRepository artistRepository, LoggedUser loggedUser, HeroRepository heroRepository, ImageCloudService imageCloudService) {
        this.userRepository = userRepository;
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
        hero.setHeroName(addHeroDTO.getHeroName());
        hero.setDescription(addHeroDTO.getDescription());
        hero.setPrice(addHeroDTO.getPrice());
        hero.setPower(addHeroDTO.getPower());
        hero.setCreator(artist);

        String pictureUrl=imageCloudService.saveImage(imageFile);

        ImageEntity image=new ImageEntity();

        image.setUrl(pictureUrl);
        image.setTitle(imageFile.getOriginalFilename());
        image.setHero(hero);


        hero.setImgUrls(Collections.singleton(image));


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

        Optional<UserEntity> user= Optional.ofNullable(userRepository.findByUsername(loggedUserName));

        if(user.isPresent()){
        List<Item> myBoughtCollection = user.get().getMyBoughtCollection();

        HeroEntity hero = heroRepository.findById(id).orElse(null);


            heroRepository.delete(hero);

            Item item=new Item(HeroEntity.class);
            item.setImgUrls(hero.getImgUrls());
            item.setName(hero.getHeroName());
            item.setPrice(hero.getPrice());

            myBoughtCollection.add(item);

        }
    }

    @Override
    public String getTheMost() {


        List<HeroEntity> heroes = heroRepository.findAll().stream().max(Comparator.comparing(HeroEntity::getLikes)).stream().toList();

      if(heroes.size()>0){
         Optional<HeroEntity> hero= heroes.stream().findFirst();

        String heroMaxName=hero.get().getHeroName();
        return heroMaxName;
    }else{
          return "None";
      }

    }


}

