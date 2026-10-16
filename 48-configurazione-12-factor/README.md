# Lezione 48: configurazione esterna e profili

JDK 25, Spring Boot 4.1.1. Maven Wrapper incluso; il primo build richiede Internet.

```bash
./mvnw test
./mvnw package
java -jar target/shop-0.0.1-SNAPSHOT.jar
```

Su Windows usa mvnw.cmd.

Il programma senza server stampa valuta e spedizione. Confronta avvio normale, --spring.profiles.active=demo, SHOP_SHIPPING=190 e --shop.shipping-in-cents=0. Il Jar non cambia fra gli avvii.
