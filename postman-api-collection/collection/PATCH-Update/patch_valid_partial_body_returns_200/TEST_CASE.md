# Test Case: PATCH with Valid Partial Body and Existing ID Returns 200 and Updated Resource

| Attribute | Value |
|-----------|--------|
| **Test Case ID** | API-PATCH-001 |
| **Priority** | High |
| **Type** | REST API, Functional |

## Objective

Verify that a PATCH request with a valid partial body and existing ID returns HTTP 200 and only the sent fields are updated.

## Preconditions

- Resource exists. Valid ID and auth available.
- Base URL and endpoint configured.

## Test Data

- Endpoint: `PATCH {{base_url}}/users/{{user_id}}`
- Partial body: `{"status":"inactive"}` or `{"name":"New Name"}`

## Steps

1. Send a PATCH request with partial body (one or few fields).
2. Capture the response status code and body.
3. Verify status 200 and only sent field(s) updated.

## Expected Result

- Response status code is **200**.
- Response body reflects the update. Only sent fields changed; others unchanged.

## Postman Automation Notes

- **Tests tab:**
  ```
  pm.test("Status is 200", () => pm.response.to.have.status(200));
  pm.test("Updated field reflected", () => {
    const json = pm.response.json();
    pm.expect(json.status).to.eql('inactive');
  });
  ```

---

## Traceability

| Item | Location |
|------|----------|
| **Test Case ID** | API-PATCH-001 |
| **Postman request** | PATCH Update > Valid Partial Body Returns 200 |
| **Project path** | `postman-api-collection/collection/PATCH-Update/patch_valid_partial_body_returns_200/` |
| **Java equivalent** | `java-rest-api-automation/rest-api-tests/patch-update/patch_valid_partial_body_existing_id_returns_200_and_updated_resource/` |
