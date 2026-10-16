# Lezione 47: starter e autoconfigurazione

JDK 25, Spring Boot 4.1.1. Il Maven Wrapper originale è incluso; il primo build richiede Internet.

```bash
./mvnw test
./mvnw dependency:tree
./mvnw package
java -jar target/shop-0.0.1-SNAPSHOT.jar --server.port=18080 --debug
```

Su Windows usa `mvnw.cmd`. La radice HTTP risponde 404 perché non è stato aggiunto un controller.
Arresta con Ctrl+C. Per avviare lo stesso Jar senza server:

```bash
java -jar target/shop-0.0.1-SNAPSHOT.jar --spring.main.web-application-type=none
```

Lo starter web MVC porta Spring MVC e Tomcat; il parent gestisce le versioni.
Il rapporto `--debug` descrive le condizioni che attivano l'autoconfigurazione.
La classe principale non cambia rispetto al progetto iniziale. Il test generato verifica il caricamento del contesto.
