# Test Case: PATCH Without Auth Returns 401 When Endpoint Is Protected

| Attribute | Value |
|-----------|--------|
| **Test Case ID** | API-PATCH-006 |
| **Priority** | High |
| **Type** | REST API, Security, Negative |

## Objective

Verify that a PATCH request without authentication returns HTTP 401 when the endpoint is protected.

## Preconditions

- PATCH endpoint requires authentication.
- Valid resource ID. No Authorization header.

## Test Data

- Endpoint: `PATCH {{base_url}}/users/{{user_id}}`
- Body: `{"status":"inactive"}`. No auth header.

## Steps

1. Send a PATCH request without Authorization header.
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
| **Test Case ID** | API-PATCH-006 |
| **Postman request** | PATCH Update > Without Auth Returns 401 |
| **Project path** | `postman-api-collection/collection/PATCH-Update/patch_without_auth_returns_401/` |
| **Java equivalent** | `java-rest-api-automation/rest-api-tests/patch-update/patch_without_auth_returns_401_when_protected/` |
