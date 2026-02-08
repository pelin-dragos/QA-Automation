# Test Case: GET List with Valid Pagination Returns Correct Page and Size

| Attribute | Value |
|-----------|--------|
| **Test Case ID** | API-GET-LIST-003 |
| **Priority** | High |
| **Type** | REST API, Functional |

## Objective

Verify that when valid pagination parameters are sent, the API returns the correct page of results and respects the requested page size.

## Preconditions

- List endpoint supports pagination (e.g. `?page=1&per_page=10` for GoREST).
- Base URL and endpoint are configured.

## Test Data

- Endpoint: `GET {{base_url}}/users?page=1&per_page=10`.
- Valid values: `page=1`, `per_page=10`.

## Steps

1. Send a GET request with valid pagination parameters.
2. Capture the response status code and body.
3. Parse the response and verify collection size and pagination metadata.

## Expected Result

- Response status code is **200**.
- Response contains at most `per_page` items.
- Pagination metadata (if supported) is consistent with the request.

## Postman Automation Notes

- **Tests tab:**
  ```
  pm.test("Status is 200", () => pm.response.to.have.status(200));
  pm.test("Max per_page items returned", () => {
    const json = pm.response.json();
    pm.expect(json).to.be.an('array');
    pm.expect(json.length).to.be.at.most(10);
  });
  ```

---

## Traceability

| Item | Location |
|------|----------|
| **Test Case ID** | API-GET-LIST-003 |
| **Postman request** | GET List > Valid Pagination |
| **Project path** | `postman-api-collection/collection/GET-List/get_list_valid_pagination_returns_correct_page_and_size/` |
| **Java equivalent** | `java-rest-api-automation/rest-api-tests/get-list/get_list_valid_pagination_returns_correct_page_and_size/` |
