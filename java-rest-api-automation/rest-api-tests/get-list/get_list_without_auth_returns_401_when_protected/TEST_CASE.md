# Test Case: GET List Without Auth Returns 401 When Endpoint Is Protected

| Attribute | Value |
|-----------|--------|
| **Test Case ID** | API-GET-LIST-007 |
| **Priority** | High |
| **Type** | REST API, Security / Negative |

## Objective

Verify that when the list endpoint requires authentication, a GET request sent without credentials (or without a valid token) returns HTTP 401 Unauthorized.

## Preconditions

- List endpoint is configured to require authentication (e.g. Bearer token, API key).
- Base URL and endpoint are configured.
- Test does not send any auth header (or sends an invalid/expired token).

## Test Data

- Endpoint: list endpoint (same as for authenticated flow).
- Headers: no `Authorization` header, or `Authorization: Bearer invalid_token`.

## Steps

1. Send a GET request to the list endpoint without including valid authentication (omit auth header or use invalid token).
2. Capture the response status code and, if applicable, response body.
3. Do not send credentials or valid token.

## Expected Result

- Response status code is **401 Unauthorized**.
- Response may include a body with error message or code (e.g. "Unauthorized", "Invalid token"); no list data is returned.
- Test is not applicable if the endpoint is public; in that case, skip or document as N/A.

## Automation Notes

- Assert on status 401. Optionally assert on presence of error message in body.
- If endpoint is not protected, mark test as skipped or document precondition; use tags/annotations for optional auth tests.

---

## Traceability (automation)

| Item | Location |
|------|----------|
| **Test Case ID** | API-GET-LIST-007 |
| **Automated test (source)** | `GetListWithoutAuthReturns401WhenProtectedTest.java` (same folder) |
| **Project path** | `java-rest-api-automation/rest-api-tests/get-list/get_list_without_auth_returns_401_when_protected/` |
