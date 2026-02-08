# Test Case: PATCH with Invalid Field Value Returns 400 with Validation Message

| Attribute | Value |
|-----------|--------|
| **Test Case ID** | API-PATCH-004 |
| **Priority** | Medium |
| **Type** | REST API, Negative |

## Objective

Verify that a PATCH request with an invalid field value returns HTTP 400 and validation message.

## Preconditions

- Valid resource ID exists. Auth available.
- API validates field values (e.g. status must be active/inactive).

## Test Data

- Body: `{"status":"invalid_status"}` or invalid enum value.

## Steps

1. Send a PATCH request with invalid field value.
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
| **Test Case ID** | API-PATCH-004 |
| **Postman request** | PATCH Update > Invalid Field Value |
| **Project path** | `postman-api-collection/collection/PATCH-Update/patch_invalid_field_value_returns_400/` |
| **Java equivalent** | `java-rest-api-automation/rest-api-tests/patch-update/patch_invalid_field_value_returns_400_with_validation_message/` |
