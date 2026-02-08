# Test Case: DELETE with Non-Existent ID Returns 404

| Attribute | Value |
|-----------|--------|
| **Test Case ID** | API-DELETE-002 |
| **Priority** | High |
| **Type** | REST API, Negative |

## Objective

Verify that a DELETE request with a non-existent resource ID returns HTTP 404 Not Found.

## Preconditions

- Base URL and endpoint configured.
- Non-existent ID: 999999999.
- Valid auth (so the only failure is non-existent resource).

## Test Data

- Endpoint: `DELETE {{base_url}}/users/999999999`
- Auth: Bearer {{auth_token}}

## Steps

1. Send a DELETE request with non-existent ID.
2. Capture the response status code.
3. Verify status is 404.

## Expected Result

- Response status code is **404**.
- No 204 or 200 (resource not found, nothing deleted).

## Postman Automation Notes

- **Tests tab:** `pm.test("Status is 404", () => pm.response.to.have.status(404));`

---

## Traceability

| Item | Location |
|------|----------|
| **Test Case ID** | API-DELETE-002 |
| **Postman request** | DELETE > Non-Existent ID Returns 404 |
| **Project path** | `postman-api-collection/collection/DELETE/delete_nonexistent_id_returns_404/` |
| **Java equivalent** | `java-rest-api-automation/rest-api-tests/delete/delete_nonexistent_id_returns_404/` |
