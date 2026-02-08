# Test Case: PATCH with Empty Body Returns 400 or 200 Per API Contract

| Attribute | Value |
|-----------|--------|
| **Test Case ID** | API-PATCH-005 |
| **Priority** | Medium |
| **Type** | REST API, Negative / Functional |

## Objective

Verify that a PATCH request with an empty body returns 400 (validation error) or 200 (no change) per API contract.

## Preconditions

- Valid resource ID exists. Auth available.
- API behaviour for empty PATCH body is documented.

## Test Data

- Body: `{}` (empty JSON object).

## Steps

1. Send a PATCH request with empty body `{}`.
2. Capture the response status code.
3. Verify status per API: 400 (reject) or 200 (no-op acceptable).

## Expected Result

- Response status code is **400** (if API rejects) or **200** (if API accepts no-op).
- No unintended changes to resource.

## Postman Automation Notes

- **Tests tab:** Accept 400, 200 or 404 (GoREST returns 200 for {}). 404 when user_id invalid.
  ```
  pm.test("Status is 400, 200 or 404", () => pm.expect([400, 200, 404]).to.include(pm.response.code));
  ```

---

## Traceability

| Item | Location |
|------|----------|
| **Test Case ID** | API-PATCH-005 |
| **Postman request** | PATCH Update > Empty Body |
| **Project path** | `postman-api-collection/collection/PATCH-Update/patch_empty_body_returns_400_or_200/` |
| **Java equivalent** | `java-rest-api-automation/rest-api-tests/patch-update/patch_empty_body_returns_400_or_200_per_contract/` |
