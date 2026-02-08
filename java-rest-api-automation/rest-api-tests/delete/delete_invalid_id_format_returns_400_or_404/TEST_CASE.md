# Test Case: DELETE with Invalid ID Format Returns 400 or 404

| Attribute | Value |
|-----------|--------|
| **Test Case ID** | API-DELETE-003 |
| **Priority** | Medium |
| **Type** | REST API, Negative |

## Objective

Verify that a DELETE request with an invalid ID format in the path (e.g. non-numeric when numeric expected) returns HTTP 400 or 404 and does not delete any resource.

## Preconditions

- Delete endpoint expects a specific ID format (e.g. numeric, UUID).
- Base URL and endpoint are configured.

## Test Data

- Invalid ID format: e.g. `abc`, `invalid`, or malformed UUID.

## Steps

1. Send a DELETE request to the delete endpoint with an invalid ID in the path.
2. Capture the response status code and body.

## Expected Result

- Response status code is **400** or **404** (per API contract).
- No resource is deleted. No 500.

## Automation Notes

- Assert on status 400 or 404. Use a constant for the invalid ID.

---

## Traceability (automation)

| Item | Location |
|------|----------|
| **Test Case ID** | API-DELETE-003 |
| **Automated test (source)** | `DeleteInvalidIdFormatReturns400Or404Test.java` (same folder) |
| **Project path** | `java-rest-api-automation/rest-api-tests/delete/delete_invalid_id_format_returns_400_or_404/` |
