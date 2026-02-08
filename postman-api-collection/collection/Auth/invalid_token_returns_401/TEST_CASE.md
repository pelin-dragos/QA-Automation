# Test Case: Endpoint Requiring Auth Returns 401 With Invalid Token

| Attribute | Value |
|-----------|--------|
| **Test Case ID** | API-AUTH-001b |
| **Priority** | High |
| **Type** | REST API, Security |

## Objective

Verify that an endpoint that requires authentication returns HTTP 401 Unauthorized when the request is sent with an invalid or malformed token.

## Preconditions

- Protected endpoint (e.g. POST `/users` on GoREST) is configured.
- Base URL and endpoint are configured.

## Test Data

- Invalid token: `Authorization: Bearer invalid_token_12345` or malformed value.
- Valid path and method for the endpoint.

## Steps

1. Send a request to the protected endpoint with an invalid token in the Authorization header.
2. Capture the response status code and body.
3. Verify response is 401.

## Expected Result

- Response status code is **401 Unauthorized**.
- No resource data is returned. Body may contain an error message.

## Postman Automation Notes

- **Tests tab:** `pm.test("Status is 401", () => pm.response.to.have.status(401));`
- Set `Authorization: Bearer invalid_token_12345` in headers for this request only.

---

## Traceability

| Item | Location |
|------|----------|
| **Test Case ID** | API-AUTH-001b |
| **Postman request** | Auth > Invalid Token Returns 401 |
| **Project path** | `postman-api-collection/collection/Auth/invalid_token_returns_401/` |
