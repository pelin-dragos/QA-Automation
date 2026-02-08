# Test Case: Endpoint Requiring Auth Returns 200 or 201 With Valid Token

| Attribute | Value |
|-----------|--------|
| **Test Case ID** | API-AUTH-002 |
| **Priority** | High |
| **Type** | REST API, Security, Functional |

## Objective

Verify that an endpoint that requires authentication returns a success status (200 or 201) and the expected data when the request includes a valid authentication token.

## Preconditions

- Endpoint requires authentication.
- Valid token is available from environment (`{{auth_token}}`).
- Base URL and endpoint are configured.

## Test Data

- Valid auth token from environment.
- Request to protected endpoint: POST `/users` (GoREST requires auth for POST).

## Steps

1. Send a request to the protected endpoint with `Authorization: Bearer {{auth_token}}`.
2. Capture the response status code and body.
3. Verify success status and that data is returned as expected.

## Expected Result

- Response status code is **200** or **201** (as appropriate for the operation).
- Response body contains the expected data. No 401 or 403.

## Postman Automation Notes

- **Tests tab:** `pm.test("Status is 200 or 201", () => pm.expect([200, 201]).to.include(pm.response.code));`
- Assert on body structure if needed. Use `{{auth_token}}` from environment.

---

## Traceability

| Item | Location |
|------|----------|
| **Test Case ID** | API-AUTH-002 |
| **Postman request** | Auth > Valid Token Returns 200/201 |
| **Project path** | `postman-api-collection/collection/Auth/valid_token_returns_200_or_201/` |
| **Java equivalent** | `java-rest-api-automation/rest-api-tests/auth/endpoint_requiring_auth_returns_200_or_201_with_valid_token/` |
