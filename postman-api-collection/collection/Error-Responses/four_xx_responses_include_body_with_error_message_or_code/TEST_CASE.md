# Test Case: 4xx Responses Include Body with Error Message or Code

| Attribute | Value |
|-----------|--------|
| **Test Case ID** | API-ERROR-001 |
| **Priority** | Medium |
| **Type** | REST API, Functional |

## Objective

Verify that when the API returns a 4xx client error (e.g. 400, 401, 404, 422), the response includes a body with an error message or error code so that clients can display or log a meaningful reason for the failure.

## Preconditions

- API returns 4xx for at least one scenario (e.g. validation failure, not found, unauthorized).
- API contract states that 4xx responses include a body with message/code.

## Test Data

- A request that triggers a 4xx (e.g. GET with non-existent ID for 404, or POST with invalid body for 400).
- Expected: response body is non-empty and contains message or code field.

## Steps

1. Send a request that is expected to return a 4xx.
2. Capture the response status code and body.
3. Verify the response body is present and contains error message or code.

## Expected Result

- Response status is 4xx.
- Response body is present and parseable (e.g. JSON).
- Body contains an error message and/or error code.

## Postman Automation Notes

- **Tests tab** (use on any 4xx request, e.g. GET /users/999999999):
  ```
  pm.test("Status is 4xx", () => pm.expect(pm.response.code).to.be.within(400, 499));
  pm.test("Body contains error message or code", () => {
    const body = pm.response.json();
    pm.expect(body).to.have.property('message').or.have.property('error').or.have.property('code');
  });
  ```
- Can reuse requests from other folders (e.g. GET 404, POST 400).

---

## Traceability

| Item | Location |
|------|----------|
| **Test Case ID** | API-ERROR-001 |
| **Postman request** | Error Responses > 4xx Includes Error Message |
| **Project path** | `postman-api-collection/collection/Error-Responses/four_xx_responses_include_body_with_error_message_or_code/` |
| **Java equivalent** | `java-rest-api-automation/rest-api-tests/error-responses/four_xx_responses_include_body_with_error_message_or_code/` |
