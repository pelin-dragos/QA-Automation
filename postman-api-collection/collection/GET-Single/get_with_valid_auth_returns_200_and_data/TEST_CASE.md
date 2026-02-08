# Test Case: GET Resource With Valid Auth Returns 200 and Data

| Attribute | Value |
|-----------|--------|
| **Test Case ID** | API-GET-SINGLE-006 |
| **Priority** | High |
| **Type** | REST API, Functional, Security |

## Objective

Verify that when the resource-by-ID endpoint requires authentication, a GET request with valid credentials returns HTTP 200 and the correct resource data.

## Preconditions

- Resource-by-ID endpoint requires authentication (or test validates 200 with auth).
- Valid token from environment.
- Valid resource ID available.

## Test Data

- Endpoint: `GET {{base_url}}/users/{{user_id}}`.
- Auth: `Authorization: Bearer {{auth_token}}`.

## Steps

1. Send a GET request with valid auth header.
2. Capture the response status code and body.
3. Verify status 200 and correct resource in body.

## Expected Result

- Response status code is **200**.
- Response body contains the correct resource with expected fields.
- No 401 or 403.

## Postman Automation Notes

- **Tests tab:**
  ```
  pm.test("Status is 200", () => pm.response.to.have.status(200));
  pm.test("Body contains resource", () => {
    const json = pm.response.json();
    pm.expect(json).to.have.property('id');
    pm.expect(json).to.have.property('name');
  });
  ```
- Use `{{auth_token}}` from environment.

---

## Traceability

| Item | Location |
|------|----------|
| **Test Case ID** | API-GET-SINGLE-006 |
| **Postman request** | GET Single > With Valid Auth Returns 200 |
| **Project path** | `postman-api-collection/collection/GET-Single/get_with_valid_auth_returns_200_and_data/` |
| **Java equivalent** | `java-rest-api-automation/rest-api-tests/get-single/get_by_id_with_valid_auth_returns_200_and_data/` |
