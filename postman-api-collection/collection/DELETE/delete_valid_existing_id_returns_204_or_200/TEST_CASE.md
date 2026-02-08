# Test Case: DELETE with Valid Existing ID Returns 204 or 200

| Attribute | Value |
|-----------|--------|
| **Test Case ID** | API-DELETE-001 |
| **Priority** | High |
| **Type** | REST API, Functional |

## Objective

Verify that a DELETE request with a valid existing resource ID returns HTTP 204 No Content (or 200 per API contract) and removes the resource.

## Preconditions

- Resource exists (created in setup or previous request).
- Valid resource ID and auth available.
- Base URL and endpoint configured.

## Test Data

- Endpoint: `DELETE {{base_url}}/users/{{user_id}}`
- Auth: Bearer {{auth_token}}

## Steps

1. Ensure a resource exists (create in setup if needed).
2. Send a DELETE request with valid ID and auth.
3. Capture the response status code.
4. Optionally verify resource is gone (GET returns 404).

## Expected Result

- Response status code is **204** (or 200 per contract).
- Resource is deleted. Subsequent GET for same ID returns 404.

## Postman Automation Notes

- **Tests tab:**
  ```
  pm.test("Status is 204 or 200", () => pm.expect([204, 200]).to.include(pm.response.code));
  ```
- GoREST typically returns 204. Use created resource ID from collection variable.

---

## Traceability

| Item | Location |
|------|----------|
| **Test Case ID** | API-DELETE-001 |
| **Postman request** | DELETE > Valid ID Returns 204/200 |
| **Project path** | `postman-api-collection/collection/DELETE/delete_valid_existing_id_returns_204_or_200/` |
| **Java equivalent** | `java-rest-api-automation/rest-api-tests/delete/delete_valid_existing_id_returns_204_or_200/` |
