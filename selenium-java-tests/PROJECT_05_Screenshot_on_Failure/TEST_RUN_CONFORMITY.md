# Test Run Conformity — PROJECT_05

**Run date:** 2026-02-09  
**Browser:** Firefox  
**Result:** 1 pass, 4 expected failures. Screenshots saved in `target/screenshot-on-failure/` (e.g. `ScreenshotOnFailureTest_shouldFailWithTimeout_screenshotCaptured_20260209-151510.png`).

---

## Conformity with TEST_CASES.md

| TC_ID | Summary | Test Method | Result | Screenshot |
|-------|---------|-------------|--------|------------|
| TC-SCREEN-001 | Successful login | shouldSuccessfullyLogin_noScreenshot | PASS | No |
| TC-SCREEN-002 | Invalid credentials fail | shouldFailWithInvalidCredentials_screenshotCaptured | FAIL (expected) | Yes |
| TC-SCREEN-003 | Assertion fail | shouldFailWithAssertionError_screenshotCaptured | FAIL (expected) | Yes |
| TC-SCREEN-004 | Element not found | shouldFailWithElementNotFound_screenshotCaptured | FAIL (expected) | Yes |
| TC-SCREEN-005 | Timeout | shouldFailWithTimeout_screenshotCaptured | FAIL (expected) | Yes |

---

## Summary

- **Total tests:** 5 (1 pass, 4 intentional failures for screenshot demo).
- **Screenshots:** Saved under `target/screenshot-on-failure/` (or SCREENSHOT_OUTPUT_DIR) when a test fails. Verified: all 4 failing tests produced a PNG file.
