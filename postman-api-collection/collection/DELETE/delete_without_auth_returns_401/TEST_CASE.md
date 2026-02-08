# Test Case: DELETE Without Auth Returns 401 When Endpoint Is Protected

| Attribute | Value |
|-----------|--------|
| **Test Case ID** | API-DELETE-005 |
| **Priority** | High |
| **Type** | REST API, Security, Negative |

## Objective

Verify that a DELETE request without authentication returns HTTP 401 when the endpoint is protected.

## Preconditions

- DELETE endpoint requires authentication.
- Valid resource ID. No Authorization header.

## Test Data

- Endpoint: `DELETE {{base_url}}/users/1` (fixed ID; 401 returned before resource existence check)
- No auth header.

## Steps

1. Send a DELETE request without Authorization header.
2. Capture the response status code.
3. Verify status is 401.

## Expected Result

- Response status code is **401 Unauthorized** (or 404 when API avoids leaking resource existence).
- No resource is deleted.

## Postman Automation Notes

- **Tests tab:** Accept 401 or 404. `pm.test("Status is 401 or 404", () => pm.expect([401, 404]).to.include(pm.response.code));`

---

## Traceability

| Item | Location |
|------|----------|
| **Test Case ID** | API-DELETE-005 |
| **Postman request** | DELETE > Without Auth Returns 401 |
| **Project path** | `postman-api-collection/collection/DELETE/delete_without_auth_returns_401/` |
| **Java equivalent** | `java-rest-api-automation/rest-api-tests/delete/delete_without_auth_returns_401_when_protected/` |
