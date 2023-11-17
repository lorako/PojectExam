package com.example.ProjectExam.services.Impl;

import com.example.ProjectExam.models.DTOs.AddWeaponDTO;
import com.example.ProjectExam.models.DTOs.WeaponViewDTO;
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

    public WeaponServiceImpl(WeaponRepository weaponRepository, ShopBagRepository shopBagRepository, UserRepository userRepository, ArtistRepository artistRepository, ImageCloudService imageCloudService) {
        this.weaponRepository = weaponRepository;
        this.shopBagRepository = shopBagRepository;
        this.userRepository = userRepository;
        this.artistRepository = artistRepository;
        this.imageCloudService = imageCloudService;
    }



    @Override
    public void addWeapon(AddWeaponDTO addWeaponDTO, String username, MultipartFile imageFile) {

        Optional<WeaponEntity> weaponN = weaponRepository.findByWeaponName(addWeaponDTO.getWeaponName());

        if(weaponN.isPresent()){
            return;
        }else{
        Optional<ArtistEntity> artist=artistRepository.findByUsername(username);

        WeaponEntity weapon=new WeaponEntity();
        weapon.setWeaponName(addWeaponDTO.getWeaponName());
        weapon.setPrice(addWeaponDTO.getPrice());
        weapon.setCreator(artist.get());

        String pictureUrl=imageCloudService.saveImage(imageFile);
        weapon.setImgUrl(pictureUrl);


        weaponRepository.save(weapon);
    }}

    @Override
    public List<WeaponViewDTO> getAll() {
        return weaponRepository.findAll()
                .stream()
                .map(WeaponViewDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public void buyWeapon(Long id, String username) {



        Optional<UserEntity> user= userRepository.findByUsername(username);


        List<ShopBagEntity> myBoughtCollection = user.get().getMyShopBag();

        WeaponEntity weapon = weaponRepository.findById(id).orElse(null);



        if( weapon != null) {

            ShopBagEntity item = new ShopBagEntity();
            item.setImgUrl(weapon.getImgUrl());
            item.setItemName(weapon.getWeaponName());
            item.setPrice(weapon.getPrice());
            item.setBuyer(user.get());
            shopBagRepository.save(item);

            myBoughtCollection.add(item);

            userRepository.save(user.get());

            Long wId = weapon.getId();


            weaponRepository.deleteById(wId);

        }

    }
}
