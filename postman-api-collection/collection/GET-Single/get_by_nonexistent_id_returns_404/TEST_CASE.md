# Test Case: GET Resource by Non-Existent ID Returns 404

| Attribute | Value |
|-----------|--------|
| **Test Case ID** | API-GET-SINGLE-003 |
| **Priority** | High |
| **Type** | REST API, Negative |

## Objective

Verify that a GET request with a non-existent ID returns HTTP 404 Not Found and does not return a resource body.

## Preconditions

- Base URL and resource-by-ID endpoint are configured.
- The chosen ID is guaranteed not to exist (e.g. 999999999).

## Test Data

- Non-existent ID: `999999999` (or very high number).
- Endpoint: `GET {{base_url}}/users/999999999`.

## Steps

1. Send a GET request with a non-existent resource ID.
2. Capture the response status code and body.
3. Verify status is 404.

## Expected Result

- Response status code is **404 Not Found**.
- Response body may contain error message; no successful resource representation.
- No 200 or 500.

## Postman Automation Notes

- **Tests tab:** `pm.test("Status is 404", () => pm.response.to.have.status(404));`
- Use a stable non-existent ID constant.

---

## Traceability

| Item | Location |
|------|----------|
| **Test Case ID** | API-GET-SINGLE-003 |
| **Postman request** | GET Single > Non-Existent ID Returns 404 |
| **Project path** | `postman-api-collection/collection/GET-Single/get_by_nonexistent_id_returns_404/` |
| **Java equivalent** | `java-rest-api-automation/rest-api-tests/get-single/get_by_nonexistent_id_returns_404/` |
