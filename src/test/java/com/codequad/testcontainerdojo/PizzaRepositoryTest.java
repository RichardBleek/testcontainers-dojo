package com.codequad.testcontainerdojo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PizzaRepositoryTest {

    private static final PostgreSQLContainer<?> POSTGRESQL_CONTAINER = new PostgreSQLContainer<>(
            DockerImageName.parse("postgres").withTag("alpine"))
            .withDatabaseName("dojo")
            .withUsername("postgres")
            .withPassword("mysecretpassword")
            .withReuse(false);

    static {
        POSTGRESQL_CONTAINER.start();
    }

    @DynamicPropertySource
    static void containerProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRESQL_CONTAINER::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRESQL_CONTAINER::getUsername);
        registry.add("spring.datasource.password", POSTGRESQL_CONTAINER::getPassword);
    }

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