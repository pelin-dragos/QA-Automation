# Test Case: GET Resource by Invalid ID Format Returns 400 or 404

| Attribute | Value |
|-----------|--------|
| **Test Case ID** | API-GET-SINGLE-004 |
| **Priority** | Medium |
| **Type** | REST API, Negative |

## Objective

Verify that when the resource ID is in an invalid format (e.g. string when numeric expected), the API returns 400 or 404 and does not return a resource.

## Preconditions

- Resource-by-ID endpoint expects a specific ID format (e.g. numeric).
- Base URL and endpoint are configured.

## Test Data

- Invalid ID format: `invalid`, `abc`, or non-numeric string.
- Endpoint: `GET {{base_url}}/users/invalid`.

## Steps

1. Send a GET request with an invalid ID format in the path.
2. Capture the response status code and body.
3. Verify status is 400 or 404.

## Expected Result

- Response status code is **400** or **404**.
- No successful resource body is returned.

## Postman Automation Notes

- **Tests tab:**
  ```
  pm.test("Status is 400 or 404", () => {
    pm.expect([400, 404]).to.include(pm.response.code);
  });
  ```
- GoREST typically returns 404 for invalid ID format.

---

## Traceability

| Item | Location |
|------|----------|
| **Test Case ID** | API-GET-SINGLE-004 |
| **Postman request** | GET Single > Invalid ID Format |
| **Project path** | `postman-api-collection/collection/GET-Single/get_by_invalid_id_format_returns_400_or_404/` |
| **Java equivalent** | `java-rest-api-automation/rest-api-tests/get-single/get_by_invalid_id_format_returns_400_or_404/` |
