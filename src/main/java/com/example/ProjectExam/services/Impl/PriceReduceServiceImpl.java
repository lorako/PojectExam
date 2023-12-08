package com.example.ProjectExam.services.Impl;

import com.example.ProjectExam.models.entities.HeroEntity;
import com.example.ProjectExam.services.HeroService;
import com.example.ProjectExam.services.PriceReduceService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.util.List;


@Service
public class PriceReduceServiceImpl implements PriceReduceService {

    private final HeroService heroService;

    public PriceReduceServiceImpl(HeroService heroService) {
        this.heroService = heroService;
    }

    @Scheduled(fixedRate = 7 * 24 * 60 * 60 * 1000)
    public  void reducePrices() {

        List<HeroEntity> heroesAll = heroService.getAllHeroes().stream().toList();

        LocalDateTime oneMonthAgo = LocalDateTime.now().minusMonths(1);

      if(heroesAll.size()>0){
        for(HeroEntity hero:heroesAll) {
            if (hero.getCreated() == null || hero.getCreated().isBefore(ChronoLocalDate.from(oneMonthAgo))) {

                hero.setPrice(hero.getPrice().multiply(BigDecimal.valueOf(0.9)));
                hero.setCreated(LocalDate.from(LocalDateTime.now()));
                heroService.safe(hero);
            }
        }
    }}
}


