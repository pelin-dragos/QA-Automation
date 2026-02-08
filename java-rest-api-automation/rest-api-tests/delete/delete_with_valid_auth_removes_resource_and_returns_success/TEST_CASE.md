# Test Case: DELETE with Valid Auth Removes Resource and Returns Success

| Attribute | Value |
|-----------|--------|
| **Test Case ID** | API-DELETE-006 |
| **Priority** | High |
| **Type** | REST API, Functional, Security |

## Objective

Verify that a DELETE request with valid authentication removes the resource and returns a success status (204 or 200).

## Preconditions

- Delete endpoint requires authentication.
- Valid credentials or token are available from config/env.
- A resource exists (created in setup) and its ID is available.

## Test Data

- Valid existing resource ID (e.g. created in @BeforeEach or test).
- Valid auth from env/config.

## Steps

1. Create or obtain a resource; note its ID.
2. Obtain valid auth from config/env.
3. Send a DELETE request with that ID and valid auth.
4. Capture the response status code.
5. Optionally send GET for same ID and verify 404.

## Expected Result

- Response status code is **204** (or 200 per contract).
- Resource is deleted. Subsequent GET returns 404. No 401 or 403.

## Automation Notes

- Assert on status and optionally on GET 404. Use auth from config; create resource in setup to keep test self-contained.

---

## Traceability (automation)

| Item | Location |
|------|----------|
| **Test Case ID** | API-DELETE-006 |
| **Automated test (source)** | `DeleteWithValidAuthRemovesResourceAndReturnsSuccessTest.java` (same folder) |
| **Project path** | `java-rest-api-automation/rest-api-tests/delete/delete_with_valid_auth_removes_resource_and_returns_success/` |
