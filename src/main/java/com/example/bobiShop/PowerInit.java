package com.example.bobiShop;

import com.example.bobiShop.models.entities.PowerEntity;
import com.example.bobiShop.models.enums.PowerEnum;
import com.example.bobiShop.repositories.PowerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class PowerInit implements CommandLineRunner {

    private final PowerRepository powerRepository;

    public PowerInit(PowerRepository powerRepository) {
        this.powerRepository = powerRepository;
    }


    @Override
    public void run(String... args) throws Exception {

        List<PowerEntity> powers=new ArrayList<>();

        if(powerRepository.count() <=0){
            Arrays.stream(PowerEnum.values())
                    .forEach(name -> {
                        PowerEntity power=new PowerEntity();
                        power.setName(name);
                        powers.add(power);
                    });
           powerRepository.saveAll(powers);
        }
    }
}

