# Lezione 46: creare Shop con Spring Initializr

Progetto Maven generato da Spring Initializr e mostrato nella lezione. Richiede JDK 25.
Il primo avvio del Maven Wrapper richiede Internet. Spring Boot 4.1.1, senza dipendenze web.

```bash
./mvnw test
./mvnw clean package
java -jar target/shop-0.0.1-SNAPSHOT.jar
```

Su Windows usa `mvnw.cmd` al posto di `./mvnw`.

`ShopApplication` avvia il contesto; il test generato verifica che il contesto possa caricarsi.
Il plugin Maven di Boot produce un Jar eseguibile con le dipendenze. Senza dipendenze web
non viene avviato un server HTTP e il processo termina normalmente.
