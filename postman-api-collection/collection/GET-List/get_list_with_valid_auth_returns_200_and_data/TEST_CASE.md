# Test Case: GET List With Valid Auth Returns 200 and Data

| Attribute | Value |
|-----------|--------|
| **Test Case ID** | API-GET-LIST-008 |
| **Priority** | High |
| **Type** | REST API, Functional, Security |

## Objective

Verify that when the list endpoint requires authentication, a GET request with valid credentials returns HTTP 200 and the expected list data.

## Preconditions

- List endpoint requires authentication (or test validates 200 with auth).
- Valid token is available from environment.
- Base URL and endpoint are configured.

## Test Data

- Endpoint: `GET {{base_url}}/users`.
- Auth: `Authorization: Bearer {{auth_token}}`.

## Steps

1. Send a GET request to the list endpoint with valid auth header.
2. Capture the response status code and body.
3. Verify response is successful and contains expected structure.

## Expected Result

- Response status code is **200**.
- Response body contains the list/collection (valid JSON array).
- No 401 or 403.

## Postman Automation Notes

- **Tests tab:**
  ```
  pm.test("Status is 200", () => pm.response.to.have.status(200));
  pm.test("Response has array", () => {
    const json = pm.response.json();
    pm.expect(json).to.be.an('array');
  });
  ```
- Use `{{auth_token}}` from environment. Never hardcode.

---

## Traceability

| Item | Location |
|------|----------|
| **Test Case ID** | API-GET-LIST-008 |
| **Postman request** | GET List > With Valid Auth Returns 200 |
| **Project path** | `postman-api-collection/collection/GET-List/get_list_with_valid_auth_returns_200_and_data/` |
| **Java equivalent** | `java-rest-api-automation/rest-api-tests/get-list/get_list_with_valid_auth_returns_200_and_data/` |
