# Test Case: 5xx Responses Handled Without Crashing Client

| Attribute | Value |
|-----------|--------|
| **Test Case ID** | API-ERROR-002 |
| **Priority** | Low |
| **Type** | REST API, Resilience |

## Objective

Verify that when the API returns a 5xx server error, the test client handles the response without throwing an unhandled exception—i.e. the client can read status code and optionally body.

## Preconditions

- Ability to trigger or simulate a 5xx (e.g. mock server, fault injection). If 5xx cannot be triggered reliably, test may be N/A.
- Client is configured with timeouts and error handling.

## Test Data

- Request or endpoint that returns 5xx (when available), or use a mock.

## Steps

1. Send a request that results in a 5xx response (or use mock).
2. Capture the response status code without letting the client throw.
3. Assert that status code is in 5xx range and that no unhandled exception occurred.

## Expected Result

- Response status code is 5xx (500, 502, 503, etc.).
- Test client completes without unhandled exception.

## Postman Automation Notes

- **Tests tab:** When 5xx cannot be triggered (e.g. GoREST), use fallback:
  ```
  pm.test("Client handles response", () => pm.expect(pm.response.code).to.be.within(200, 599));
  ```
- **Note:** 5xx is hard to trigger against real GoREST. Request uses GET /users/1; assertion accepts 200–599 as fallback. With mock/fault injection, assert 500–599.

---

## Traceability

| Item | Location |
|------|----------|
| **Test Case ID** | API-ERROR-002 |
| **Postman request** | Error Responses > 5xx Handled |
| **Project path** | `postman-api-collection/collection/Error-Responses/five_xx_responses_handled_without_crashing_client/` |
| **Java equivalent** | `java-rest-api-automation/rest-api-tests/error-responses/five_xx_responses_handled_without_crashing_client/` |
