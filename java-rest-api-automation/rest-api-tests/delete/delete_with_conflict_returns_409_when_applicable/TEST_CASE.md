# Test Case: DELETE with Conflict Returns 409 When Applicable

| Attribute | Value |
|-----------|--------|
| **Test Case ID** | API-DELETE-007 |
| **Priority** | Medium |
| **Type** | REST API, Negative |

## Objective

Verify that when a resource cannot be deleted due to a business rule or dependency (e.g. resource in use, has child records), the API returns HTTP 409 Conflict (or 400 with a clear message) and does not delete the resource.

## Preconditions

- Delete endpoint enforces constraints (e.g. cannot delete category with products, or order in progress).
- Test data or setup creates a resource that is in a "cannot delete" state (e.g. linked to another resource, or status = in_use).
- API returns 409 or 400 for this scenario (per contract).

## Test Data

- Resource ID that is in a state that triggers conflict (e.g. resource with dependencies, or status preventing delete).

## Steps

1. Create or identify a resource that cannot be deleted per business rules (e.g. add dependency first).
2. Send a DELETE request with that resource ID and valid auth (if required).
3. Capture the response status code and body.
4. Verify the resource still exists (GET returns 200).

## Expected Result

- Response status code is **409 Conflict** (or 400 per API contract).
- Response body contains an error message indicating the conflict or dependency.
- Resource is not deleted. If API does not support conflict scenario, mark test as N/A.

## Automation Notes

- Assert on status 409 (or 400) and on error message. Setup may require creating dependent resources. Document when test is N/A.

---

## Traceability (automation)

| Item | Location |
|------|----------|
| **Test Case ID** | API-DELETE-007 |
| **Automated test (source)** | `DeleteWithConflictReturns409WhenApplicableTest.java` (same folder) |
| **Project path** | `java-rest-api-automation/rest-api-tests/delete/delete_with_conflict_returns_409_when_applicable/` |
