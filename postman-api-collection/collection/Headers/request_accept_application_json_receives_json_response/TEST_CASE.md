# Test Case: Request with Accept application/json Receives JSON Response

| Attribute | Value |
|-----------|--------|
| **Test Case ID** | API-HEADERS-001 |
| **Priority** | Medium |
| **Type** | REST API, Functional |

## Objective

Verify that when a request includes the header `Accept: application/json`, the API responds with a JSON body and typically `Content-Type: application/json` in the response.

## Preconditions

- Target endpoint returns a response body (e.g. GET list or GET by ID).
- API supports JSON representation.
- Base URL and endpoint configured.

## Test Data

- Header: `Accept: application/json`.
- Any valid request (e.g. GET list or GET by ID) that returns data.

## Steps

1. Send a request with header `Accept: application/json`.
2. Capture the response status code, Content-Type header, and body.
3. Parse the response body as JSON and verify it is valid.

## Expected Result

- Response is successful (e.g. 200).
- Response header `Content-Type` includes `application/json`.
- Response body is valid JSON (parseable).

## Postman Automation Notes

- **Tests tab:**
  ```
  pm.test("Content-Type is application/json", () => {
    pm.expect(pm.response.headers.get("Content-Type")).to.include("application/json");
  });
  pm.test("Response is valid JSON", () => {
    pm.response.json();
  });
  ```
- Use any success request (e.g. GET list).

---

## Traceability

| Item | Location |
|------|----------|
| **Test Case ID** | API-HEADERS-001 |
| **Postman request** | Headers > Accept application/json Receives JSON |
| **Project path** | `postman-api-collection/collection/Headers/request_accept_application_json_receives_json_response/` |
| **Java equivalent** | `java-rest-api-automation/rest-api-tests/headers/request_accept_application_json_receives_json_response/` |
