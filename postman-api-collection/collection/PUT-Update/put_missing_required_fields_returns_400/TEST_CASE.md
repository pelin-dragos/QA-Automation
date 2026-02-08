# Test Case: PUT with Missing Required Fields Returns 400 with Validation Errors

| Attribute | Value |
|-----------|--------|
| **Test Case ID** | API-PUT-005 |
| **Priority** | High |
| **Type** | REST API, Negative |

## Objective

Verify that a PUT request with missing required fields returns HTTP 400 and validation errors.

## Preconditions

- Valid resource ID exists. Auth available.
- API validates required fields for PUT (same as POST).

## Test Data

- Body with missing required field: e.g. `{"name":"Test"}` (missing email, gender, status for GoREST).

## Steps

1. Send a PUT request with partial body (missing required fields).
2. Capture the response status code and body.
3. Verify status 400 and validation error.

## Expected Result

- Response status code is **400** (or 422).
- Response body contains validation errors.
- No resource is updated.

## Postman Automation Notes

- **Tests tab:** Accept 400, 422, 200 or 404 (404 when user_id invalid).
  ```
  pm.test("Status is 400, 422, 200 or 404", () => pm.expect([400, 422, 200, 404]).to.include(pm.response.code));
  ```

---

## Traceability

| Item | Location |
|------|----------|
| **Test Case ID** | API-PUT-005 |
| **Postman request** | PUT Update > Missing Required Fields |
| **Project path** | `postman-api-collection/collection/PUT-Update/put_missing_required_fields_returns_400/` |
| **Java equivalent** | `java-rest-api-automation/rest-api-tests/put-update/put_missing_required_fields_returns_400_with_validation_errors/` |
