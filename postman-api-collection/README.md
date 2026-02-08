# Postman API Test Collection

Structured **Postman** (or **Bruno**) collection for REST API testing: requests, environments, and automated assertions.  


## Purpose

- Demonstrate **API testing** with **Postman** (or Bruno) as per industry preference.
- Full test list: see [docs/TEST_LIST.md](docs/TEST_LIST.md) — 55 tests aligned with `java-rest-api-automation` and GoREST API.
- Reusable collection with tests (status codes, response body, schema) and variables.
- Easy to export, share, and run in CI (Newman/Bruno CLI) if needed.

## Structure

```
postman-api-collection/
├── collection/                   # Postman collection(s) — see collection/README.md
│   └── REST-API-Tests.postman_collection.json  (to be created)
├── environments/                 # Environment variables — see environments/README.md
│   ├── demo.postman_environment.json           (to be created)
│   └── .env.example                            (to be created)
├── docs/                         # Documentation — see docs/README.md
│   └── API-Scope.md                            (to be created)
└── README.md
```

## Contents (suggested)

- **Collection:** Folders by resource or flow (e.g. Users, Posts, Auth). Each request with:
  - Pre-request script if needed (e.g. token, timestamp).
  - Tests tab: `pm.test()` for status, `pm.expect()` for body, optional schema validation.
- **Environments:** `base_url`, `api_key` (placeholder); real secrets via Postman env or `.env` (not committed).
- **README:** How to import, set environment, run collection/Newman.

## Running

- **Postman:** Import `collection/REST-API-Tests.postman_collection.json` and `environments/demo.postman_environment.json`. Set `auth_token` in environment. Run collection.
- **Newman CLI:** `newman run collection/REST-API-Tests.postman_collection.json -e environments/demo.postman_environment.json`
- **REST Client (VS Code):** Open any `REQUEST.http` in a test folder. Set variables in `collection/http-client.env.json` or `http-client.private.env.json`. Click "Send Request".

---

*Add your API endpoints, tests, and environment files as needed.*
