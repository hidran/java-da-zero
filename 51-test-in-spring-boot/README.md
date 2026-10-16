# Lezione 51: test in Spring Boot

JDK 25, Spring Boot 4.1.1. Maven Wrapper incluso; il primo build richiede Internet.

```bash
./mvnw test
./mvnw package
java -jar target/shop-0.0.1-SNAPSHOT.jar
```

Su Windows usa mvnw.cmd.

./mvnw test esegue sei test: tre della regola ShippingService, due della slice QuoteController e uno del contesto completo. MockMvc e l’ambiente MOCK predefinito non avviano una porta reale. La sorgente pubblicata contiene la soglia inclusiva corretta.
