# Lezione 53: progettare risorse e risposte REST

Progettiamo indirizzi e metodi per i prodotti, restituiamo 201 con Location in creazione e verifichiamo l’intero ciclo della risorsa.

Richiede JDK 25. Il progetto include Maven Wrapper.

```bash
./mvnw test
./mvnw package
java -jar target/shop-0.0.1-SNAPSHOT.jar
```

Su Windows usa `mvnw.cmd`. Il primo build scarica le dipendenze.

Quando il log mostra `Started ShopApplication`, apri il [client HTTP nel browser](http://127.0.0.1:18083/rest-client.html?request=list-products.http). La richiesta viene caricata ma si invia soltanto premendo **Send request**. Leggi stato, intestazioni e corpo nelle due colonne. I file `.http` in `requests/` si possono usare anche in un IDE con client HTTP abilitato.

Il client web incluso serve per provare il laboratorio in locale. Non richiede account, servizi esterni o una licenza IDE. I dati sono fittizi. Al termine arresta il server con Ctrl+C.

Questa cartella contiene il risultato finale della lezione: i quattro test devono passare. Lo starter dell’esercizio allegato alla lezione contiene invece una modifica da completare.

Per il ciclo completo usa nell’ordine `list-products`, `create-product`, `read-product`, `delete-product`, `delete-again`, `read-deleted`, `unsupported-content` (file `.http`). Parti da un avvio pulito ed esegui la creazione una sola volta: il nuovo identificatore sarà 2. Se ripeti POST, aggiorna l’indirizzo delle richieste successive usando Location. Il catalogo conserva i dati soltanto in memoria.
