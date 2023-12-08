package com.example.ProjectExam.services.Impl;

import com.example.ProjectExam.Exceptions.HeroNotFoundException;
import com.example.ProjectExam.Exceptions.ObjectNotFoundException;
import com.example.ProjectExam.Exceptions.UserNotFoundException;
import com.example.ProjectExam.models.DTOs.BindingModel.AddHeroDTO;
import com.example.ProjectExam.models.DTOs.RestDTO.AddHeroRestDTO;
import com.example.ProjectExam.models.DTOs.RestDTO.HeroRestDTO;
import com.example.ProjectExam.models.DTOs.View.HeroViewDTO;

import com.example.ProjectExam.models.entities.*;
import com.example.ProjectExam.models.enums.RoleEnum;
import com.example.ProjectExam.repositories.ArtistRepository;
import com.example.ProjectExam.repositories.HeroRepository;
import com.example.ProjectExam.repositories.ShopBagRepository;
import com.example.ProjectExam.repositories.UserRepository;
import com.example.ProjectExam.services.HeroService;
import com.example.ProjectExam.services.ImageCloudService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

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

        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("This user does not exist!"));

        HeroEntity hero = new HeroEntity();


         String newHeroName=addHeroDTO.getHeroName();
         Optional<HeroEntity> oneHero = heroRepository.findByHeroName(newHeroName);

         if(oneHero.isPresent()){
            throw new ObjectNotFoundException("HeroName already exists!");
         }

        ArtistEntity artist = artistRepository.findByUsername(user.getUsername())
                .orElseThrow(()->new ObjectNotFoundException("Not found!"));
        hero.setHeroName(addHeroDTO.getHeroName());
        hero.setDescription(addHeroDTO.getDescription());
        hero.setPrice(addHeroDTO.getPrice());
        hero.setPower(addHeroDTO.getPower());
        hero.setCreator(artist);
        hero.setCreated(addHeroDTO.getCreated());
        String pictureUrl=imageCloudService.saveImage(imageFile);
        hero.setPhotoUrl(pictureUrl);
        this.heroRepository.save(hero);
        int totalArt = artist.getTotalArt();
        totalArt+=1;
        artist.setTotalArt(totalArt);
        artistRepository.save(artist);}
     @Override
    public List<HeroEntity> getAllHeroes(){
       return  heroRepository.getAll();
    }
    @Override
    public Page<HeroViewDTO> getAllHeroes(Pageable pageable) {

        return this.heroRepository.findAll(pageable)

                .map(HeroViewDTO::new);
    }

    @Override
    public void likeHeroWithId(Long id, String username) {
       HeroEntity hero = heroRepository.findById(id).orElseThrow(() -> new HeroNotFoundException("Hero not found!"));
        ArtistEntity creator =hero.getCreator();
        ArtistEntity creatorHero= artistRepository.findByUsername(creator.getUsername())
                .orElseThrow(() -> new ObjectNotFoundException("Not found artist"));
        if(!creator.getUsername().equals(username)){
          int likes=hero.getLikes();
          int allLikes = creatorHero.getTotalLikes();
          hero.setLikes(likes+=1);
          creatorHero.setTotalLikes(allLikes+=1);
          artistRepository.save(creatorHero);
          heroRepository.save(hero);
      }}
    @Override
    public void buyHero(Long id, String username) {
          Optional<UserEntity> user= userRepository.findByUsername(username);

            List<ShopBagEntity> myBoughtCollection = user.get().getMyShopBag();
            HeroEntity hero = heroRepository.findById(id).orElseThrow(()->new ObjectNotFoundException("Hero is not found!"));
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
                heroRepository.deleteById(heroId);}}
    @Override
    public List<HeroRestDTO> findAllHeroesForRest(){
    return heroRepository.getAll()
                .stream()
                .map(this::map).toList();}
    @Override
    public Optional<HeroRestDTO> findHeroById(Long id) {
           Optional<HeroEntity> byId = Optional.ofNullable(heroRepository.findById(id)
                   .orElseThrow(() -> new HeroNotFoundException("Hero not found")));
            return  byId.map(this::map);}
    @Override
    public void deleteById(Long id) {

        heroRepository.deleteById(id);
    }
    @Override
    public boolean addRestHero(AddHeroRestDTO addHeroRestDTO) {

        ArtistEntity creator = addHeroRestDTO.getCreator();
        Optional<UserEntity> userCreator= userRepository.findByUsername(creator.getUsername());

      //  Optional<ArtistEntity> artistOptional = artistRepository.findByUsername(creator.getUsername());


        if(userCreator.isPresent() && userCreator.get().getRole()== RoleEnum.Artist) {

            HeroEntity heroRest = new HeroEntity();
         //   heroRest.setId(addHeroRestDTO.getId());
            heroRest.setCreator(creator);
            heroRest.setCreated(addHeroRestDTO.getCreated());
            heroRest.setDescription(addHeroRestDTO.getDescription());
            heroRest.setHeroName(addHeroRestDTO.getHeroName());
            heroRest.setLikes(addHeroRestDTO.getLikes());
            heroRest.setPower(addHeroRestDTO.getPower());
            heroRest.setPrice(addHeroRestDTO.getPrice());
            heroRest.setPhotoUrl(addHeroRestDTO.getImgUrl().getOriginalFilename());

            heroRepository.save(heroRest);

            creator.setTotalArt(creator.getTotalArt()+1);
            artistRepository.save(creator);
        }
        return true ;
    }
    @Override
    public void safe(HeroEntity hero) {
        heroRepository.save(hero);
    }
    @Override
    public long findById(Long id) {
       heroRepository.findById(id);
       return id;}
    @Override
    public void deleteByIdRest(Long id) {
        heroRepository.deleteById(id);
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
        return heroRestDTO;}
    @Override
    public String getTheMost() {

        List<HeroEntity> heroes = heroRepository.getAll().stream()
                .max(Comparator.comparing(HeroEntity::getLikes))
                .stream()
                .toList();
      if(heroes.size()>0){
         Optional<HeroEntity> hero= heroes.stream().findFirst();
        String heroMaxName=hero.get().getHeroName();
        return heroMaxName;
    }else{
          return "None";}}}