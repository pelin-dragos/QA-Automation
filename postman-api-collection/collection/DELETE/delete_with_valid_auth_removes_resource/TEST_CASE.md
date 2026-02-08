# Test Case: DELETE with Valid Auth Removes Resource and Returns Success

| Attribute | Value |
|-----------|--------|
| **Test Case ID** | API-DELETE-006 |
| **Priority** | High |
| **Type** | REST API, Functional, Security |

## Objective

Verify that a DELETE request with valid authentication removes the resource and returns a success status (204 or 200).

## Preconditions

- Resource exists (create in setup or previous request).
- Valid ID and auth token available.
- Base URL and endpoint configured.

## Test Data

- Endpoint: `DELETE {{base_url}}/users/{{user_id}}`
- Auth: Bearer {{auth_token}}

## Steps

1. Create or obtain a resource; note its ID.
2. Send a DELETE request with valid auth.
3. Capture the response status code.
4. Optionally send GET for same ID and verify 404.

## Expected Result

- Response status code is **204** (or 200 per contract).
- Resource is deleted. Subsequent GET returns 404.
- No 401 or 403.

## Postman Automation Notes

- **Tests tab:**
  ```
  pm.test("Status is 204 or 200", () => pm.expect([204, 200]).to.include(pm.response.code));
  ```

---

## Traceability

| Item | Location |
|------|----------|
| **Test Case ID** | API-DELETE-006 |
| **Postman request** | DELETE > With Valid Auth Removes Resource |
| **Project path** | `postman-api-collection/collection/DELETE/delete_with_valid_auth_removes_resource/` |
| **Java equivalent** | `java-rest-api-automation/rest-api-tests/delete/delete_with_valid_auth_removes_resource_and_returns_success/` |
