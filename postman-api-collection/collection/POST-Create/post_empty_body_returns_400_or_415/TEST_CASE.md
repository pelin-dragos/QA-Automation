# Test Case: POST with Empty Body Returns 400 or 415 When Body Is Required

| Attribute | Value |
|-----------|--------|
| **Test Case ID** | API-POST-005 |
| **Priority** | Medium |
| **Type** | REST API, Negative |

## Objective

Verify that a POST request with an empty body to an endpoint that requires a body returns 400 or 415 and does not create a resource.

## Preconditions

- Create endpoint requires a request body.
- Base URL and endpoint are configured.

## Test Data

- Empty body: `{}` with Content-Type: `application/json`.

## Steps

1. Send a POST request with an empty JSON body `{}`.
2. Set Content-Type: application/json.
3. Capture the response status code and body.

## Expected Result

- Response status code is **400** or **415**.
- No resource is created.

## Postman Automation Notes

- **Tests tab:** Accept 400, 415 or 422 (GoREST returns 422).
  ```
  pm.test("Status is 400, 415 or 422", () => pm.expect([400, 415, 422]).to.include(pm.response.code));
  ```
- Body: `{}`

---

## Traceability

| Item | Location |
|------|----------|
| **Test Case ID** | API-POST-005 |
| **Postman request** | POST Create > Empty Body |
| **Project path** | `postman-api-collection/collection/POST-Create/post_empty_body_returns_400_or_415/` |
| **Java equivalent** | `java-rest-api-automation/rest-api-tests/post-create/post_empty_body_returns_400_or_415_when_required/` |
