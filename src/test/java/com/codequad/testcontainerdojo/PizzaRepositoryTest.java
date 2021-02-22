package com.codequad.testcontainerdojo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PizzaRepositoryTest {

    @Autowired
    PizzaRepository pizzaRepository;

    @Test
    void testInsertPizza() {
        Pizza margherita = Pizza.builder().name("Margherita").price(800).build();
        Pizza caprese = Pizza.builder().name("Caprese").price(900).build();
        Pizza hawaii = Pizza.builder().name("Hawaii").price(900).build();

        pizzaRepository.save(margherita);
        pizzaRepository.save(caprese);
        pizzaRepository.save(hawaii);

        assertEquals(pizzaRepository.findAll().size(), 3);
    }
}