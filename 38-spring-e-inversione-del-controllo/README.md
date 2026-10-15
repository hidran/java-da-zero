# Lezione 38: Spring e l'inversione del controllo

Progetto Maven mostrato nella lezione (JDK 25, il wrapper scarica Maven al primo avvio). Test e programma:

```bash
./mvnw test
./mvnw compile && java -cp "target/classes:$(./mvnw -q dependency:build-classpath -Dmdep.outputFile=/dev/stdout)" com.shop.Main
```
