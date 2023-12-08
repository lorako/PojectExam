package com.example.ProjectExam.services.Impl;


import com.example.ProjectExam.Exceptions.UserNotFoundException;
import com.example.ProjectExam.models.DTOs.BindingModel.RegisterDTO;
import com.example.ProjectExam.models.entities.ArtistEntity;
import com.example.ProjectExam.models.entities.UserEntity;;
import com.example.ProjectExam.models.enums.RoleEnum;
import com.example.ProjectExam.repositories.ArtistRepository;
import com.example.ProjectExam.repositories.UserRepository;
import com.example.ProjectExam.services.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.example.ProjectExam.models.enums.RoleEnum.*;

@Service
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final ArtistRepository artistRepository;

    public UserServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository,
                           ArtistRepository artistRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.artistRepository = artistRepository;
    }

    @Override
    public boolean register(RegisterDTO registerDTO) {

        if(!registerDTO.getPassword().equals(registerDTO.getConfirmPassword())){
            return false;
        }
        boolean existByUsernameOrEmail =userRepository.existsByUsernameOrEmail(registerDTO.getUsername(),
                registerDTO.getEmail());

        if(existByUsernameOrEmail){return false;}
        UserEntity user=new UserEntity();
        user.setUsername(registerDTO.getUsername());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        user.setEmail(registerDTO.getEmail());
        user.setCountry(registerDTO.getCountry());
        if(registerDTO.getAge() >0 ) {
            user.setAge(registerDTO.getAge());}
        String  rolenew= registerDTO.getRole();
        RoleEnum role = switch (rolenew) {
            case "Admin" -> Admin;
            case "Artist" -> Artist;
            case "User"-> User;
            default -> throw new IllegalStateException("Unexpected value: " + rolenew);};
        user.setRole(role);
        if ("Artist" == role.name()){
            ArtistEntity artist=new ArtistEntity();
            artist.setUsername(registerDTO.getUsername());
            artist.setEmail(registerDTO.getEmail());
            artistRepository.save(artist);}
        userRepository.save(user);
        return true;}
    @Override
    public Optional<UserEntity> findByUsername(String username) {
        return Optional.ofNullable(userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("This user does not exist")));}}