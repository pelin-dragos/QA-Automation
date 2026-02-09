# Test Run Conformity — PROJECT_01

**Run date:** 2026-02-08  
**Browser:** Firefox  
**Result:** All 14 tests **PASSED** (0 failures, 0 errors, 0 skipped).

---

## Conformity with TEST_CASES.md

| TC_ID | Test Case Summary | Test Method | Result | Expected (TEST_CASES.md) |
|-------|-------------------|-------------|--------|---------------------------|
| **1. Login Success** |
| TC-LOGIN-001 | Login with valid credentials (standard_user) | shouldLoginWithStandardUser | ✅ PASS | Products page; URL contains inventory; title "Products" |
| TC-LOGIN-002 | Login with valid credentials (problem_user) | shouldLoginWithProblemUser | ✅ PASS | Products page; URL contains inventory |
| TC-LOGIN-003 | Login with valid credentials (performance_glitch_user) | shouldLoginWithPerformanceGlitchUser | ✅ PASS | Products page; URL contains inventory |
| TC-LOGIN-004 | Verify UI elements after successful login | shouldShowMenuAndCartAfterLogin | ✅ PASS | Menu and shopping cart visible |
| **2. Login Failure** |
| TC-LOGIN-005 | Error message with invalid username | shouldShowErrorWithInvalidUsername | ✅ PASS | Error message; user remains on login page |
| TC-LOGIN-006 | Error message with invalid password | shouldShowErrorWithInvalidPassword | ✅ PASS | Error (password/credentials/match); no redirect to inventory |
| TC-LOGIN-007 | Validation with empty credentials | shouldShowValidationWithEmptyCredentials | ✅ PASS | Error or HTML5 validation; not logged in |
| TC-LOGIN-008 | Fail with both wrong credentials | shouldFailWithBothWrongCredentials | ✅ PASS | Login fails; no redirect to inventory |
| TC-LOGIN-009 | Fail with empty username only | shouldFailWithEmptyUsername | ✅ PASS | Login fails; error or validation |
| TC-LOGIN-010 | Fail with empty password only | shouldFailWithEmptyPassword | ✅ PASS | Login fails; error or validation |
| TC-LOGIN-011 | Fail with email as username | shouldFailWithEmailAsUsername | ✅ PASS | Login fails; no access to products |
| **3. Logout** |
| TC-LOGOUT-001 | Logout after successful login | shouldLogoutAndRedirectToLoginPage | ✅ PASS | Redirect to login; username, password, Login button visible |
| TC-LOGOUT-002 | Session cleared (direct access blocked) | shouldBlockDirectAccessToInventoryAfterLogout | ✅ PASS | Direct nav to inventory → redirect or login page |
| TC-LOGOUT-003 | Relogin after logout | shouldAllowReloginAfterLogout | ✅ PASS | Second login succeeds; products page loads |

---

## Summary

- **Total test cases in TEST_CASES.md:** 14  
- **Total tests implemented and run:** 14  
- **All test results match the Expected Result column** in TEST_CASES.md (steps, assertions, and outcomes are aligned).  
- Tests are independent, use Page Objects (LoginPage, ProductsPage), explicit waits, and config for base URL.
