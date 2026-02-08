# Test Case: GET List Returns Valid JSON Array

| Attribute | Value |
|-----------|--------|
| **Test Case ID** | API-GET-LIST-002 |
| **Priority** | High |
| **Type** | REST API, Functional |

## Objective

Verify that the GET list endpoint returns a valid JSON array (or expected response structure) that can be parsed and validated.

## Preconditions

- Base URL and list endpoint are configured.
- API is available; GET list returns 200.
- Response is expected to be JSON.

## Test Data

- Endpoint: `GET {{base_url}}/users`.
- Expected structure: JSON array `[]` or object with array field per API contract.

## Steps

1. Send a GET request to the list endpoint.
2. Set `Accept: application/json`.
3. Parse the response body as JSON.
4. Verify the root element is an array or matches documented structure.

## Expected Result

- Response body is valid JSON.
- Root is an array, or response contains a collection field that is an array.
- No parsing or schema violation.

## Postman Automation Notes

- **Tests tab:**
  ```
  pm.test("Response is JSON array", () => {
    const json = pm.response.json();
    pm.expect(json).to.be.an('array');
  });
  ```
- GoREST returns array directly. Adapt if API uses `{ data: [] }`.

---

## Traceability

| Item | Location |
|------|----------|
| **Test Case ID** | API-GET-LIST-002 |
| **Postman request** | GET List > Returns Valid JSON Array |
| **Project path** | `postman-api-collection/collection/GET-List/get_list_returns_valid_json_array/` |
| **Java equivalent** | `java-rest-api-automation/rest-api-tests/get-list/get_list_returns_valid_json_array/` |
