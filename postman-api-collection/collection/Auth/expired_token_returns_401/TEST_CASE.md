# Test Case: Expired Token Returns 401 With Appropriate Message

| Attribute | Value |
|-----------|--------|
| **Test Case ID** | API-AUTH-004 |
| **Priority** | Medium |
| **Type** | REST API, Security |

## Objective

Verify that when a request is sent with an expired authentication token, the API returns HTTP 401 Unauthorized and optionally an error message indicating token expiry.

## Preconditions

- Endpoint requires authentication.
- An expired token can be obtained (fixture or pre-generated). If not available, test may be skipped.
- API returns 401 for expired token.

## Test Data

- Expired token (from environment variable or fixture).
- Request to any protected endpoint.

## Steps

1. Obtain an expired token (if environment supports it).
2. Send a request to a protected endpoint with the expired token in the Authorization header.
3. Capture the response status code and body.
4. Optionally verify the error message indicates expiry.

## Expected Result

- Response status code is **401 Unauthorized**.
- Response body may contain a message or code indicating token expired. No protected data is returned.

## Postman Automation Notes

- **Tests tab:** `pm.test("Status is 401", () => pm.response.to.have.status(401));`
- Use `{{expired_token}}` in environment if available. Add to `demo.postman_environment.json` or `.env.example`. Skip when expired token cannot be produced (GoREST does not provide easy way to get expired token).

---

## Traceability

| Item | Location |
|------|----------|
| **Test Case ID** | API-AUTH-004 |
| **Postman request** | Auth > Expired Token Returns 401 |
| **Project path** | `postman-api-collection/collection/Auth/expired_token_returns_401/` |
| **Java equivalent** | `java-rest-api-automation/rest-api-tests/auth/expired_token_returns_401_with_appropriate_message/` |
