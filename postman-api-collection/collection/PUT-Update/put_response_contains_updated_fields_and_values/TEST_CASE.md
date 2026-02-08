# Test Case: PUT Response Body Contains Updated Fields and Values

| Attribute | Value |
|-----------|--------|
| **Test Case ID** | API-PUT-002 |
| **Priority** | High |
| **Type** | REST API, Functional |

## Objective

Verify that the PUT response body contains the updated fields and values that were sent in the request.

## Preconditions

- Resource exists. Valid ID and auth available.
- Base URL and endpoint configured.

## Test Data

- Body with known values: `{"name":"ExactMatch","email":"exact@example.com","gender":"female","status":"active"}`

## Steps

1. Send a PUT request with known field values.
2. Capture the response body.
3. Verify response matches or reflects sent data.

## Expected Result

- Response status code is **200**.
- Response body contains updated values matching the request (name, email, gender, status).

## Postman Automation Notes

- **Tests tab:**
  ```
  pm.test("Status is 200", () => pm.response.to.have.status(200));
  pm.test("Response matches sent data", () => {
    const json = pm.response.json();
    pm.expect(json.name).to.eql('ExactMatch');
    pm.expect(json.status).to.eql('active');
  });
  ```

---

## Traceability

| Item | Location |
|------|----------|
| **Test Case ID** | API-PUT-002 |
| **Postman request** | PUT Update > Response Contains Updated Fields |
| **Project path** | `postman-api-collection/collection/PUT-Update/put_response_contains_updated_fields_and_values/` |
| **Java equivalent** | `java-rest-api-automation/rest-api-tests/put-update/put_response_contains_updated_fields_and_values/` |
