# Test Case: PUT with Invalid ID Format Returns 400 or 404

| Attribute | Value |
|-----------|--------|
| **Test Case ID** | API-PUT-004 |
| **Priority** | Medium |
| **Type** | REST API, Negative |

## Objective

Verify that a PUT request with an invalid ID format (e.g. string when numeric expected) returns 400 or 404.

## Preconditions

- Base URL and endpoint configured.
- Invalid ID: `invalid` or `abc`.

## Test Data

- Endpoint: `PUT {{base_url}}/users/invalid`
- Valid body.

## Steps

1. Send a PUT request with invalid ID format.
2. Capture the response status code.
3. Verify status is 400 or 404.

## Expected Result

- Response status code is **400** or **404**.
- No resource is updated.

## Postman Automation Notes

- **Tests tab:**
  ```
  pm.test("Status is 400 or 404", () => pm.expect([400, 404]).to.include(pm.response.code));
  ```

---

## Traceability

| Item | Location |
|------|----------|
| **Test Case ID** | API-PUT-004 |
| **Postman request** | PUT Update > Invalid ID Format |
| **Project path** | `postman-api-collection/collection/PUT-Update/put_invalid_id_format_returns_400_or_404/` |
| **Java equivalent** | `java-rest-api-automation/rest-api-tests/put-update/put_invalid_id_format_returns_400_or_404/` |
