# Test Case: POST with Valid Auth Creates Resource and Returns Success

| Attribute | Value |
|-----------|--------|
| **Test Case ID** | API-POST-008 |
| **Priority** | High |
| **Type** | REST API, Functional, Security |

## Objective

Verify that a POST request with valid authentication creates the resource and returns a success status (201).

## Preconditions

- Create endpoint requires authentication.
- Valid token from environment.
- Base URL and endpoint are configured.

## Test Data

- Valid body: `{"name":"Test User","email":"test.{{$timestamp}}@example.com","gender":"male","status":"active"}`
- Auth: `Authorization: Bearer {{auth_token}}`.

## Steps

1. Send a POST request with valid body and valid auth header.
2. Capture the response status code and body.
3. Verify status 201 and resource created.

## Expected Result

- Response status code is **201**.
- Resource is created. Response body contains id and resource data.
- No 401 or 403.

## Postman Automation Notes

- **Tests tab:**
  ```
  pm.test("Status is 201", () => pm.response.to.have.status(201));
  pm.test("Resource created", () => {
    const json = pm.response.json();
    pm.expect(json).to.have.property('id');
  });
  ```

---

## Traceability

| Item | Location |
|------|----------|
| **Test Case ID** | API-POST-008 |
| **Postman request** | POST Create > With Valid Auth Creates |
| **Project path** | `postman-api-collection/collection/POST-Create/post_with_valid_auth_creates_resource/` |
| **Java equivalent** | `java-rest-api-automation/rest-api-tests/post-create/post_with_valid_auth_creates_resource_and_returns_success/` |
