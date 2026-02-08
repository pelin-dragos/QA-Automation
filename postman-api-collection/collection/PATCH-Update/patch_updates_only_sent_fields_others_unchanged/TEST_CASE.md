# Test Case: PATCH Updates Only Sent Fields; Others Unchanged

| Attribute | Value |
|-----------|--------|
| **Test Case ID** | API-PATCH-002 |
| **Priority** | High |
| **Type** | REST API, Functional |

## Objective

Verify that a PATCH request updates only the fields sent in the request; other fields remain unchanged.

## Preconditions

- Resource exists with known initial values.
- Valid ID and auth. Base URL configured.

## Test Data

- Initial resource has: name="Original", status="active".
- PATCH body: `{"status":"inactive"}` only.
- Verify name unchanged, status updated.

## Steps

1. Note initial resource state (or GET before PATCH).
2. Send PATCH with one field only.
3. Verify in response: sent field updated, other fields unchanged.
4. Optionally GET again to confirm.

## Expected Result

- Response status code is **200**.
- Only the sent field (e.g. status) is updated.
- Other fields (e.g. name, email) remain as before.

## Postman Automation Notes

- **Tests tab:**
  ```
  pm.test("Status is 200", () => pm.response.to.have.status(200));
  pm.test("Only sent field updated", () => {
    const json = pm.response.json();
    pm.expect(json).to.have.property('status', 'inactive');
    pm.expect(json).to.have.property('name'); // name unchanged
  });
  ```

---

## Traceability

| Item | Location |
|------|----------|
| **Test Case ID** | API-PATCH-002 |
| **Postman request** | PATCH Update > Only Sent Fields Updated |
| **Project path** | `postman-api-collection/collection/PATCH-Update/patch_updates_only_sent_fields_others_unchanged/` |
| **Java equivalent** | `java-rest-api-automation/rest-api-tests/patch-update/patch_updates_only_sent_fields_others_unchanged/` |
