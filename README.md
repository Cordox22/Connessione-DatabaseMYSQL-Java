# Connessione al Database in Java — Ricerca Comuni e Province

Progetto Java che dimostra come stabilire una connessione a un database MySQL e come eseguire query sicure tramite `PreparedStatement`.  
Il programma permette di recuperare informazioni su **Comuni** e **Province** italiane utilizzando metodi dedicati, restituendo i risultati tramite `Optional` e mappandoli su record immutabili (`User`, `Province`).  
La gestione delle risorse è completamente automatizzata grazie all’uso di `AutoCloseable` e del costrutto `try-with-resources`.

## Funzionalità principali

- Connessione automatica al database tramite JDBC
- Query parametrizzate con `PreparedStatement` (protezione da SQL injection)
- Ricerca di:
  - Comuni tramite `findByComune(String comune)`
  - Province tramite `findByProvince(String codiceIstatProvincia)`
- Restituzione dei risultati tramite `Optional`
- Mapping dei record su oggetti immutabili (`User`, `Province`)
- Gestione sicura della connessione con `AutoCloseable`
- Gestione delle eccezioni SQL tramite `IllegalStateException`

## Struttura del progetto

### `DatabaseSora.java` — Classe Main
Contiene esempi pratici di interrogazione del database:

- Ricerca di un comune tramite `findByComune("Sora")`
- Ricerca di una provincia tramite `findByProvince("10")`
- Stampa dei campi principali del record trovato
- Gestione del caso “nessun risultato trovato”
- Gestione delle eccezioni tramite blocchi `try/catch`

### `Data.java` — Gestione connessione e query
Classe responsabile di:

- Apertura della connessione al database MySQL
- Esecuzione delle query tramite `PreparedStatement`
- Conversione dei risultati in oggetti `User` o `Province`
- Chiusura automatica della connessione tramite `close()`
- Esposizione controllata della connessione tramite `getConnection()`

### `User.java` — Record Comune
Rappresenta un comune italiano con i campi:

- comune  
- cap  
- prefisso  
- codice catastale  
- popolazione  

### `Province.java` — Record Provincia
Rappresenta una provincia italiana con i campi:

- codice ISTAT provincia  
- codice ISTAT regione  
- sigla  
- nome della provincia  

## Esempio di utilizzo

``try (Data data = new Data()) {
Optional<Province> opt = data.findByProvince("10");  ``Il 10 sta per "Cercami la provincia che ha codice ISTAT = 10"``
if (opt.isPresent()) {
Province pr = opt.get();
System.out.println("Codice Istat Provincia:           " + pr.codiceIstatProvincia());
System.out.println("Codice Istat Regione:             " + pr.codiceIstatRegione());
System.out.println("Sigla:                            " + pr.sigla());
System.out.println("Provincia:                        " + pr.provincia());
}
else {
System.out.println("Nessun record trovato");
}
}
catch (IllegalStateException e) {
System.err.println("Errore: " + e.getMessage());
} ``

## Esempio di Output

``Connessione al database riuscita!
Codice Istat Provincia:           10
Codice Istat Regione:             7
Sigla:                            RM
Provincia:                        Roma
``

*(L’output varia in base ai dati presenti nel database.)*

## Note tecniche

- La connessione utilizza il driver JDBC MySQL.
- `PreparedStatement` protegge da SQL injection.
- `Optional` evita l’uso di valori nulli.
- La connessione viene chiusa automaticamente grazie a `AutoCloseable`.
- Le query sono incapsulate nella classe `Data`, mantenendo il main pulito.
- I record `User` e `Province` garantiscono immutabilità e semplicità di utilizzo.
