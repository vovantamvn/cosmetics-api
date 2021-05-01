# Cosmetics API

## Getting started

- Config your username, password, database name 
  in file [application.yml](./src/main/resources/application.yml)

```yaml
spring:
  jpa:
    hibernate:
      ddl-auto: update
  datasource:
    url: "jdbc:postgresql://localhost:5432/cosmetics"
    username: postgres
    password: postgres

```

- Run `./mvnw spring-boot:run`