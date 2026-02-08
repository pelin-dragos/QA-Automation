# Test Case: PUT with Valid Body and Existing ID Returns 200 and Updated Resource

| Attribute | Value |
|-----------|--------|
| **Test Case ID** | API-PUT-001 |
| **Priority** | High |
| **Type** | REST API, Functional |

## Objective

Verify that a PUT request with a valid body and existing ID returns HTTP 200 and the updated resource in the response.

## Preconditions

- Resource exists (created in setup or previous request).
- Valid resource ID available (`{{user_id}}`).
- Valid auth token. Base URL configured.

## Test Data

- Endpoint: `PUT {{base_url}}/users/{{user_id}}`
- Body: `{"name":"Updated Name","email":"updated.{{$timestamp}}@example.com","gender":"male","status":"inactive"}`

## Steps

1. Send a PUT request with full body and valid auth.
2. Capture the response status code and body.
3. Verify status 200 and body contains updated fields.

## Expected Result

- Response status code is **200**.
- Response body contains the updated resource with new values.
- No 404, 401, or 403.

## Postman Automation Notes

- **Tests tab:**
  ```
  pm.test("Status is 200", () => pm.response.to.have.status(200));
  pm.test("Response contains updated resource", () => {
    const json = pm.response.json();
    pm.expect(json).to.have.property('name', 'Updated Name');
  });
  ```

---

## Traceability

| Item | Location |
|------|----------|
| **Test Case ID** | API-PUT-001 |
| **Postman request** | PUT Update > Valid Body Returns 200 |
| **Project path** | `postman-api-collection/collection/PUT-Update/put_valid_body_existing_id_returns_200/` |
| **Java equivalent** | `java-rest-api-automation/rest-api-tests/put-update/put_valid_body_existing_id_returns_200_and_updated_resource/` |
