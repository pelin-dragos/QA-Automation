# Test Case: GET List When Empty Returns 200 and Empty Array

| Attribute | Value |
|-----------|--------|
| **Test Case ID** | API-GET-LIST-005 |
| **Priority** | Medium |
| **Type** | REST API, Functional |

## Objective

Verify that when the list has no items (empty state), the GET list endpoint returns HTTP 200 and an empty array, not an error.

## Preconditions

- List endpoint is available.
- A way to achieve empty list: filter with no matches (e.g. `?status=NonExistent`) or empty default.

## Test Data

- Endpoint with filter that returns no results, or default list if empty.
- Example: `GET {{base_url}}/users?status=non_existent_status` (if supported).

## Steps

1. Send a GET request under conditions that yield an empty collection.
2. Capture the response status code and body.
3. Verify the collection element is an empty array.

## Expected Result

- Response status code is **200**.
- Response body contains a valid empty collection (`[]` or equivalent).
- No 4xx or 5xx for "no data" scenario.

## Postman Automation Notes

- **Tests tab:**
  ```
  pm.test("Status is 200", () => pm.response.to.have.status(200));
  pm.test("Body is empty array", () => {
    const json = pm.response.json();
    pm.expect(json).to.be.an('array').and.to.have.lengthOf(0);
  });
  ```
- Use filter that yields no results if API supports it. Otherwise document N/A if empty state cannot be achieved.

---

## Traceability

| Item | Location |
|------|----------|
| **Test Case ID** | API-GET-LIST-005 |
| **Postman request** | GET List > When Empty Returns 200 |
| **Project path** | `postman-api-collection/collection/GET-List/get_list_when_empty_returns_200_and_empty_array/` |
| **Java equivalent** | `java-rest-api-automation/rest-api-tests/get-list/get_list_when_empty_returns_200_and_empty_array/` |
