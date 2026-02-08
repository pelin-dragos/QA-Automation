# Test Case: POST Duplicate Returns 409 or 400 When Applicable

| Attribute | Value |
|-----------|--------|
| **Test Case ID** | API-POST-009 |
| **Priority** | Medium |
| **Type** | REST API, Negative |

## Objective

Verify that when the API enforces a uniqueness constraint (e.g. unique email), sending a second POST with the same unique value returns 409 or 400 and does not create a duplicate.

## Preconditions

- Create endpoint has a uniqueness constraint (GoREST: email must be unique).
- First POST with given unique value succeeds.
- Same unique value sent again is considered duplicate.

## Test Data

- Same email used in two POST requests.
- Body: `{"name":"Test","email":"duplicate@example.com","gender":"male","status":"active"}`.
- Run twice; second request should fail.

## Steps

1. Send first POST with valid body and unique email; verify 201.
2. Send second POST with same email (and other required fields).
3. Capture the response status code and body of the second request.
4. Verify status is 409 or 400.

## Expected Result

- Second response status code is **409** or **400**.
- Response body contains error message indicating duplicate or constraint violation.
- Only one resource exists for that unique value.

## Postman Automation Notes

- **Tests tab (for second request):** Accept 409, 400 or 422 (GoREST returns 422 for validation).
  ```
  pm.test("Status is 409, 400 or 422", () => pm.expect([409, 400, 422]).to.include(pm.response.code));
  pm.test("Duplicate error message", () => {
    const body = pm.response.json();
    pm.expect(JSON.stringify(body)).to.match(/duplicate|already exists|email|unique/i);
  });
  ```
- Use a fixed email for both requests, or run as chained requests (first POST, then same body again).

---

## Traceability

| Item | Location |
|------|----------|
| **Test Case ID** | API-POST-009 |
| **Postman request** | POST Create > Duplicate Returns 409/400 |
| **Project path** | `postman-api-collection/collection/POST-Create/post_duplicate_returns_409_or_400/` |
| **Java equivalent** | `java-rest-api-automation/rest-api-tests/post-create/post_duplicate_returns_409_or_400_when_applicable/` |
