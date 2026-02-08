# Test Case: Response Includes Content-Type application/json

| Attribute | Value |
|-----------|--------|
| **Test Case ID** | API-HEADERS-002 |
| **Priority** | Medium |
| **Type** | REST API, Functional |

## Objective

Verify that for endpoints that return a JSON body, the response includes a `Content-Type` header set to `application/json` (or with charset).

## Preconditions

- At least one endpoint that returns a body (e.g. GET list, GET by ID) is configured.
- API returns JSON for that endpoint.

## Test Data

- Endpoint that returns JSON (e.g. GET list or GET by ID with valid ID).

## Steps

1. Send a request to an endpoint that returns a JSON body.
2. Capture the response headers.
3. Verify the `Content-Type` header is present and indicates JSON.

## Expected Result

- Response includes a `Content-Type` header.
- Value contains `application/json` (exact or with charset).

## Postman Automation Notes

- **Tests tab:**
  ```
  pm.test("Content-Type includes application/json", () => {
    const ct = pm.response.headers.get("Content-Type");
    pm.expect(ct).to.be.a('string');
    pm.expect(ct).to.include("application/json");
  });
  ```

---

## Traceability

| Item | Location |
|------|----------|
| **Test Case ID** | API-HEADERS-002 |
| **Postman request** | Headers > Response Includes Content-Type |
| **Project path** | `postman-api-collection/collection/Headers/response_includes_content_type_application_json/` |
| **Java equivalent** | `java-rest-api-automation/rest-api-tests/headers/response_includes_content_type_application_json/` |
