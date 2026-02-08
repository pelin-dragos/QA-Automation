# Test Case: POST with Invalid Field Format Returns 400 with Validation Message

| Attribute | Value |
|-----------|--------|
| **Test Case ID** | API-POST-004 |
| **Priority** | High |
| **Type** | REST API, Negative |

## Objective

Verify that a POST request with an invalid format for a field (e.g. invalid email) returns HTTP 400 and a validation error message.

## Preconditions

- Create endpoint validates field formats and returns 4xx on validation failure.
- API contract defines format rules (e.g. email format, enum values).

## Test Data

- Body with invalid format: e.g. `{"name":"Test","email":"not-an-email","gender":"male","status":"active"}`.
- Or invalid `status`: `"invalid_status"`.

## Steps

1. Build a request body with one field set to invalid format.
2. Send a POST request to the create endpoint.
3. Capture the response status code and body.
4. Verify response indicates validation failure.

## Expected Result

- Response status code is **400** or **422**.
- Response body contains validation error message. No resource is created.

## Postman Automation Notes

- **Tests tab:**
  ```
  pm.test("Status is 400 or 422", () => pm.expect([400, 422]).to.include(pm.response.code));
  pm.test("Validation error in body", () => {
    const body = pm.response.json();
    pm.expect(JSON.stringify(body)).to.include('email').or.include('validation').or.include('invalid');
  });
  ```

---

## Traceability

| Item | Location |
|------|----------|
| **Test Case ID** | API-POST-004 |
| **Postman request** | POST Create > Invalid Field Format |
| **Project path** | `postman-api-collection/collection/POST-Create/post_invalid_field_format_returns_400/` |
| **Java equivalent** | `java-rest-api-automation/rest-api-tests/post-create/post_invalid_field_format_returns_400_with_validation_message/` |
