# Test Case: Endpoint Requiring Auth Returns 401 Without Token

| Attribute | Value |
|-----------|--------|
| **Test Case ID** | API-AUTH-001 |
| **Priority** | High |
| **Type** | REST API, Security |

## Objective

Verify that an endpoint that requires authentication returns HTTP 401 Unauthorized when the request is sent without an Authorization header.

## Preconditions

- At least one protected endpoint is configured (e.g. POST `/users`).
- Base URL and endpoint are configured.

## Test Data

- No Authorization header.
- Valid path and method for the endpoint.

## Steps

1. Send a request to the protected endpoint without an Authorization header.
2. Capture the response status code and body.
3. Verify response is 401.

## Expected Result

- Response status code is **401 Unauthorized**.
- No resource data is returned. Body may contain an error message.

## Postman Automation Notes

- **Tests tab:** `pm.test("Status is 401", () => pm.response.to.have.status(401));`
- Ensure no `Authorization` header is set in the request. Use a separate request or disable auth for this request.

---

## Traceability

| Item | Location |
|------|----------|
| **Test Case ID** | API-AUTH-001 |
| **Postman request** | Auth > No Token Returns 401 |
| **Project path** | `postman-api-collection/collection/Auth/no_token_returns_401/` |
| **Java equivalent** | `java-rest-api-automation/rest-api-tests/auth/endpoint_requiring_auth_returns_401_without_or_invalid_token/` |
