# Test Case: DELETE with Invalid ID Format Returns 400 or 404

| Attribute | Value |
|-----------|--------|
| **Test Case ID** | API-DELETE-003 |
| **Priority** | Medium |
| **Type** | REST API, Negative |

## Objective

Verify that a DELETE request with an invalid ID format (e.g. string when numeric expected) returns 400 or 404.

## Preconditions

- Base URL and endpoint configured.
- Invalid ID: `invalid` or `abc`.

## Test Data

- Endpoint: `DELETE {{base_url}}/users/invalid`
- Auth: Bearer {{auth_token}}

## Steps

1. Send a DELETE request with invalid ID format.
2. Capture the response status code.
3. Verify status is 400 or 404.

## Expected Result

- Response status code is **400** or **404**.
- No resource is deleted.

## Postman Automation Notes

- **Tests tab:**
  ```
  pm.test("Status is 400 or 404", () => pm.expect([400, 404]).to.include(pm.response.code));
  ```

---

## Traceability

| Item | Location |
|------|----------|
| **Test Case ID** | API-DELETE-003 |
| **Postman request** | DELETE > Invalid ID Format |
| **Project path** | `postman-api-collection/collection/DELETE/delete_invalid_id_format_returns_400_or_404/` |
| **Java equivalent** | `java-rest-api-automation/rest-api-tests/delete/delete_invalid_id_format_returns_400_or_404/` |
