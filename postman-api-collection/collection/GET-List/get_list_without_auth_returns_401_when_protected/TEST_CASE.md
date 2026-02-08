# Test Case: GET List Without Auth Returns 401 When Endpoint Is Protected

| Attribute | Value |
|-----------|--------|
| **Test Case ID** | API-GET-LIST-007 |
| **Priority** | High |
| **Type** | REST API, Security, Negative |

## Objective

Verify that when the list endpoint requires authentication, a GET request without credentials returns HTTP 401 Unauthorized.

## Preconditions

- List endpoint is configured to require authentication (if applicable).
- Base URL and endpoint are configured.
- Test does not send any auth header.

## Test Data

- Endpoint: `GET {{base_url}}/users` (or POST when GET list is public, e.g. GoREST).
- No `Authorization` header.

## Steps

1. Send a request to a protected endpoint without authentication (GET list or POST when list is public).
2. Capture the response status code and body.
3. Verify status is 401.

## Expected Result

- Response status code is **401 Unauthorized**.
- No list data is returned. Body may contain error message.
- N/A if endpoint is public; skip or document.

## Postman Automation Notes

- **Tests tab:** `pm.test("Status is 401", () => pm.response.to.have.status(401));`
- Ensure no Authorization header. GoREST GET list is often public; use POST/PUT/DELETE for protected endpoints if needed.

---

## Traceability

| Item | Location |
|------|----------|
| **Test Case ID** | API-GET-LIST-007 |
| **Postman request** | GET List > Without Auth Returns 401 |
| **Project path** | `postman-api-collection/collection/GET-List/get_list_without_auth_returns_401_when_protected/` |
| **Java equivalent** | `java-rest-api-automation/rest-api-tests/get-list/get_list_without_auth_returns_401_when_protected/` |
