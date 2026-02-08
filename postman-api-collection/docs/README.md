# Postman API Documentation

Documentation for scope, endpoints, and run instructions.

## Files

| File          | Content |
|---------------|---------|
| `TEST_LIST.md` | Full list of 55 tests to implement (aligned with GoREST) |
| `API-Scope.md` | Covered endpoints, resources, flows (to be created) |
| `Run-Guide.md` | How to import, configure, and run the collection (to be created) |

## API Scope

Will be aligned with the API used in `java-rest-api-automation`:
- Base URL from `.env` / environment
- Auth: Bearer token or API key
- Resources: Users, Posts or equivalent

## Running Newman in CI

```bash
newman run ../collection/REST-API-Tests.postman_collection.json \
  -e ../environments/demo.postman_environment.json \
  --reporters cli,html \
  --reporter-html-export report.html
```
