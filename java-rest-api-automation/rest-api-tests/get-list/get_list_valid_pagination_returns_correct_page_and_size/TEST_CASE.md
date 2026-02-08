# Test Case: GET List with Valid Pagination Returns Correct Page and Size

| Attribute | Value |
|-----------|--------|
| **Test Case ID** | API-GET-LIST-003 |
| **Priority** | High |
| **Type** | REST API, Functional |

## Objective

Verify that when valid pagination parameters (e.g. `page`, `size` or `limit`, `offset`) are sent, the API returns the correct page of results and respects the requested page size.

## Preconditions

- List endpoint supports pagination (query parameters documented).
- API has sufficient data to return multiple pages, or test accepts empty/second page behaviour per contract.
- Base URL and endpoint are configured.

## Test Data

- Endpoint: list endpoint with query parameters (e.g. `?page=1&size=10` or `?limit=10&offset=0`).
- Valid values: e.g. `page=1`, `size=10` (adjust to API contract).

## Steps

1. Send a GET request to the list endpoint with valid pagination parameters (e.g. `page=1`, `size=10`).
2. Capture the response status code and body.
3. Parse the response and identify the returned collection and any pagination metadata (e.g. `totalElements`, `totalPages`).

## Expected Result

- Response status code is **200**.
- Response contains a collection (array or equivalent) with at most `size` items (or as per API contract).
- When supported, pagination metadata (e.g. current page, page size, total count) is present and consistent with the request.

## Automation Notes

- Assert on status 200, and on `response body array size <= requested size` (and optionally on pagination metadata fields).
- Use query parameters from config or constants; avoid hardcoded values where possible.

---

## Traceability (automation)

| Item | Location |
|------|----------|
| **Test Case ID** | API-GET-LIST-003 |
| **Automated test (source)** | `GetListValidPaginationReturnsCorrectPageAndSizeTest.java` (same folder) |
| **Project path** | `java-rest-api-automation/rest-api-tests/get-list/get_list_valid_pagination_returns_correct_page_and_size/` |
