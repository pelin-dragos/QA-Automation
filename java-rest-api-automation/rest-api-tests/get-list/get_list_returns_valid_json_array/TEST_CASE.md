# Test Case: GET List Returns Valid JSON Array

| Attribute | Value |
|-----------|--------|
| **Test Case ID** | API-GET-LIST-002 |
| **Priority** | High |
| **Type** | REST API, Functional |

## Objective

Verify that the GET list (collection) endpoint returns a valid JSON array (or the expected response structure) that can be parsed and validated.

## Preconditions

- Base URL and list endpoint are configured.
- API is available; GET list returns 200 (or this test can run after verifying status).
- Response is expected to be JSON (array or object with collection property).

## Test Data

- Endpoint: list/collection endpoint from config.
- Expected structure: JSON array `[]` or object with array field per API contract.

## Steps

1. Send a GET request to the list/collection endpoint.
2. Set `Accept: application/json` (or as required).
3. Parse the response body as JSON.
4. Verify the root element is an array or that the response matches the documented structure (e.g. `data` is an array).

## Expected Result

- Response body is valid JSON.
- Root is an array, or the response contains a collection field that is an array, as per API contract.
- No parsing or schema violation; test passes when structure assertion succeeds.

## Automation Notes

- Use JSON path or RestAssured body extraction to assert on type (array) and optionally on presence of expected top-level keys.
- Do not assert on exact number of items unless that is the test scope.

---

## Traceability (automation)

| Item | Location |
|------|----------|
| **Test Case ID** | API-GET-LIST-002 |
| **Automated test (source)** | `GetListReturnsValidJsonArrayTest.java` (same folder) |
| **Project path** | `java-rest-api-automation/rest-api-tests/get-list/get_list_returns_valid_json_array/` |
