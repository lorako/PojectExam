package com.example.ProjectExam.services.Impl;

import com.example.ProjectExam.models.DTOs.AddHeroDTO;
import com.example.ProjectExam.models.DTOs.HeroRestDTO;
import com.example.ProjectExam.models.DTOs.HeroViewDTO;

import com.example.ProjectExam.models.entities.*;
import com.example.ProjectExam.repositories.ArtistRepository;
import com.example.ProjectExam.repositories.HeroRepository;
import com.example.ProjectExam.repositories.ShopBagRepository;
import com.example.ProjectExam.repositories.UserRepository;
import com.example.ProjectExam.services.HeroService;
import com.example.ProjectExam.services.ImageCloudService;
import com.example.ProjectExam.session.LoggedUser;
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
        List<HeroEntity> artistList=artist.getHeroesList();
        if(artistList.isEmpty()){
            artistList=new ArrayList<>();
        }

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
        artistList.add(hero);
        artist.setHeroesList(artistList);

        this.heroRepository.save(hero);
        this.artistRepository.save(artist);
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
        public List<HeroRestDTO> findAllHeroesForRest(){
        return heroRepository.getAll()
                .stream()
                .map(this::map).toList();
        }

        private HeroRestDTO map(HeroEntity hero){
        HeroRestDTO heroRestDTO=new HeroRestDTO();
        heroRestDTO.setId(hero.getId());
        heroRestDTO.setCreated(hero.getCreated());
        heroRestDTO.setCreator(hero.getCreator().getUsername());
        heroRestDTO.setDescription(hero.getDescription());
        heroRestDTO.setHeroName(hero.getHeroName());
        heroRestDTO.setPower(hero.getPower());
        heroRestDTO.setPrice(hero.getPrice());
        heroRestDTO.setLikes(hero.getLikes());
        return heroRestDTO;

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

