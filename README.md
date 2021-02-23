## Testcontainers dojo
Welkom bij de testcontainers dojo. Dit is een Spring boot project met geintegreerde H2 database zoals je die misschien wel kent.

#### Benodigdheden
- Java 11 - https://openjdk.java.net/projects/jdk/11/
- Docker - https://www.docker.com/products/docker-desktop
- Maven - https://maven.apache.org/

### Opdracht 1. 
- Fix de PizzaRepositoryTest.
#### Run test
```
mvn test
```

### Opdracht 2. 
- Vervang de H2 database met een postgres database

####application.yml configuratie
```
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/postgres
    username: postgres
    password: mysecretpassword
    driver-class-name: org.postgresql.Driver
  jpa:
    hibernate:
      ddl-auto: update
    database-platform: org.hibernate.dialect.PostgreSQLDialect
```

#### Run docker container
```
docker run --name some-postgres -e POSTGRES_PASSWORD=mysecretpassword -p 5432:5432 -d postgres
```

#### Remove docker container
```
docker ps
docker stop {CONTAINER ID}
docker rm {CONTAINER ID}
```

### Opdracht 3.
- Maak gebruikt van testcontainers in PizzaRepositoryTest

#### Postgres Testcontairs configuratie
```
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
```

### Opdracht 4.
- Kijk op https://www.testcontainers.org/ onder modules. 
    - Als je support ziet voor software die je op je opdracht gebruikt, probeer deze uit.
    - Of, kies een andere module en probeer deze uit.
