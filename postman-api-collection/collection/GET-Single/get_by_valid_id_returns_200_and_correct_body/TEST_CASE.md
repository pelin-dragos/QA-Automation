# Test Case: GET Resource by Valid ID Returns 200 and Correct Body

| Attribute | Value |
|-----------|--------|
| **Test Case ID** | API-GET-SINGLE-001 |
| **Priority** | High |
| **Type** | REST API, Functional |

## Objective

Verify that a GET request to the resource-by-ID endpoint with a valid existing ID returns HTTP 200 and the correct resource representation in the response body.

## Preconditions

- Base URL and resource-by-ID endpoint are configured.
- At least one resource exists; valid ID available (from list or setup).
- Use `{{user_id}}` from collection variable or previous request.

## Test Data

- Valid resource ID: e.g. first ID from GET list, or created in prior request.
- Endpoint: `GET {{base_url}}/users/{{user_id}}`.

## Steps

1. Obtain a valid resource ID (from collection variable or setup request).
2. Send a GET request to the resource-by-ID endpoint with that ID.
3. Capture the response status code and body.
4. Verify body represents a single resource (JSON object), not an array.

## Expected Result

- Response status code is **200**.
- Response body is a single resource object with the requested ID and expected fields.
- ID in path matches ID in body.

## Postman Automation Notes

- **Setup:** Collection includes Setup request that fetches user_id from GET /users (when run in isolation).
- **Pre-request:** Set `user_id` from previous response if needed: `pm.collectionVariables.set("user_id", pm.response.json()[0].id);`
- **Tests tab:**
  ```
  pm.test("Status is 200", () => pm.response.to.have.status(200));
  pm.test("Body is single resource object", () => {
    const json = pm.response.json();
    pm.expect(json).to.be.an('object');
    pm.expect(json).to.have.property('id');
  });
  ```

---

## Traceability

| Item | Location |
|------|----------|
| **Test Case ID** | API-GET-SINGLE-001 |
| **Postman request** | GET Single > By Valid ID Returns 200 |
| **Project path** | `postman-api-collection/collection/GET-Single/get_by_valid_id_returns_200_and_correct_body/` |
| **Java equivalent** | `java-rest-api-automation/rest-api-tests/get-single/get_by_valid_id_returns_200_and_correct_body/` |
