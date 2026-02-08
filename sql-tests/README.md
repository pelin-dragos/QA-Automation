# SQL Tests

Teste care verifică date în baza de date — validare după operații API, setup/cleanup, sau verificări de integritate.

## Obiectiv

- Demonstra **proficiență în SQL** pentru QA (cerință JD Senior QA Engineer)
- Verificări de date după POST/PUT/PATCH/DELETE
- Setup de date de test înainte de rulare
- Cleanup după teste (izolare, independență)

## Structură (de implementat)

```
sql-tests/
├── scripts/                    # Scripturi SQL reutilizabile
│   ├── setup/                  # Fișiere pentru setup (INSERT)
│   ├── cleanup/                # Fișiere pentru cleanup (DELETE)
│   └── assertions/             # Query-uri de verificare
├── java/                       # (Opțional) Teste Java cu JDBC
│   └── DbValidationTest.java
├── docs/
│   └── Schema.md               # Schema DB, tabele relevante
├── .env.example                # DB connection (user, host - fără parole)
└── README.md
```

## Tipuri de teste

1. **Setup**: INSERT date de test înainte de rulare
2. **Assertion**: SELECT pentru validare după operație API
3. **Cleanup**: DELETE/TRUNCATE pentru stare curată

## Cerințe

- **Nu hardcodați parole** — folosiți `.env` sau variabile de mediu
- **Test DB only** — niciodată pe producție
- **Parametrizare** — evitați concatenare directă în SQL (prevenire SQL injection)

## Integrare cu Java

Poate folosi același API din `java-rest-api-automation` + JDBC pentru verificări DB după request-uri REST.
