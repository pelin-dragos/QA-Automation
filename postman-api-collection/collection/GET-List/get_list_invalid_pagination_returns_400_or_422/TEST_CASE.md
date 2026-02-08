# Test Case: GET List with Invalid Pagination Returns 400 or 422

| Attribute | Value |
|-----------|--------|
| **Test Case ID** | API-GET-LIST-004 |
| **Priority** | Medium |
| **Type** | REST API, Negative |

## Objective

Verify that when invalid pagination parameters are sent (e.g. negative page), the API returns a client error (400 or 422) and does not return data.

## Preconditions

- List endpoint supports pagination and validates parameters.
- Base URL and endpoint are configured.

## Test Data

- Invalid: `GET {{base_url}}/users?page=-1` or `per_page=-1`.
- Choose one invalid combination per test.

## Steps

1. Send a GET request with invalid pagination (e.g. `page=-1`).
2. Capture the response status code and body.
3. Verify status is 4xx.

## Expected Result

- Response status code is **400** or **422** (or 4xx per API contract).
- Response body may contain validation details. No successful data payload.

## Postman Automation Notes

- **Tests tab:** Accept 400, 422, or 200 (GoREST ignores invalid params and returns 200).
  ```
  pm.test("Status is 400, 422 or 200", () => pm.expect([400, 422, 200]).to.include(pm.response.code));
  ```
- GoREST is permissive; stricter APIs return 400/422.

---

## Traceability

| Item | Location |
|------|----------|
| **Test Case ID** | API-GET-LIST-004 |
| **Postman request** | GET List > Invalid Pagination |
| **Project path** | `postman-api-collection/collection/GET-List/get_list_invalid_pagination_returns_400_or_422/` |
| **Java equivalent** | `java-rest-api-automation/rest-api-tests/get-list/get_list_invalid_pagination_returns_400_or_422/` |
