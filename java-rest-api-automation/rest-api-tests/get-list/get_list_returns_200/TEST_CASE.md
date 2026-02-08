# Test Case: GET List Returns Status 200

| Attribute | Value |
|-----------|--------|
| **Test Case ID** | API-GET-LIST-001 |
| **Priority** | High |
| **Type** | REST API, Functional |

## Objective

Verify that a GET request to the list (collection) endpoint returns HTTP status code 200 OK.

## Preconditions

- Base URL and list endpoint are configured (e.g. `GET /resources`).
- API is available and reachable.
- No mandatory authentication is required for the endpoint, or valid auth is supplied.

## Test Data

- Endpoint: as per environment config (e.g. `{baseUrl}/posts`, `{baseUrl}/users`).
- No request body required for GET.

## Steps

1. Send a GET request to the list/collection endpoint.
2. Do not include a request body.
3. Include required headers (e.g. `Accept: application/json`) if applicable.
4. Capture the response status code and body.

## Expected Result

- Response status code is **200**.
- Test passes when the status code assertion succeeds; no requirement on response body content for this test.

## Automation Notes

- Assert only on `response.getStatusCode() == 200`.
- Use base URL and path from config/environment variables.

---

## Traceability (automation)

| Item | Location |
|------|----------|
| **Test Case ID** | API-GET-LIST-001 |
| **Automated test (source)** | `GetListReturns200Test.java` (same folder) |
| **Project path** | `java-rest-api-automation/rest-api-tests/get-list/get_list_returns_200/` |
