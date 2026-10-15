# Lezione 43: configurazione esterna e profili

Progetto Maven mostrato nella lezione (JDK 25, il wrapper scarica Maven al primo avvio). Test e programma:

```bash
./mvnw test
./mvnw compile && java -cp "target/classes:$(./mvnw -q dependency:build-classpath -Dmdep.outputFile=/dev/stdout)" com.shop.Main
```
