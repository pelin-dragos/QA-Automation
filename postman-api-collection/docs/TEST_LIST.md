# Postman API — Test List

Tests to include in the Postman collection. Aligned with `java-rest-api-automation` and **GoREST API** (`https://gorest.co.in/public/v2`).

---

## Auth

| # | Test name | Request | Assertions |
|---|-----------|---------|------------|
| 1 | Auth: Valid token returns 200/201 | GET/POST with Bearer token | Status 200 or 201 |
| 2 | Auth: No token returns 401 | Request without Authorization header | Status 401 |
| 3 | Auth: Invalid token returns 401 | Request with invalid/malformed token | Status 401 |
| 4 | Auth: Expired token returns 401 | Request with expired JWT | Status 401, error message in body |

---

## GET — List

| # | Test name | Request | Assertions |
|---|-----------|---------|------------|
| 5 | GET list returns 200 | GET `/users` | Status 200 |
| 6 | GET list returns valid JSON array | GET `/users` | Body is array, items have expected structure |
| 7 | GET list with pagination returns correct page/size | GET `/users?page=1&per_page=10` | Status 200, max 10 items |
| 8 | GET list invalid pagination returns 400/422 | GET `/users?page=-1` | Status 400 or 422 |
| 9 | GET list when empty returns 200 and empty array | GET with filter that matches nothing | Status 200, body `[]` |
| 10 | GET list respects query filters | GET `/users?status=active` | Returns only matching results |
| 11 | GET list without auth returns 401 (when protected) | GET without token | Status 401 |
| 12 | GET list with valid auth returns 200 and data | GET with Bearer token | Status 200, non-empty array |

---

## GET — Single resource

| # | Test name | Request | Assertions |
|---|-----------|---------|------------|
| 13 | GET by valid ID returns 200 and correct body | GET `/users/{id}` | Status 200, id matches, required fields present |
| 14 | GET by valid ID returns expected fields and types | GET `/users/{id}` | Fields: id (number), name (string), email (string), gender, status |
| 15 | GET by non-existent ID returns 404 | GET `/users/999999999` | Status 404 |
| 16 | GET by invalid ID format returns 400/404 | GET `/users/invalid` | Status 400 or 404 |
| 17 | GET without auth returns 401 (when protected) | GET without token | Status 401 |
| 18 | GET with valid auth returns 200 and data | GET with Bearer token | Status 200, correct resource |

---

## POST — Create

| # | Test name | Request | Assertions |
|---|-----------|---------|------------|
| 19 | POST valid body returns 201 and Location | POST `/users` with valid body | Status 201, Location header present |
| 20 | POST valid body returns created resource in response | POST `/users` | Status 201, body contains id, name, email, etc. |
| 21 | POST missing required field returns 400 | POST without `name` or `email` | Status 400/422, validation message |
| 22 | POST invalid field format returns 400 | POST with invalid email format | Status 400/422, validation message |
| 23 | POST empty body returns 400/415 | POST with empty body | Status 400 or 415 |
| 24 | POST wrong Content-Type returns 415/400 | POST with `Content-Type: text/plain` | Status 415 or 400 |
| 25 | POST without auth returns 401 | POST without Bearer token | Status 401 |
| 26 | POST with valid auth creates resource | POST with token | Status 201, resource created |
| 27 | POST duplicate (unique constraint) returns 409/400 | POST same email twice | Status 409 or 400 |

---

## PUT — Full update

| # | Test name | Request | Assertions |
|---|-----------|---------|------------|
| 28 | PUT valid body existing ID returns 200 | PUT `/users/{id}` with full body | Status 200, body contains updated fields |
| 29 | PUT response contains updated fields and values | PUT `/users/{id}` | Response matches sent data |
| 30 | PUT non-existent ID returns 404 | PUT `/users/999999999` | Status 404 |
| 31 | PUT invalid ID format returns 400/404 | PUT `/users/invalid` | Status 400 or 404 |
| 32 | PUT missing required fields returns 400 | PUT with partial body missing required | Status 400, validation errors |
| 33 | PUT invalid field values returns 400 | PUT with invalid gender/status | Status 400, validation message |
| 34 | PUT without auth returns 401 | PUT without token | Status 401 |
| 35 | PUT with valid auth updates resource | PUT with token | Status 200, resource updated |

---

## PATCH — Partial update

| # | Test name | Request | Assertions |
|---|-----------|---------|------------|
| 36 | PATCH valid partial body returns 200 | PATCH `/users/{id}` with `{"status":"inactive"}` | Status 200, only sent field updated |
| 37 | PATCH updates only sent fields | PATCH with one field | Other fields unchanged |
| 38 | PATCH non-existent ID returns 404 | PATCH `/users/999999999` | Status 404 |
| 39 | PATCH invalid field value returns 400 | PATCH with invalid value | Status 400 |
| 40 | PATCH empty body returns 400 or 200 | PATCH with `{}` | Per API contract |
| 41 | PATCH without auth returns 401 | PATCH without token | Status 401 |
| 42 | PATCH with valid auth updates resource | PATCH with token | Status 200 |

---

## DELETE

| # | Test name | Request | Assertions |
|---|-----------|---------|------------|
| 43 | DELETE valid ID returns 204/200 | DELETE `/users/{id}` | Status 204 or 200 |
| 44 | DELETE non-existent ID returns 404 | DELETE `/users/999999999` | Status 404 |
| 45 | DELETE invalid ID format returns 400/404 | DELETE `/users/invalid` | Status 400 or 404 |
| 46 | GET after DELETE returns 404 | DELETE then GET same ID | Status 404 |
| 47 | DELETE without auth returns 401 | DELETE without token | Status 401 |
| 48 | DELETE with valid auth removes resource | DELETE with token | Status 204, resource gone |
| 49 | DELETE with conflict returns 409 (when applicable) | DELETE resource in use | Status 409 |

---

## Headers & Content-Type

| # | Test name | Request | Assertions |
|---|-----------|---------|------------|
| 50 | Accept application/json receives JSON | Request with `Accept: application/json` | Response is JSON |
| 51 | Response includes Content-Type application/json | Any valid request | Header `Content-Type: application/json` |
| 52 | Response does not expose sensitive headers | Any request | No `X-Powered-By`, server version, etc. (or as expected) |

---

## Error responses

| # | Test name | Request | Assertions |
|---|-----------|---------|------------|
| 53 | 4xx responses include error message/code | Trigger 400/404/422 | Body contains error message or code |
| 54 | 5xx handled without crashing client | If testable via mock | Client does not crash |
| 55 | Response time within timeout | Success request | `pm.expect(pm.response.responseTime).to.be.below(5000)` (optional) |

---

## Summary

| Category | Count |
|----------|-------|
| Auth | 4 |
| GET — List | 8 |
| GET — Single | 6 |
| POST — Create | 9 |
| PUT — Update | 8 |
| PATCH — Partial | 7 |
| DELETE | 7 |
| Headers | 3 |
| Error responses | 3 |
| **Total** | **55** |

---

## Suggested Postman folder structure

```
REST-API-Tests
├── Auth (4 requests)
├── GET List (8 requests)
├── GET Single (6 requests)
├── POST Create (9 requests)
├── PUT Update (8 requests)
├── PATCH Update (7 requests)
├── DELETE (7 requests)
├── Headers (3 requests)
└── Error Responses (3 requests)
```

---

## GoREST example body (POST/PUT)

```json
{
  "name": "Test User",
  "email": "test.user.123@example.com",
  "gender": "male",
  "status": "active"
}
```

Use unique email per run (e.g. timestamp) to avoid duplicate errors.
