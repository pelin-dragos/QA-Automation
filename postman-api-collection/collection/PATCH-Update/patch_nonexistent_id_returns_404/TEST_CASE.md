# Test Case: PATCH with Non-Existent ID Returns 404

| Attribute | Value |
|-----------|--------|
| **Test Case ID** | API-PATCH-003 |
| **Priority** | High |
| **Type** | REST API, Negative |

## Objective

Verify that a PATCH request with a non-existent resource ID returns HTTP 404 Not Found.

## Preconditions

- Base URL and endpoint configured.
- Non-existent ID: 999999999.

## Test Data

- Endpoint: `PATCH {{base_url}}/users/999999999`
- Body: `{"status":"inactive"}`

## Steps

1. Send a PATCH request with non-existent ID.
2. Capture the response status code.
3. Verify status is 404.

## Expected Result

- Response status code is **404**.
- No resource is updated.

## Postman Automation Notes

- **Tests tab:** `pm.test("Status is 404", () => pm.response.to.have.status(404));`

---

## Traceability

| Item | Location |
|------|----------|
| **Test Case ID** | API-PATCH-003 |
| **Postman request** | PATCH Update > Non-Existent ID Returns 404 |
| **Project path** | `postman-api-collection/collection/PATCH-Update/patch_nonexistent_id_returns_404/` |
| **Java equivalent** | `java-rest-api-automation/rest-api-tests/patch-update/patch_nonexistent_id_returns_404/` |
