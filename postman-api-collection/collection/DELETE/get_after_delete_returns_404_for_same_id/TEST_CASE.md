# Test Case: GET After DELETE for Same ID Returns 404

| Attribute | Value |
|-----------|--------|
| **Test Case ID** | API-DELETE-004 |
| **Priority** | High |
| **Type** | REST API, Functional |

## Objective

Verify that after a successful DELETE, a GET request for the same resource ID returns HTTP 404 (resource no longer exists).

## Preconditions

- Resource exists. Valid ID and auth available.
- This test requires two requests: DELETE then GET (or run after DELETE test).

## Test Data

- First: `DELETE {{base_url}}/users/{{user_id}}`
- Second: `GET {{base_url}}/users/{{user_id}}` (same ID)
- Auth for both.

## Steps

1. Create or obtain a resource; note its ID.
2. Send a DELETE request for that ID; verify 204 or 200.
3. Send a GET request for the same ID.
4. Verify GET returns 404.

## Expected Result

- DELETE returns 204 or 200.
- GET for same ID returns **404 Not Found**.
- Resource is confirmed deleted.

## Postman Automation Notes

- Use collection runner or chained requests: run DELETE, then GET with same `user_id`.
- **Tests tab (for GET request):** `pm.test("GET after DELETE returns 404", () => pm.response.to.have.status(404));`
- Or use a single request flow: DELETE in pre-request, GET as main request.

---

## Traceability

| Item | Location |
|------|----------|
| **Test Case ID** | API-DELETE-004 |
| **Postman request** | DELETE > GET After Delete Returns 404 |
| **Project path** | `postman-api-collection/collection/DELETE/get_after_delete_returns_404_for_same_id/` |
| **Java equivalent** | `java-rest-api-automation/rest-api-tests/delete/get_after_delete_returns_404_for_same_id/` |
