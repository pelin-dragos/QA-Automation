# Test Case: PUT with Non-Existent ID Returns 404

| Attribute | Value |
|-----------|--------|
| **Test Case ID** | API-PUT-003 |
| **Priority** | High |
| **Type** | REST API, Negative |

## Objective

Verify that a PUT request with a non-existent resource ID returns HTTP 404 Not Found.

## Preconditions

- Base URL and endpoint configured.
- Non-existent ID: 999999999.

## Test Data

- Endpoint: `PUT {{base_url}}/users/999999999`
- Valid body (so the only failure is non-existent resource).

## Steps

1. Send a PUT request with non-existent ID.
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
| **Test Case ID** | API-PUT-003 |
| **Postman request** | PUT Update > Non-Existent ID Returns 404 |
| **Project path** | `postman-api-collection/collection/PUT-Update/put_nonexistent_id_returns_404/` |
| **Java equivalent** | `java-rest-api-automation/rest-api-tests/put-update/put_nonexistent_id_returns_404/` |
