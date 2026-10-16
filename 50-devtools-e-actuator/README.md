# Lezione 50: DevTools e primo sguardo ad Actuator

JDK 25, Spring Boot 4.1.1. Maven Wrapper incluso; il primo build richiede Internet.

```bash
./mvnw test
./mvnw package
java -jar target/shop-0.0.1-SNAPSHOT.jar
```

Su Windows usa mvnw.cmd.

Avvia ./mvnw spring-boot:run e interroga health e info su http://127.0.0.1:18081/actuator. env non è esposto. Una modifica compilata con ./mvnw compile provoca il riavvio DevTools. Arresta con Ctrl+C prima di package. Il Jar eseguibile standard esclude DevTools.
