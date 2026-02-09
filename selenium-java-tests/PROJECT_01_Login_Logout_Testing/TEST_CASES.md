# Test Cases — PROJECT_01: Login & Logout Testing

**Project:** Login & Logout Testing (Sauce Demo)  
**Document Version:** 1.0  
**Last Updated:** 2025-02-08  
**Scope:** Web UI automation with Selenium (Java). Page Object Model; no hardcoded waits.

---

## 1. Login Success

| TC_ID | Summary | Preconditions | Steps | Expected Result | Priority | Type |
|-------|---------|---------------|-------|-----------------|----------|------|
| TC-LOGIN-001 | Login with valid credentials (standard_user) | Browser open; application URL from config. | 1. Navigate to login page.<br>2. Enter username `standard_user`, password `secret_sauce`.<br>3. Click Login.<br>4. Wait for products page (explicit wait). | Products page loads; URL contains `inventory`; page title "Products"; menu and cart visible. | Critical | Functional |
| TC-LOGIN-002 | Login with valid credentials (problem_user) | Same as above. | 1. Navigate to login page.<br>2. Enter username `problem_user`, password `secret_sauce`.<br>3. Click Login. | Products page loads; URL contains `inventory`. | High | Functional |
| TC-LOGIN-003 | Login with valid credentials (performance_glitch_user) | Same as above. | 1. Navigate to login page.<br>2. Enter username `performance_glitch_user`, password `secret_sauce`.<br>3. Click Login. | Products page loads; URL contains `inventory`. | High | Functional |
| TC-LOGIN-004 | Verify UI elements after successful login | User logged in as standard_user. | 1. After login, verify menu button visible.<br>2. Verify shopping cart icon visible. | Menu and shopping cart are visible. | Medium | Functional |

---

## 2. Login Failure

| TC_ID | Summary | Preconditions | Steps | Expected Result | Priority | Type |
|-------|---------|---------------|-------|-----------------|----------|------|
| TC-LOGIN-005 | Error message with invalid username | On login page. | 1. Navigate to login page.<br>2. Enter username `invalid_user`, password `secret_sauce`.<br>3. Click Login. | Error message displayed; user remains on login page. | Critical | Negative |
| TC-LOGIN-006 | Error message with invalid password | On login page. | 1. Enter username `standard_user`, password `wrong_password`.<br>2. Click Login. | Error message (e.g. password/credentials/match); user remains on login page. | Critical | Negative |
| TC-LOGIN-007 | Validation with empty credentials | On login page. | 1. Leave username and password empty.<br>2. Click Login. | Error message or HTML5 validation; user not logged in. | High | Negative |
| TC-LOGIN-008 | Fail with both wrong credentials | On login page. | 1. Enter username `wrong_user`, password `wrong_pass`.<br>2. Click Login. | Login fails; no redirect to inventory. | High | Negative |
| TC-LOGIN-009 | Fail with empty username only | On login page. | 1. Leave username empty; enter valid password.<br>2. Click Login. | Login fails; error or validation. | Medium | Negative |
| TC-LOGIN-010 | Fail with empty password only | On login page. | 1. Enter valid username; leave password empty.<br>2. Click Login. | Login fails; error or validation. | Medium | Negative |
| TC-LOGIN-011 | Fail with email as username (invalid format) | On login page. | 1. Enter `test@test.com`, password `password123`.<br>2. Click Login. | Login fails; no access to products. | Low | Negative |

---

## 3. Logout

| TC_ID | Summary | Preconditions | Steps | Expected Result | Priority | Type |
|-------|---------|---------------|-------|-----------------|----------|------|
| TC-LOGOUT-001 | Logout after successful login | User logged in (standard_user). | 1. From products page, open menu.<br>2. Click Logout.<br>3. Verify URL and login form. | Redirect to login page; URL contains saucedemo.com and not inventory; username, password, Login button visible. | Critical | Functional |
| TC-LOGOUT-002 | Session cleared after logout (direct access blocked) | User logged in then logged out. | 1. Logout from products page.<br>2. Navigate directly to `.../inventory.html`. | User redirected to login page or cannot see inventory content. | High | Security/Functional |
| TC-LOGOUT-003 | Relogin after logout | User logged out. | 1. On login page, enter standard_user / secret_sauce.<br>2. Click Login. | Second login succeeds; products page loads. | High | Functional |

---

## Test Data (no secrets in code)

- **Base URL:** From config/env (e.g. `https://www.saucedemo.com`).
- **Valid users:** standard_user, problem_user, performance_glitch_user — password from config/env or test data file only.
- **Invalid:** invalid_user, wrong_password, empty fields, wrong_user/wrong_pass.

---

## Checklist (pre-implementation)

- [ ] Tests are independent; can run in any order.
- [ ] Credentials and URL from config/env; `.env.example` with placeholders only.
- [ ] Page Object classes: LoginPage, ProductsPage.
- [ ] Explicit waits (WebDriverWait); no Thread.sleep.
- [ ] Assertion messages are descriptive.
- [ ] No sensitive data in logs or reports.
