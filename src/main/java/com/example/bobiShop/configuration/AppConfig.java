package com.example.bobiShop.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysql.cj.xdevapi.JsonArray;
import org.apache.tomcat.util.json.JSONParser;
import org.cloudinary.json.JSONArray;
import org.cloudinary.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.mysql.cj.xdevapi.Type.JSON;

@Configuration
public class AppConfig {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ModelMapper modelMapper(){
        ModelMapper modelMapper=new ModelMapper();
        modelMapper.addConverter(mappingContext ->
                        LocalDateTime.parse(mappingContext.getSource(),
                                DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                ,String.class,LocalDateTime.class);
        return modelMapper;
    }

}
