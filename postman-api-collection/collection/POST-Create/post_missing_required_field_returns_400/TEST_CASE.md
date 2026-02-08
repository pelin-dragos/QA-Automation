# Test Case: POST with Missing Required Field Returns 400 with Validation Message

| Attribute | Value |
|-----------|--------|
| **Test Case ID** | API-POST-003 |
| **Priority** | High |
| **Type** | REST API, Negative |

## Objective

Verify that a POST request with one or more required fields missing returns HTTP 400 (or 422) and a validation error message.

## Preconditions

- Create endpoint validates required fields and returns 4xx with body on validation failure.
- API contract defines required fields (e.g. GoREST: name, email, gender, status).

## Test Data

- Body with one required field omitted: e.g. `{"email":"test@example.com","gender":"male","status":"active"}` (missing `name`).
- Or omit `email` with other fields present.

## Steps

1. Build a request body that omits one required field.
2. Send a POST request to the create endpoint.
3. Capture the response status code and body.
4. Verify error message references validation or the missing field.

## Expected Result

- Response status code is **400** or **422**.
- Response body contains an error message or validation details.
- No resource is created.

## Postman Automation Notes

- **Tests tab:**
  ```
  pm.test("Status is 400 or 422", () => pm.expect([400, 422]).to.include(pm.response.code));
  pm.test("Error message present", () => {
    const body = pm.response.json();
    pm.expect(body).to.have.property('message').or.have.property('field');
  });
  ```

---

## Traceability

| Item | Location |
|------|----------|
| **Test Case ID** | API-POST-003 |
| **Postman request** | POST Create > Missing Required Field |
| **Project path** | `postman-api-collection/collection/POST-Create/post_missing_required_field_returns_400/` |
| **Java equivalent** | `java-rest-api-automation/rest-api-tests/post-create/post_missing_required_field_returns_400_with_validation_message/` |
