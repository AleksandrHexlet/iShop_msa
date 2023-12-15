package com.sprng.users.unit;

import com.sprng.library.entity.ProductTrader;
import com.sprng.users.repository.ProductTraderRepository;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

public class TraderRepositoryTest { // это тестированние запросов в БД
    @Autowired
    TestEntityManager testEntityManager;
    @Autowired
    ProductTraderRepository productTraderRepository;

    @BeforeAll
    public static void returnString() {
        System.out.println("Один раз перед всеми тестами метод " +
                "что-то выполнит, чтобы не дублировать логику в каждом методе");
    }

    @BeforeEach
    public void returnStringBeforeEach() {
        System.out.println("Один раз перед запуском каждого теста метод " +
                "что-то выполнит, чтобы не дублировать логику в каждом методе");
    }

    @Test
    public void testProductTraderRepository() {
        ProductTrader productTraderSmith = new ProductTrader();
        productTraderSmith.setName("Smith");
//        productTraderSmith.setId(999);
        productTraderSmith.setCityStorage("Moscow");
        testEntityManager.persist(productTraderSmith);
        ProductTrader productTrader = productTraderRepository.findById(productTraderSmith.getId()).orElse(null);
        assertAll("productTraderRepositoryTest",
                () -> assertNotNull(productTrader),
                () -> assertEquals(productTrader.getName(), "Smith"),
                () -> assertEquals(productTrader.getId(), productTraderSmith.getId()),
                () -> assertEquals(productTrader.getCityStorage(), "Moscow")
//                ()-> assertEquals(productTrader.getRole(), "ROLE_TRADER")
        );
    }
}
