# Test Case: PUT with Valid Auth Updates Resource and Returns Success

| Attribute | Value |
|-----------|--------|
| **Test Case ID** | API-PUT-008 |
| **Priority** | High |
| **Type** | REST API, Functional, Security |

## Objective

Verify that a PUT request with valid authentication updates the resource and returns success status (200).

## Preconditions

- Resource exists. Valid ID and auth token available.
- Base URL and endpoint configured.

## Test Data

- Endpoint: `PUT {{base_url}}/users/{{user_id}}`
- Full valid body. Auth: Bearer {{auth_token}}.

## Steps

1. Send a PUT request with valid body and valid auth.
2. Capture the response status code and body.
3. Verify status 200 and resource updated.

## Expected Result

- Response status code is **200**.
- Resource is updated. Response body contains updated resource.
- No 401 or 403.

## Postman Automation Notes

- **Tests tab:**
  ```
  pm.test("Status is 200", () => pm.response.to.have.status(200));
  pm.test("Resource updated", () => {
    const json = pm.response.json();
    pm.expect(json).to.have.property('id');
  });
  ```

---

## Traceability

| Item | Location |
|------|----------|
| **Test Case ID** | API-PUT-008 |
| **Postman request** | PUT Update > With Valid Auth Updates |
| **Project path** | `postman-api-collection/collection/PUT-Update/put_with_valid_auth_updates_resource/` |
| **Java equivalent** | `java-rest-api-automation/rest-api-tests/put-update/put_with_valid_auth_updates_resource_and_returns_success/` |
