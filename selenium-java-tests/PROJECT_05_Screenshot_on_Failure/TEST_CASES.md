# Test Cases — PROJECT_05: Screenshot on Failure

**Project:** Screenshot on Failure (Sauce Demo)  
**Document Version:** 1.0  
**Scope:** Automatic screenshot capture when a test fails. One passing test; four intentional failures to demonstrate screenshots.

---

## 1. Successful Test (No Screenshot)

| TC_ID | Summary | Steps | Expected Result | Priority |
|-------|---------|-------|-----------------|----------|
| TC-SCREEN-001 | Successful login — no screenshot | 1. Navigate to Sauce Demo. 2. Login standard_user / secret_sauce. 3. Assert URL contains inventory. | Test passes; no screenshot saved. | High |

---

## 2. Intentional Failures (Screenshot Captured)

| TC_ID | Summary | Steps | Expected Result | Priority |
|-------|---------|-------|-----------------|----------|
| TC-SCREEN-002 | Invalid credentials — fail and capture | 1. Navigate. 2. Login invalid_user / invalid_password. 3. Assert URL contains inventory (fails). | Test fails; screenshot saved in target/screenshot-on-failure/. | High |
| TC-SCREEN-003 | Assertion failure — capture | 1. Navigate. 2. Assert login page is NOT loaded (fails). | Test fails; screenshot saved. | High |
| TC-SCREEN-004 | Element not found — capture | 1. Navigate. 2. Wait for #this-element-does-not-exist. 3. Fail with message. | Test fails; screenshot saved. | Medium |
| TC-SCREEN-005 | Timeout — capture | 1. Navigate. 2. Wait for #element-that-will-never-appear (short timeout). 3. Fail with message. | Test fails; screenshot saved. | Medium |

---

## Screenshot Behaviour

- **ScreenshotOnFailureWatcher** (JUnit 5 TestWatcher) runs after each test; if the test failed, it takes a screenshot from the WebDriver and saves it to **target/screenshot-on-failure/** (or SCREENSHOT_OUTPUT_DIR).
- File name format: `ClassName_methodName_yyyyMMdd-HHmmss.png`.
- Only failed tests produce a screenshot; the passing test does not.

---

## Checklist

- [ ] Tests independent; run in any order.
- [ ] Base URL from config/env; screenshot dir configurable.
- [ ] Page Object: LoginPage (Sauce Demo).
- [ ] @RegisterExtension ScreenshotOnFailureWatcher in BaseTest.
