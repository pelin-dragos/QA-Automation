# Test Case: GET Resource Without Auth Returns 401 When Protected

| Attribute | Value |
|-----------|--------|
| **Test Case ID** | API-GET-SINGLE-005 |
| **Priority** | High |
| **Type** | REST API, Security, Negative |

## Objective

Verify that when the resource-by-ID endpoint requires authentication, a GET request without credentials returns HTTP 401 Unauthorized.

## Preconditions

- Resource-by-ID endpoint requires authentication (if applicable).
- Base URL and endpoint are configured.
- No Authorization header sent.

## Test Data

- Endpoint: `GET {{base_url}}/users/{{user_id}}` (or POST when GET by ID is public, e.g. GoREST).
- No Authorization header.

## Steps

1. Send a GET request to the resource-by-ID endpoint without auth.
2. Capture the response status code.
3. Verify status is 401.

## Expected Result

- Response status code is **401 Unauthorized**.
- No resource data is returned.
- N/A if endpoint is public.

## Postman Automation Notes

- **Tests tab:** `pm.test("Status is 401", () => pm.response.to.have.status(401));`
- Ensure no auth header. GoREST GET by ID may be public; document N/A if so.

---

## Traceability

| Item | Location |
|------|----------|
| **Test Case ID** | API-GET-SINGLE-005 |
| **Postman request** | GET Single > Without Auth Returns 401 |
| **Project path** | `postman-api-collection/collection/GET-Single/get_without_auth_returns_401_when_protected/` |
| **Java equivalent** | `java-rest-api-automation/rest-api-tests/get-single/get_by_id_without_auth_returns_401_when_protected/` |
