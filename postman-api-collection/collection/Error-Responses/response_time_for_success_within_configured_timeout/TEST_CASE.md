# Test Case: Response Time for Success Within Configured Timeout

| Attribute | Value |
|-----------|--------|
| **Test Case ID** | API-ERROR-003 |
| **Priority** | Low |
| **Type** | REST API, Performance (optional) |

## Objective

Verify that a successful request completes within a configured maximum response time (timeout threshold), so that slow endpoints can be detected in CI or test runs.

## Preconditions

- A success endpoint is configured (e.g. GET list).
- A maximum response time (e.g. 5000 ms) is configured.
- Environment is stable enough to avoid flakiness.

## Test Data

- Endpoint that typically returns 200 (e.g. GET list).
- Configured timeout threshold: 5000 ms (or from environment).

## Steps

1. Send a request to an endpoint that returns 200.
2. Measure the response time.
3. Assert that response time is less than or equal to the configured threshold.
4. Assert that status code is 200.

## Expected Result

- Response status code is **200**.
- Response time (ms) is **≤ configured timeout** (e.g. 5000 ms).

## Postman Automation Notes

- **Tests tab:**
  ```
  pm.test("Status is 200", () => pm.response.to.have.status(200));
  pm.test("Response time within 5000 ms", () => {
    pm.expect(pm.response.responseTime).to.be.below(5000);
  });
  ```
- Consider tagging as performance and running separately. Can be disabled in unstable environments.

---

## Traceability

| Item | Location |
|------|----------|
| **Test Case ID** | API-ERROR-003 |
| **Postman request** | Error Responses > Response Time Within Timeout |
| **Project path** | `postman-api-collection/collection/Error-Responses/response_time_for_success_within_configured_timeout/` |
| **Java equivalent** | `java-rest-api-automation/rest-api-tests/error-responses/response_time_for_success_within_configured_timeout/` |
