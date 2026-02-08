# Test Case: DELETE with Non-Existent ID Returns 404

| Attribute | Value |
|-----------|--------|
| **Test Case ID** | API-DELETE-002 |
| **Priority** | High |
| **Type** | REST API, Negative |

## Objective

Verify that a DELETE request with a non-existent resource ID returns HTTP 404 Not Found. The API should not return 204 or 200 for a resource that does not exist (idempotent delete semantics may allow 204; 404 is acceptable and clear).

## Preconditions

- Delete endpoint is configured.
- The ID used does not exist (e.g. high number, or already deleted).

## Test Data

- Non-existent resource ID.

## Steps

1. Choose a non-existent resource ID.
2. Send a DELETE request with that ID in the path.
3. Capture the response status code and body.

## Expected Result

- Response status code is **404 Not Found** (or 204 if API treats "delete non-existent" as idempotent success; document which).
- No 500. If 404, body may contain an error message.

## Automation Notes

- Assert on status 404 (or 204 per contract). Use a stable non-existent ID. Document API idempotency behaviour.

---

## Traceability (automation)

| Item | Location |
|------|----------|
| **Test Case ID** | API-DELETE-002 |
| **Automated test (source)** | `DeleteNonexistentIdReturns404Test.java` (same folder) |
| **Project path** | `java-rest-api-automation/rest-api-tests/delete/delete_nonexistent_id_returns_404/` |
