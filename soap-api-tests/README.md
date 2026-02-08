# SOAP API Tests

Teste pentru API-uri SOAP (Simple Object Access Protocol). Complementează suite-ul REST din `java-rest-api-automation`.

## Obiectiv

- Demonstra experiență cu **SOAP** (cerință JD: "REST/SOAP")
- Teste funcționale pentru endpoint-uri SOAP
- Validare request/response XML, status, fault handling

## Structură (de implementat)

```
soap-api-tests/
├── requests/                   # Request-uri XML exemple
│   ├── get-user.xml
│   └── create-order.xml
├── tests/                      # Teste automate
│   ├── Java/                   # RestAssured SOAP sau SoapUI
│   │   └── SoapApiTest.java
│   └── Postman/                # Postman suportă SOAP
│       └── (în postman-api-collection)
├── docs/
│   └── Endpoints.md            # WSDL, operații disponibile
├── .env.example                # SOAP endpoint URL
└── README.md
```

## Tehnologii posibile

| Opțiune        | Descriere                          |
|----------------|------------------------------------|
| **RestAssured**| Suport SOAP în Java (XML body)     |
| **Postman**    | Request SOAP cu body XML           |
| **SoapUI**     | Tool dedicat SOAP (open source)    |

## Endpoint demo (exemplu)

Pot fi folosite servicii SOAP publice pentru demo:
- [GeoIPService](http://www.webservicex.com/geoipservice.asmx)
- Sau un mock local (ex. WireMock cu SOAP)

## Cerințe

- **WSDL** documentat pentru endpoint-ul testat
- **Assertions**: status 200, conținut XML așteptat, fault handling
- **Nu hardcodați** URL-uri sau credențiale — `.env`
