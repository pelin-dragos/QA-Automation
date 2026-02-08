# Test Case: POST with Wrong Content-Type Returns 415 or 400

| Attribute | Value |
|-----------|--------|
| **Test Case ID** | API-POST-006 |
| **Priority** | Medium |
| **Type** | REST API, Negative |

## Objective

Verify that a POST request with an unsupported Content-Type (e.g. text/plain instead of application/json) returns 415 or 400 and does not create a resource.

## Preconditions

- Create endpoint expects application/json and rejects others.
- Base URL and endpoint are configured.

## Test Data

- Valid JSON body.
- Header: `Content-Type: text/plain`.

## Steps

1. Build a valid JSON request body.
2. Send a POST request with Content-Type: text/plain.
3. Capture the response status code.

## Expected Result

- Response status code is **415** or **400**.
- No resource is created.

## Postman Automation Notes

- **Tests tab:** Accept 415, 400 or 422 (GoREST returns 422).
  ```
  pm.test("Status is 415, 400 or 422", () => pm.expect([415, 400, 422]).to.include(pm.response.code));
  ```
- Override Content-Type to `text/plain` for this request only.

---

## Traceability

| Item | Location |
|------|----------|
| **Test Case ID** | API-POST-006 |
| **Postman request** | POST Create > Wrong Content-Type |
| **Project path** | `postman-api-collection/collection/POST-Create/post_wrong_content_type_returns_415_or_400/` |
| **Java equivalent** | `java-rest-api-automation/rest-api-tests/post-create/post_wrong_content_type_returns_415_or_400/` |
