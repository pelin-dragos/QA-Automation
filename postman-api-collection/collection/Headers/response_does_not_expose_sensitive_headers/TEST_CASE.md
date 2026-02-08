# Test Case: Response Does Not Expose Sensitive Headers

| Attribute | Value |
|-----------|--------|
| **Test Case ID** | API-HEADERS-003 |
| **Priority** | Low |
| **Type** | REST API, Security |

## Objective

Verify that the API response does not expose sensitive or unnecessary headers (e.g. server version, internal framework, debug headers) that could aid attackers or leak implementation details.

## Preconditions

- API is configured (or expected) to not expose certain headers.
- Base URL and at least one endpoint are available.
- Expected behaviour is documented (which headers must be absent).

## Test Data

- Headers that must not be present: e.g. `X-Powered-By`, `Server` with version.
- Any successful request to get a response.

## Steps

1. Send a request to an endpoint that returns a response.
2. Capture all response headers.
3. Verify that no sensitive/blacklisted header is present (or value is sanitized).

## Expected Result

- Sensitive headers (per project policy) are absent or do not contain sensitive values.
- Test passes when none of the blacklisted headers are present.

## Postman Automation Notes

- **Tests tab:**
  ```
  pm.test("No X-Powered-By header", () => {
    pm.expect(pm.response.headers.has("X-Powered-By")).to.be.false;
  });
  pm.test("Server header does not expose version (optional)", () => {
    const server = pm.response.headers.get("Server");
    if (server) {
      pm.expect(server).to.not.match(/\d+\.\d+/); // No version number
    }
  });
  ```
- Adapt assertions to API policy. May be N/A if no header policy defined.

---

## Traceability

| Item | Location |
|------|----------|
| **Test Case ID** | API-HEADERS-003 |
| **Postman request** | Headers > No Sensitive Headers |
| **Project path** | `postman-api-collection/collection/Headers/response_does_not_expose_sensitive_headers/` |
| **Java equivalent** | `java-rest-api-automation/rest-api-tests/headers/response_does_not_expose_sensitive_headers/` |
