# Test Case: GET List With Valid Auth Returns 200 and Data

| Attribute | Value |
|-----------|--------|
| **Test Case ID** | API-GET-LIST-008 |
| **Priority** | High |
| **Type** | REST API, Functional, Security |

## Objective

Verify that when the list endpoint requires authentication, a GET request sent with valid credentials (or valid token) returns HTTP 200 and the expected list data.

## Preconditions

- List endpoint requires authentication.
- Valid credentials or token are available from test config/environment (never hardcoded).
- Base URL, endpoint, and auth mechanism are configured.

## Test Data

- Endpoint: list endpoint.
- Auth: valid token or credentials from environment (e.g. `Authorization: Bearer {token}`).

## Steps

1. Obtain valid authentication (e.g. login or use pre-configured token from env).
2. Send a GET request to the list endpoint with the valid auth header (or equivalent).
3. Capture the response status code and body.
4. Verify response is successful and contains the expected structure (e.g. array or collection).

## Expected Result

- Response status code is **200**.
- Response body contains the list/collection as per API contract (valid JSON array or equivalent).
- No 401 or 403; authentication is accepted and data is returned.

## Automation Notes

- Use auth from config/env; do not hardcode tokens or passwords. Assert on status 200 and optionally on response structure.
- If endpoint is public, this test can assert 200 with no auth; document accordingly.

---

## Traceability (automation)

| Item | Location |
|------|----------|
| **Test Case ID** | API-GET-LIST-008 |
| **Automated test (source)** | `GetListWithValidAuthReturns200AndDataTest.java` (same folder) |
| **Project path** | `java-rest-api-automation/rest-api-tests/get-list/get_list_with_valid_auth_returns_200_and_data/` |
