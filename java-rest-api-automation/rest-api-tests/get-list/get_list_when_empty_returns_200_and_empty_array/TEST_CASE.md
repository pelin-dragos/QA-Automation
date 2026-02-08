# Test Case: GET List When Empty Returns 200 and Empty Array

| Attribute | Value |
|-----------|--------|
| **Test Case ID** | API-GET-LIST-005 |
| **Priority** | Medium |
| **Type** | REST API, Functional |

## Objective

Verify that when the list/collection has no items (empty state), the GET list endpoint returns HTTP 200 and an empty array (or equivalent empty collection structure), not an error.

## Preconditions

- List endpoint is available.
- A way to achieve an empty list exists: either the collection is empty by default, or test uses a filter that yields no results, or a dedicated test environment has no data.
- Base URL and endpoint are configured.

## Test Data

- Endpoint: list endpoint, optionally with filter that returns no results (e.g. `?status=NonExistent`), or default list if empty.

## Steps

1. Send a GET request to the list endpoint under conditions that yield an empty collection (e.g. no data, or filter with no matches).
2. Capture the response status code and body.
3. Parse the response and verify the collection element is an empty array (or empty collection structure per contract).

## Expected Result

- Response status code is **200**.
- Response body contains a valid empty collection (e.g. `[]` or `{"data": []}` as per API contract).
- No server error (5xx) or client error (4xx) for “no data” scenario.

## Automation Notes

- Assert on status 200 and on empty array (length 0 or equivalent). Adapt to actual API response shape.
- Document how empty state was achieved (e.g. filter, clean DB, or default env).

---

## Traceability (automation)

| Item | Location |
|------|----------|
| **Test Case ID** | API-GET-LIST-005 |
| **Automated test (source)** | `GetListWhenEmptyReturns200AndEmptyArrayTest.java` (same folder) |
| **Project path** | `java-rest-api-automation/rest-api-tests/get-list/get_list_when_empty_returns_200_and_empty_array/` |
