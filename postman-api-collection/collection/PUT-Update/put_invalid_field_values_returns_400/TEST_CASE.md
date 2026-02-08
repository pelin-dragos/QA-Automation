# Test Case: PUT with Invalid Field Values Returns 400 with Validation Message

| Attribute | Value |
|-----------|--------|
| **Test Case ID** | API-PUT-006 |
| **Priority** | Medium |
| **Type** | REST API, Negative |

## Objective

Verify that a PUT request with invalid field values (e.g. invalid enum) returns HTTP 400 and validation message.

## Preconditions

- Valid resource ID exists. Auth available.
- API validates field formats (e.g. gender: male/female, status: active/inactive).

## Test Data

- Body with invalid value: `{"name":"Test","email":"test@example.com","gender":"invalid","status":"active"}`

## Steps

1. Send a PUT request with invalid field value.
2. Capture the response status code and body.
3. Verify status 400 and validation message.

## Expected Result

- Response status code is **400** (or 422).
- Response body contains validation error. No resource is updated.

## Postman Automation Notes

- **Tests tab:** Accept 400, 422 or 404 (404 when user_id invalid).
  ```
  pm.test("Status is 400, 422 or 404", () => pm.expect([400, 422, 404]).to.include(pm.response.code));
  ```

---

## Traceability

| Item | Location |
|------|----------|
| **Test Case ID** | API-PUT-006 |
| **Postman request** | PUT Update > Invalid Field Values |
| **Project path** | `postman-api-collection/collection/PUT-Update/put_invalid_field_values_returns_400/` |
| **Java equivalent** | `java-rest-api-automation/rest-api-tests/put-update/put_invalid_field_values_returns_400_with_validation_message/` |
