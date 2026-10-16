# Lezione 54: DTO con record e Jackson 3

Separiamo il contratto pubblico dal dominio con ProductResponse e verifichiamo la conversione dei record con Jackson 3.

Richiede JDK 25. Il progetto include Maven Wrapper.

```bash
./mvnw test
./mvnw package
java -jar target/shop-0.0.1-SNAPSHOT.jar
```

Su Windows usa `mvnw.cmd`. Il primo build scarica le dipendenze.

Quando il log mostra `Started ShopApplication`, apri il [client HTTP nel browser](http://127.0.0.1:18084/rest-client.html?request=featured-after.http). La richiesta viene caricata ma si invia soltanto premendo **Send request**. Leggi stato, intestazioni e corpo nelle due colonne. I file `.http` in `requests/` si possono usare anche in un IDE con client HTTP abilitato.

Il client web incluso serve per provare il laboratorio in locale. Non richiede account, servizi esterni o una licenza IDE. I dati sono fittizi. Al termine arresta il server con Ctrl+C.

Questa cartella contiene il risultato finale della lezione: i quattro test devono passare. Lo starter dell’esercizio allegato alla lezione contiene invece una modifica da completare.
