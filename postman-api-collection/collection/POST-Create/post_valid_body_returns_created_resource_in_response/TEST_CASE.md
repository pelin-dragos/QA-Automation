# Test Case: POST with Valid Body Returns Created Resource in Response

| Attribute | Value |
|-----------|--------|
| **Test Case ID** | API-POST-002 |
| **Priority** | High |
| **Type** | REST API, Functional |

## Objective

Verify that the POST create response body contains the created resource (assigned ID and submitted or computed fields).

## Preconditions

- Create endpoint returns 201 for valid payload.
- API returns the created resource in the response body.
- Valid auth token available.

## Test Data

- Valid body: `{"name":"Test User","email":"test.{{$timestamp}}@example.com","gender":"male","status":"active"}`

## Steps

1. Send a POST request with valid body.
2. Capture the response body.
3. Verify response contains `id` and key submitted fields (name, email, etc.).

## Expected Result

- Response status code is **201**.
- Response body includes `id` and key fields (name, email, gender, status).
- Submitted values are reflected in the response.

## Postman Automation Notes

- **Tests tab:**
  ```
  pm.test("Status is 201", () => pm.response.to.have.status(201));
  pm.test("Response contains created resource", () => {
    const json = pm.response.json();
    pm.expect(json).to.have.property('id');
    pm.expect(json).to.have.property('name');
    pm.expect(json).to.have.property('email');
    pm.expect(json).to.have.property('gender');
    pm.expect(json).to.have.property('status');
  });
  pm.collectionVariables.set("created_user_id", pm.response.json().id);
  ```
- Save `created_user_id` for subsequent PUT/PATCH/DELETE tests.

---

## Traceability

| Item | Location |
|------|----------|
| **Test Case ID** | API-POST-002 |
| **Postman request** | POST Create > Returns Created Resource |
| **Project path** | `postman-api-collection/collection/POST-Create/post_valid_body_returns_created_resource_in_response/` |
| **Java equivalent** | `java-rest-api-automation/rest-api-tests/post-create/post_valid_body_returns_created_resource_in_response/` |
