# Test Case: GET List Respects Query Filters and Returns Matching Results

| Attribute | Value |
|-----------|--------|
| **Test Case ID** | API-GET-LIST-006 |
| **Priority** | High |
| **Type** | REST API, Functional |

## Objective

Verify that when supported query filters are applied, the API returns only items that match the filter criteria.

## Preconditions

- List endpoint supports at least one query filter (e.g. `status` for GoREST).
- Base URL and endpoint are configured.

## Test Data

- Filter: `GET {{base_url}}/users?status=active` (or `inactive`).
- Expected: only resources with matching status appear.

## Steps

1. Send a GET request with the filter parameter (e.g. `?status=active`).
2. Parse the response and verify each item satisfies the filter.
3. Assert that all returned items have the expected field value.

## Expected Result

- Response status code is **200**.
- Every item in the response satisfies the filter (e.g. `status=active`).
- No items that do not match are present.

## Postman Automation Notes

- **Tests tab:**
  ```
  pm.test("Status is 200", () => pm.response.to.have.status(200));
  pm.test("All items match filter", () => {
    const json = pm.response.json();
    pm.expect(json).to.be.an('array');
    json.forEach(item => pm.expect(item.status).to.eql('active'));
  });
  ```
- Adapt filter and field to API (GoREST: `status`, `gender`).

---

## Traceability

| Item | Location |
|------|----------|
| **Test Case ID** | API-GET-LIST-006 |
| **Postman request** | GET List > Respects Query Filters |
| **Project path** | `postman-api-collection/collection/GET-List/get_list_respects_query_filters_returns_matching_results/` |
| **Java equivalent** | `java-rest-api-automation/rest-api-tests/get-list/get_list_respects_query_filters_returns_matching_results/` |
