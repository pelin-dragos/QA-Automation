# Test Case: GET List Respects Query Filters and Returns Matching Results

| Attribute | Value |
|-----------|--------|
| **Test Case ID** | API-GET-LIST-006 |
| **Priority** | High |
| **Type** | REST API, Functional |

## Objective

Verify that when supported query filters (e.g. filter by status, type, or date range) are applied, the API returns only items that match the filter criteria.

## Preconditions

- List endpoint supports at least one query filter (documented).
- Test data exists that includes both matching and non-matching items for the chosen filter, or filter value is known to match a subset.
- Base URL and endpoint are configured.

## Test Data

- Filter parameter(s): e.g. `status=active`, `type=user` (use values from API contract).
- Expected: only resources matching the filter appear in the response.

## Steps

1. Send a GET request to the list endpoint without the filter; note the total or sample IDs.
2. Send a GET request with the filter parameter(s) set to a value that matches a known subset.
3. Parse the filtered response and verify each returned item satisfies the filter (e.g. each has `status=active`).
4. Optionally assert that filtered list size is less than or equal to unfiltered (when applicable).

## Expected Result

- Response status code is **200**.
- Every item in the response satisfies the filter criteria (e.g. same status, type, or range).
- No items that do not match the filter are present in the response.

## Automation Notes

- Assert on status 200 and iterate (or use RestAssured matchers) to verify each element matches the filter.
- Use filter parameter names and values from config or constants; document filter semantics if not obvious.

---

## Traceability (automation)

| Item | Location |
|------|----------|
| **Test Case ID** | API-GET-LIST-006 |
| **Automated test (source)** | `GetListRespectsQueryFiltersReturnsMatchingResultsTest.java` (same folder) |
| **Project path** | `java-rest-api-automation/rest-api-tests/get-list/get_list_respects_query_filters_returns_matching_results/` |
