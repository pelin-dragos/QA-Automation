# Test Case: DELETE with Valid Existing ID Returns 204 or 200

| Attribute | Value |
|-----------|--------|
| **Test Case ID** | API-DELETE-001 |
| **Priority** | High |
| **Type** | REST API, Functional |

## Objective

Verify that a DELETE request with a valid existing resource ID returns HTTP 204 No Content (or 200 with empty/minimal body per API contract) and that the resource is removed.

## Preconditions

- Delete endpoint is configured (e.g. `DELETE /resources/{id}`).
- A resource exists (created in setup or test data) and its ID is known.

## Test Data

- Valid existing resource ID (created in test setup if necessary).

## Steps

1. Ensure a resource exists; obtain its ID.
2. Send a DELETE request to the resource-by-ID endpoint with that ID.
3. Capture the response status code and body (if any).
4. Optionally send GET for the same ID and verify 404.

## Expected Result

- Response status code is **204** (or 200 with no/empty body per contract).
- Resource is deleted. Subsequent GET with same ID returns 404 (can be a separate test or same test).

## Automation Notes

- Assert on status 204 (or 200). Create resource in setup if needed; do not rely on pre-existing data from other tests. Optional GET 404 assertion confirms deletion.

---

## Traceability (automation)

| Item | Location |
|------|----------|
| **Test Case ID** | API-DELETE-001 |
| **Automated test (source)** | `DeleteValidExistingIdReturns204Or200Test.java` (same folder) |
| **Project path** | `java-rest-api-automation/rest-api-tests/delete/delete_valid_existing_id_returns_204_or_200/` |
