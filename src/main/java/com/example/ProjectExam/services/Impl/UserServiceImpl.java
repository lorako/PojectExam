package com.example.ProjectExam.services.Impl;

import com.example.ProjectExam.models.DTOs.LoginDTO;
import com.example.ProjectExam.models.DTOs.RegisterDTO;
import com.example.ProjectExam.models.entities.ArtistEntity;
import com.example.ProjectExam.models.entities.UserEntity;
import com.example.ProjectExam.models.enums.RoleEnum;
import com.example.ProjectExam.repositories.ArtistRepository;
import com.example.ProjectExam.repositories.UserRepository;
import com.example.ProjectExam.services.UserService;
import com.example.ProjectExam.session.LoggedUser;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    private final LoggedUser loggedUser;

    private final ArtistRepository artistRepository;

    public UserServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository, LoggedUser loggedUser, ArtistRepository artistRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.loggedUser = loggedUser;
        this.artistRepository = artistRepository;
    }

    @Override
    public boolean register(RegisterDTO registerDTO) {
        if(!registerDTO.getPassword().equals(registerDTO.getConfirmPassword())){
            return false;
        }
        boolean existByUsernameOrEmail =userRepository.existsByUsernameOrEmail(registerDTO.getUsername(), registerDTO.getEmail());

        if(existByUsernameOrEmail){
            return false;
        }
        UserEntity user=new UserEntity();
        user.setUsername(registerDTO.getUsername());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        user.setEmail(registerDTO.getEmail());
        user.setCountry(registerDTO.getCountry());
        if(registerDTO.getAge() >0 ){
            user.setAge(registerDTO.getAge());
        }
        RoleEnum role=registerDTO.getRole();
        user.setRole(role);
        if (role.toString() == "Artist"){
            ArtistEntity artist=new ArtistEntity();
            artist.setUsername(registerDTO.getUsername());
            artist.setEmail(registerDTO.getEmail());
            artistRepository.save(artist);
        }

        userRepository.save(user);

        return true;
    }

    @Override
    public boolean login(LoginDTO loginDTO) {
        String username= loginDTO.getUsername();

        UserEntity user=userRepository.findByUsername(username);
        if(user != null && passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())){
            loggedUser.setUsername(username);
            loggedUser.setLogged(true);
            return true;
        }
        return false;
    }
    @Override
    public void logout() {
        loggedUser.logout();

    }
}
