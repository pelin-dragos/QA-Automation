# Postman Collection

This folder contains Postman collections for REST API testing.

## Structure

```
collection/
├── Auth/                  # 4 tests — each with subfolder + TEST_CASE.md
├── GET-List/              # 8 tests
├── GET-Single/            # 6 tests
├── POST-Create/           # 9 tests
├── PUT-Update/            # 8 tests
├── PATCH-Update/          # 7 tests
├── DELETE/                # 7 tests
├── Headers/               # 3 tests
├── Error-Responses/       # 3 tests
├── REST-API-Tests.postman_collection.json   # Main collection (to be created)
└── README.md
```

Each category has subfolders per test (e.g. `Auth/valid_token_returns_200_or_201/`) with:
- **TEST_CASE.md** — professional test case (aligned with java-rest-api-automation)
- **REQUEST.http** — request definition (REST Client format, runnable in VS Code)

**Main collection:** `REST-API-Tests.postman_collection.json` — all 55 requests with `pm.test()` assertions for Postman/Newman.

## Requirements for each request

- **Pre-request script:** Token, dynamic variables (if needed)
- **Tests tab:**
  - `pm.test()` for status code
  - `pm.expect()` for body
  - Schema validation (optional)

## Running

- **Postman:** Import collection → Select environment → Run collection
- **Newman CLI:** `newman run REST-API-Tests.postman_collection.json -e ../environments/demo.postman_environment.json`

## Alignment with Java API tests

Tests in this collection will cover the same API as `java-rest-api-automation` for consistency.
