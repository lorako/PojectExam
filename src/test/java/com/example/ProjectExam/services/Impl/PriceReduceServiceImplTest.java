package com.example.ProjectExam.services.Impl;

import com.example.ProjectExam.models.entities.HeroEntity;
import com.example.ProjectExam.services.HeroService;
import com.example.ProjectExam.services.PriceReduceService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;

@SpringBootTest
@AutoConfigureMockMvc
class PriceReduceServiceImplTest {

    private PriceReduceService mockPriceService;
    @Mock
    private HeroService toTest;
    @Mock
    private MockMvc mockMvc;

    @BeforeEach
    void setUp(){
       mockPriceService=new PriceReduceServiceImpl(toTest);
    }

    @Test
        public void testHeroEntityPriceAndCreated() {
            List<HeroEntity> heroesAll = new ArrayList<>();



            HeroEntity mockhero1 = new HeroEntity();
            mockhero1.setPrice(BigDecimal.valueOf(100));
            mockhero1.setHeroName("Lora");
            mockhero1.setCreated(LocalDate.now().minusMonths(1));
            heroesAll.add(mockhero1);
            Assertions.assertEquals(BigDecimal.valueOf(100), mockhero1.getPrice());
            Assertions.assertEquals("Lora", mockhero1.getHeroName());
            Assertions.assertEquals(LocalDate.now().minusMonths(1), mockhero1.getCreated());



            if (heroesAll.size() > 0) {
                ChronoLocalDate oneMonthAgo = LocalDate.now().minusMonths(1);

                    MultipartFile mockedI=mock(MultipartFile.class);
                    if (mockhero1.getCreated() == null || mockhero1.getCreated().isBefore(oneMonthAgo)) {

                        mockhero1.setPrice(BigDecimal.valueOf(90));
                        mockhero1.setCreated(LocalDate.from(LocalDateTime.now()));

                        mockhero1.setHeroName("Lora");
                        toTest.safe(mockhero1);
                        Assertions.assertEquals(BigDecimal.valueOf(90), mockhero1.getPrice());
                        Assertions.assertEquals("Lora", mockhero1.getHeroName());
                        Assertions.assertEquals(LocalDate.now(), mockhero1.getCreated());
                    }

                }else{
                return;
            }

            }


}

