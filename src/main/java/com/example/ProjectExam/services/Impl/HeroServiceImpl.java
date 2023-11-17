package com.example.ProjectExam.services.Impl;

import com.example.ProjectExam.models.DTOs.AddHeroDTO;
import com.example.ProjectExam.models.DTOs.RestDTO.AddHeroRestDTO;
import com.example.ProjectExam.models.DTOs.RestDTO.HeroRestDTO;
import com.example.ProjectExam.models.DTOs.HeroViewDTO;

import com.example.ProjectExam.models.entities.*;
import com.example.ProjectExam.repositories.ArtistRepository;
import com.example.ProjectExam.repositories.HeroRepository;
import com.example.ProjectExam.repositories.ShopBagRepository;
import com.example.ProjectExam.repositories.UserRepository;
import com.example.ProjectExam.services.HeroService;
import com.example.ProjectExam.services.ImageCloudService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class HeroServiceImpl implements HeroService {

    private final UserRepository userRepository;
    private final ShopBagRepository shopBagRepository;
    private final ArtistRepository artistRepository;
    private final HeroRepository heroRepository;

    private final ImageCloudService imageCloudService;

    public HeroServiceImpl(UserRepository userRepository, ShopBagRepository shopBagRepository,
                           ArtistRepository artistRepository,HeroRepository heroRepository, ImageCloudService imageCloudService) {
        this.userRepository = userRepository;
        this.shopBagRepository = shopBagRepository;
        this.artistRepository = artistRepository;

        this.heroRepository = heroRepository;
        this.imageCloudService = imageCloudService;
    }



    @Override
    public void addHero(AddHeroDTO addHeroDTO,
                        MultipartFile imageFile, String username) {

        Optional<UserEntity> user = userRepository.findByUsername(username);

        HeroEntity hero = new HeroEntity();


         String newHeroName=addHeroDTO.getHeroName();
         Optional<HeroEntity> oneHero = heroRepository.findByHeroName(newHeroName);
         if(oneHero.isPresent()){
            return;
         }

        Optional<ArtistEntity> artist = artistRepository.findByUsername(user.get().getUsername());
        hero.setHeroName(addHeroDTO.getHeroName());
        hero.setDescription(addHeroDTO.getDescription());
        hero.setPrice(addHeroDTO.getPrice());
        hero.setPower(addHeroDTO.getPower());
        hero.setCreator(artist.get());
        hero.setCreated(addHeroDTO.getCreated());

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
    public void likeHeroWithId(Long id, String username) {


        String user=userRepository.findByUsername(username).get().getUsername();

        HeroEntity hero=heroRepository.findById(id).orElse(null);
        String usernameNoCreator = hero.getCreator().getUsername();

        String  creatorHero=hero.getCreator().getUsername();

      if(!usernameNoCreator.equals(user)){
          int likes=hero.getLikes();

          hero.setLikes(likes+=1);

          heroRepository.save(hero);
      }


    }

    @Override
    public void buyHero(Long id, String username) {



          Optional<UserEntity> user= userRepository.findByUsername(username);


            List<ShopBagEntity> myBoughtCollection = user.get().getMyShopBag();

            HeroEntity hero = heroRepository.findById(id).orElse(null);



            if( hero != null) {

                ShopBagEntity item = new ShopBagEntity();
                item.setImgUrl(hero.getPhotoUrl());
                item.setItemName(hero.getHeroName());
                item.setPrice(hero.getPrice());
                item.setBuyer(user.get());
                shopBagRepository.save(item);

                myBoughtCollection.add(item);

                userRepository.save(user.get());

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

        @Override
        public Optional<HeroRestDTO> findHeroById(Long id) {
            Optional<HeroEntity> byId = heroRepository.findById(id);
            return  byId.map(this::map);

       }

    @Override
    public void deleteById(Long id) {
        heroRepository.deleteById(id);
    }

    @Override
    public void addRestHero(AddHeroRestDTO addHeroRestDTO) {

       HeroEntity heroRest=new HeroEntity();
        heroRest.setId(addHeroRestDTO.getId());
        heroRest.setCreator(addHeroRestDTO.getCreator());
        heroRest.setCreated(addHeroRestDTO.getCreated());
        heroRest.setDescription(addHeroRestDTO.getDescription());
        heroRest.setHeroName(addHeroRestDTO.getHeroName());
        heroRest.setLikes(addHeroRestDTO.getLikes());
        heroRest.setPower(addHeroRestDTO.getPower());
        heroRest.setPrice(addHeroRestDTO.getPrice());
        heroRest.setPhotoUrl(addHeroRestDTO.getImgUrl().getOriginalFilename());


        heroRepository.save(heroRest);


    }


    private HeroRestDTO map(HeroEntity hero){
        HeroRestDTO heroRestDTO=new HeroRestDTO();
        heroRestDTO.setId(hero.getId());
        heroRestDTO.setCreated(hero.getCreated());
        heroRestDTO.setCreator(hero.getCreator().getUsername());
        heroRestDTO.setDescription(hero.getDescription());
        heroRestDTO.setHeroName(hero.getHeroName());
        heroRestDTO.setPower(hero.getPower());
        heroRestDTO.setImgUrl(hero.getPhotoUrl());
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

