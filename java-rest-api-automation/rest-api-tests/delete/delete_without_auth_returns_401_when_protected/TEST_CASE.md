# Test Case: DELETE Without Auth Returns 401 When Endpoint Is Protected

| Attribute | Value |
|-----------|--------|
| **Test Case ID** | API-DELETE-005 |
| **Priority** | High |
| **Type** | REST API, Security / Negative |

## Objective

Verify that a DELETE request without valid authentication returns HTTP 401 Unauthorized and the resource is not deleted.

## Preconditions

- Delete endpoint requires authentication.
- A resource exists (so that the test is about auth, not about non-existent ID). Only auth is omitted or invalid.

## Test Data

- Valid existing resource ID.
- No auth header or invalid token.

## Steps

1. Send a DELETE request with a valid existing ID but without valid auth (omit or use invalid token).
2. Capture the response status code and body.
3. Optionally GET the resource to confirm it still exists.

## Expected Result

- Response status code is **401 Unauthorized**.
- Resource is not deleted (still exists). If endpoint is public, mark test as N/A.

## Automation Notes

- Assert on status 401. Optionally assert GET still returns 200 for the resource. Skip or tag when endpoint is not protected.

---

## Traceability (automation)

| Item | Location |
|------|----------|
| **Test Case ID** | API-DELETE-005 |
| **Automated test (source)** | `DeleteWithoutAuthReturns401WhenProtectedTest.java` (same folder) |
| **Project path** | `java-rest-api-automation/rest-api-tests/delete/delete_without_auth_returns_401_when_protected/` |
