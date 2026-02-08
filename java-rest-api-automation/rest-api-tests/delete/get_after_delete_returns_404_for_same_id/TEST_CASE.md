# Test Case: GET After DELETE for Same ID Returns 404

| Attribute | Value |
|-----------|--------|
| **Test Case ID** | API-DELETE-004 |
| **Priority** | High |
| **Type** | REST API, Functional |

## Objective

Verify that after a successful DELETE of a resource, a GET request for the same resource ID returns HTTP 404 Not Found, confirming the resource no longer exists.

## Preconditions

- Delete and GET-by-ID endpoints are configured.
- A resource can be created (or exists) so that the test can delete it and then assert on GET.

## Test Data

- Resource created in test (or known existing ID). Same ID used for DELETE and then GET.

## Steps

1. Create a resource (or use an existing one) and obtain its ID.
2. Send a DELETE request with that ID; verify 204 (or 200).
3. Send a GET request to the resource-by-ID endpoint with the same ID.
4. Capture the GET response status code.

## Expected Result

- DELETE returns **204** (or 200).
- GET returns **404 Not Found**. Response body may contain an error message; no resource data is returned.

## Automation Notes

- Assert on GET status 404. Create resource in setup and delete in test to keep test independent and repeatable. Ensures delete actually removed the resource.

---

## Traceability (automation)

| Item | Location |
|------|----------|
| **Test Case ID** | API-DELETE-004 |
| **Automated test (source)** | `GetAfterDeleteReturns404ForSameIdTest.java` (same folder) |
| **Project path** | `java-rest-api-automation/rest-api-tests/delete/get_after_delete_returns_404_for_same_id/` |
