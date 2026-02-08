# Test Case: GET Resource by Valid ID Returns Expected Fields and Types

| Attribute | Value |
|-----------|--------|
| **Test Case ID** | API-GET-SINGLE-002 |
| **Priority** | High |
| **Type** | REST API, Functional |

## Objective

Verify that the GET resource-by-ID response contains the expected fields and that their types conform to the API contract.

## Preconditions

- Resource-by-ID endpoint returns 200 for a valid ID.
- API contract defines required fields (e.g. GoREST: id, name, email, gender, status).
- Valid resource ID is available.

## Test Data

- Valid resource ID.
- Expected fields (GoREST Users): `id` (number), `name` (string), `email` (string), `gender` (string), `status` (string).

## Steps

1. Send a GET request to the resource-by-ID endpoint with a valid ID.
2. Capture the response body.
3. Verify presence and type of required fields.

## Expected Result

- Response status code is **200**.
- All required fields are present.
- Field types match the contract (id: number, name: string, email: string, etc.).

## Postman Automation Notes

- **Tests tab:**
  ```
  pm.test("Status is 200", () => pm.response.to.have.status(200));
  pm.test("Response has expected fields and types", () => {
    const json = pm.response.json();
    pm.expect(json).to.have.property('id').that.is.a('number');
    pm.expect(json).to.have.property('name').that.is.a('string');
    pm.expect(json).to.have.property('email').that.is.a('string');
    pm.expect(json).to.have.property('gender').that.is.a('string');
    pm.expect(json).to.have.property('status').that.is.a('string');
  });
  ```

---

## Traceability

| Item | Location |
|------|----------|
| **Test Case ID** | API-GET-SINGLE-002 |
| **Postman request** | GET Single > Expected Fields and Types |
| **Project path** | `postman-api-collection/collection/GET-Single/get_by_valid_id_returns_expected_fields_and_types/` |
| **Java equivalent** | `java-rest-api-automation/rest-api-tests/get-single/get_by_valid_id_returns_expected_fields_and_types/` |
