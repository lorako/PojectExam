package com.example.ProjectExam.services.Impl;

import com.example.ProjectExam.Exceptions.ObjectNotFoundException;
import com.example.ProjectExam.Exceptions.UserNotFoundException;
import com.example.ProjectExam.models.DTOs.BindingModel.AddWeaponDTO;
import com.example.ProjectExam.models.DTOs.View.WeaponViewDTO;
import com.example.ProjectExam.models.entities.*;
import com.example.ProjectExam.repositories.ArtistRepository;
import com.example.ProjectExam.repositories.ShopBagRepository;
import com.example.ProjectExam.repositories.UserRepository;
import com.example.ProjectExam.repositories.WeaponRepository;
import com.example.ProjectExam.services.ImageCloudService;
import com.example.ProjectExam.services.WeaponService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WeaponServiceImpl implements WeaponService {

    private final WeaponRepository weaponRepository;
    private final ShopBagRepository shopBagRepository;
    private final UserRepository userRepository;
    private final ArtistRepository artistRepository;
    private final ImageCloudService imageCloudService;

    public WeaponServiceImpl(WeaponRepository weaponRepository, ShopBagRepository shopBagRepository,
                             UserRepository userRepository, ArtistRepository artistRepository, ImageCloudService imageCloudService) {
        this.weaponRepository = weaponRepository;
        this.shopBagRepository = shopBagRepository;
        this.userRepository = userRepository;
        this.artistRepository = artistRepository;
        this.imageCloudService = imageCloudService;
    }

    @Override
    public void addWeapon(AddWeaponDTO addWeaponDTO,
                          String username, MultipartFile imageFile) {
        Optional<WeaponEntity> weaponN = weaponRepository.findByWeaponName(addWeaponDTO.getWeaponName());

        ArtistEntity artist=artistRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("This user does not exist!"));
       if(weaponN.isEmpty()){
        WeaponEntity weapon=new WeaponEntity();
        weapon.setWeaponName(addWeaponDTO.getWeaponName());
        weapon.setPrice(addWeaponDTO.getPrice());
        weapon.setCreator(artist);
        String pictureUrl=imageCloudService.saveImage(imageFile);
        weapon.setImgUrl(pictureUrl);
        weaponRepository.save(weapon);
           int totalArt = artist.getTotalArt();
           totalArt+=1;
           artist.setTotalArt(totalArt);
           artistRepository.save(artist);
     }else{return;}}
    @Override
    public List<WeaponViewDTO> getAll()
    {
        return weaponRepository.findAll()
                .stream()
                .map(WeaponViewDTO::new)
                .collect(Collectors.toList());}
    @Override
    public void buyWeapon(Long id, String username) {
        UserEntity user= userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("This user does not exist!"));
        List<ShopBagEntity> myBoughtCollection = user.getMyShopBag();
        WeaponEntity weapon = weaponRepository.findById(id)
                .orElseThrow(()->new ObjectNotFoundException("We can not find this object"));

            ShopBagEntity item = new ShopBagEntity();
            item.setImgUrl(weapon.getImgUrl());
            item.setItemName(weapon.getWeaponName());
            item.setPrice(weapon.getPrice());
            item.setBuyer(user);
            shopBagRepository.save(item);
            myBoughtCollection.add(item);
            userRepository.save(user);
            Long wId = weapon.getId();
            weaponRepository.deleteById(wId);}}