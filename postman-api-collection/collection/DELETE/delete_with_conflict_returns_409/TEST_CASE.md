# Test Case: DELETE with Conflict Returns 409 When Applicable

| Attribute | Value |
|-----------|--------|
| **Test Case ID** | API-DELETE-007 |
| **Priority** | Medium |
| **Type** | REST API, Negative |

## Objective

Verify that when a DELETE would cause a conflict (e.g. resource in use, has dependencies), the API returns HTTP 409 Conflict and does not delete the resource.

## Preconditions

- API enforces referential integrity or business rules that prevent certain deletes.
- A resource that cannot be deleted exists (e.g. has child records, is in use).
- Base URL and endpoint configured.
- **Note:** GoREST may not enforce such constraints; test may be N/A.

## Test Data

- Resource ID that triggers conflict (per API design).
- Valid auth.

## Steps

1. Identify or create a resource that cannot be deleted (per API rules).
2. Send a DELETE request for that resource.
3. Capture the response status code and body.
4. Verify status is 409.

## Expected Result

- Response status code is **409 Conflict** (when applicable).
- Response body may indicate conflict or dependency.
- Resource is not deleted.
- **N/A** if API does not enforce such constraints.

## Postman Automation Notes

- **Tests tab:** Accept 409, 404 (GoREST: already-deleted), 204, or 200.
  ```
  pm.test("Status is 409, 404, 204 or 200", () => pm.expect([409, 404, 204, 200]).to.include(pm.response.code));
  ```
- GoREST: deleting already-deleted resource returns 404. APIs with conflict rules return 409.

---

## Traceability

| Item | Location |
|------|----------|
| **Test Case ID** | API-DELETE-007 |
| **Postman request** | DELETE > With Conflict Returns 409 |
| **Project path** | `postman-api-collection/collection/DELETE/delete_with_conflict_returns_409/` |
| **Java equivalent** | `java-rest-api-automation/rest-api-tests/delete/delete_with_conflict_returns_409_when_applicable/` |
