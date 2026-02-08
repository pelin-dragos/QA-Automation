# Test Case: GET List with Invalid Pagination Returns 400 or 422

| Attribute | Value |
|-----------|--------|
| **Test Case ID** | API-GET-LIST-004 |
| **Priority** | Medium |
| **Type** | REST API, Negative |

## Objective

Verify that when invalid pagination parameters are sent (e.g. negative page number, zero or negative size), the API returns a client error status (400 Bad Request or 422 Unprocessable Entity) and does not return data.

## Preconditions

- List endpoint supports pagination and validates parameters.
- Base URL and endpoint are configured.

## Test Data

- Invalid examples: `page=-1`, `size=-1`, or `page=0` if not allowed (per API contract).
- Choose one or two invalid combinations to keep the test focused.

## Steps

1. Send a GET request to the list endpoint with invalid pagination parameters (e.g. `page=-1&size=10`).
2. Capture the response status code and body.
3. Optionally repeat with another invalid value (e.g. `size=0`) if a single test covers multiple cases per project convention.

## Expected Result

- Response status code is **400** or **422** (or another 4xx defined in the API contract for validation errors).
- Response body may contain an error message or validation details; no successful data payload.

## Automation Notes

- Assert on status code only, or on status and presence of error message/code in body.
- Use constants for invalid values to keep the test maintainable.

---

## Traceability (automation)

| Item | Location |
|------|----------|
| **Test Case ID** | API-GET-LIST-004 |
| **Automated test (source)** | `GetListInvalidPaginationReturns400Or422Test.java` (same folder) |
| **Project path** | `java-rest-api-automation/rest-api-tests/get-list/get_list_invalid_pagination_returns_400_or_422/` |
