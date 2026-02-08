# Test Case: POST with Valid Body Returns 201 and Location Header

| Attribute | Value |
|-----------|--------|
| **Test Case ID** | API-POST-001 |
| **Priority** | High |
| **Type** | REST API, Functional |

## Objective

Verify that a POST request with a valid body to the create endpoint returns HTTP 201 Created and, when supported, a `Location` header pointing to the created resource.

## Preconditions

- Create endpoint (POST) is configured and accepts JSON.
- Request body conforms to API contract (all required fields, valid formats).
- Base URL and endpoint are configured.
- Valid auth token for GoREST.

## Test Data

- Body: `{"name":"Test User","email":"test.{{$timestamp}}@example.com","gender":"male","status":"active"}`
- Content-Type: `application/json`.

## Steps

1. Build a valid request body for resource creation.
2. Send a POST request with body and `Content-Type: application/json`.
3. Include `Authorization: Bearer {{auth_token}}`.
4. Capture response status code, headers, and body.
5. Verify `Location` header if API supports it.

## Expected Result

- Response status code is **201 Created** (or 200 per API contract).
- When supported, response includes `Location` header with URI for the created resource.
- Response body may contain the created resource.

## Postman Automation Notes

- **Tests tab:**
  ```
  pm.test("Status is 201", () => pm.response.to.have.status(201));
  pm.test("Location header present", () => {
    pm.expect(pm.response.headers.get("Location")).to.be.a('string');
  });
  ```
- Use `{{$timestamp}}` in email for uniqueness. GoREST may not return Location; adapt assertion.

---

## Traceability

| Item | Location |
|------|----------|
| **Test Case ID** | API-POST-001 |
| **Postman request** | POST Create > Valid Body Returns 201 |
| **Project path** | `postman-api-collection/collection/POST-Create/post_valid_body_returns_201_and_location_header/` |
| **Java equivalent** | `java-rest-api-automation/rest-api-tests/post-create/post_valid_body_returns_201_and_location_header/` |
