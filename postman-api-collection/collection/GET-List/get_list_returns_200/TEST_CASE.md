# Test Case: GET List Returns Status 200

| Attribute | Value |
|-----------|--------|
| **Test Case ID** | API-GET-LIST-001 |
| **Priority** | High |
| **Type** | REST API, Functional |

## Objective

Verify that a GET request to the list (collection) endpoint returns HTTP status code 200 OK.

## Preconditions

- Base URL and list endpoint are configured (e.g. `GET {{base_url}}/users`).
- API is available and reachable.
- No mandatory authentication, or valid auth is supplied.

## Test Data

- Endpoint: `GET {{base_url}}/users`.
- No request body for GET.

## Steps

1. Send a GET request to the list/collection endpoint.
2. Include `Accept: application/json` if required.
3. Capture the response status code and body.

## Expected Result

- Response status code is **200**.
- Test passes when the status code assertion succeeds.

## Postman Automation Notes

- **Tests tab:** `pm.test("Status is 200", () => pm.response.to.have.status(200));`
- Use `{{base_url}}` from environment.

---

## Traceability

| Item | Location |
|------|----------|
| **Test Case ID** | API-GET-LIST-001 |
| **Postman request** | GET List > Returns 200 |
| **Project path** | `postman-api-collection/collection/GET-List/get_list_returns_200/` |
| **Java equivalent** | `java-rest-api-automation/rest-api-tests/get-list/get_list_returns_200/` |
