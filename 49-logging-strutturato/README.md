# Lezione 49: logging strutturato

JDK 25, Spring Boot 4.1.1. Maven Wrapper incluso; il primo build richiede Internet.

```bash
./mvnw test
./mvnw package
java -jar target/shop-0.0.1-SNAPSHOT.jar
```

Su Windows usa mvnw.cmd.

Esegui il Jar e seleziona checkout_completed con jq. Il formato Logstash produce un oggetto JSON per riga; i campi orderId e totalInCents sono espliciti. --logging.level.com.shop=WARN esclude l’evento INFO.
