# Test Case: POST Without Auth Returns 401 When Protected

| Attribute | Value |
|-----------|--------|
| **Test Case ID** | API-POST-007 |
| **Priority** | High |
| **Type** | REST API, Security, Negative |

## Objective

Verify that a POST request to the create endpoint without authentication returns HTTP 401 Unauthorized when the endpoint is protected.

## Preconditions

- Create endpoint requires authentication (GoREST POST /users requires Bearer token).
- Base URL and endpoint are configured.
- No Authorization header sent.

## Test Data

- Valid request body (so the only failure is auth).
- No Authorization header.

## Steps

1. Send a POST request with valid body but no Authorization header.
2. Capture the response status code.
3. Verify status is 401.

## Expected Result

- Response status code is **401 Unauthorized**.
- No resource is created.

## Postman Automation Notes

- **Tests tab:** `pm.test("Status is 401", () => pm.response.to.have.status(401));`
- Ensure no auth header. GoREST POST /users requires Bearer token.

---

## Traceability

| Item | Location |
|------|----------|
| **Test Case ID** | API-POST-007 |
| **Postman request** | POST Create > Without Auth Returns 401 |
| **Project path** | `postman-api-collection/collection/POST-Create/post_without_auth_returns_401/` |
| **Java equivalent** | `java-rest-api-automation/rest-api-tests/post-create/post_without_auth_returns_401_when_protected/` |
