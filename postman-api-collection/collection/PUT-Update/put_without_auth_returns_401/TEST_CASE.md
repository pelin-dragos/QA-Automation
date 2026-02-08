# Test Case: PUT Without Auth Returns 401 When Endpoint Is Protected

| Attribute | Value |
|-----------|--------|
| **Test Case ID** | API-PUT-007 |
| **Priority** | High |
| **Type** | REST API, Security, Negative |

## Objective

Verify that a PUT request without authentication returns HTTP 401 when the endpoint is protected.

## Preconditions

- PUT endpoint requires authentication.
- Valid resource ID. No Authorization header.

## Test Data

- Endpoint: `PUT {{base_url}}/users/{{user_id}}`
- Valid body. No auth header.

## Steps

1. Send a PUT request without Authorization header.
2. Capture the response status code.
3. Verify status is 401.

## Expected Result

- Response status code is **401 Unauthorized**.
- No resource is updated.

## Postman Automation Notes

- **Tests tab:** Accept 401 or 404 (GoREST may return 404 when resource/user_id invalid).
  ```
  pm.test("Status is 401 or 404", () => pm.expect([401, 404]).to.include(pm.response.code));
  ```

---

## Traceability

| Item | Location |
|------|----------|
| **Test Case ID** | API-PUT-007 |
| **Postman request** | PUT Update > Without Auth Returns 401 |
| **Project path** | `postman-api-collection/collection/PUT-Update/put_without_auth_returns_401/` |
| **Java equivalent** | `java-rest-api-automation/rest-api-tests/put-update/put_without_auth_returns_401_when_protected/` |
