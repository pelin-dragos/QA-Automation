# Test Cases — PROJECT_02: Form Validation

**Project:** Form Validation (DemoQA, The Internet)  
**Document Version:** 1.0  
**Last Updated:** 2025-02-08  
**Scope:** Email, password, phone, required fields; valid/invalid formats and error messages.

---

## 1. Required Fields

| TC_ID | Summary | Preconditions | Steps | Expected Result | Priority | Type |
|-------|---------|---------------|-------|-----------------|----------|------|
| TC-REQ-001 | Validate empty First Name (Practice Form) | On DemoQA Practice Form page. | 1. Leave First Name empty.<br>2. Fill Last Name, Email, Gender, Mobile.<br>3. Click Submit. | First Name invalid (HTML5 or error message); form does not submit successfully. | Critical | Negative |
| TC-REQ-002 | Validate empty Last Name (Practice Form) | Same. | 1. Fill First Name; leave Last Name empty.<br>2. Fill Email, Gender, Mobile.<br>3. Click Submit. | Last Name invalid; form does not submit. | Critical | Negative |
| TC-REQ-003 | Validate empty Email (Practice Form) | Same. | 1. Fill First Name, Last Name; leave Email empty.<br>2. Fill Gender, Mobile.<br>3. Click Submit. | Email invalid or required message; form does not submit or error shown. | High | Negative |
| TC-REQ-004 | Form submits when all required fields valid | Same. | 1. Fill First Name, Last Name, Email, Gender, Mobile (valid values).<br>2. Click Submit. | No required-field errors; form submits or success modal/redirect. | Critical | Functional |
| TC-REQ-005 | Multiple required fields empty | Same. | 1. Fill only Gender and Mobile.<br>2. Leave First Name, Last Name, Email empty.<br>3. Click Submit. | All three fields invalid; form does not submit. | High | Negative |

---

## 2. Email Validation

| TC_ID | Summary | Preconditions | Steps | Expected Result | Priority | Type |
|-------|---------|---------------|-------|-----------------|----------|------|
| TC-EMAIL-001 | Accept valid email format (Text Box) | On DemoQA Text Box page. | 1. Enter `test@example.com`, Full Name, Address.<br>2. Click Submit. | Email not marked invalid; form accepts. | Critical | Functional |
| TC-EMAIL-002 | Reject email without @ | Same. | 1. Enter `testexample.com` in email.<br>2. Blur or submit. | Email marked invalid (HTML5 or error). | High | Negative |
| TC-EMAIL-003 | Reject email without domain | Same. | 1. Enter `test@` in email. | Email invalid. | High | Negative |
| TC-EMAIL-004 | Reject email without username | Same. | 1. Enter `@example.com` in email. | Email invalid. | High | Negative |
| TC-EMAIL-005 | Reject email with space (username) | Same. | 1. Enter `test @example.com`. | Email invalid. | Medium | Negative |
| TC-EMAIL-006 | Reject email with space in domain | Same. | 1. Enter `test@exam ple.com`. | Email invalid. | Medium | Negative |
| TC-EMAIL-007 | Reject email without TLD | Same. | 1. Enter `test@example`. | Invalid or pattern mismatch per implementation. | Medium | Negative |
| TC-EMAIL-008 | Accept valid formats (parameterized) | Same. | 1. For each: test@example.com, user.name@example.com, user+tag@example.co.uk, test123@test-domain.com, a@b.co.<br>2. Enter in email field and verify. | Email not invalid. | High | Functional |
| TC-EMAIL-009 | Validate empty email field | Same. | 1. Leave email empty; fill other fields.<br>2. Submit. | If required: invalid; otherwise per business rules. | Medium | Negative |

---

## 3. Password Validation

| TC_ID | Summary | Preconditions | Steps | Expected Result | Priority | Type |
|-------|---------|---------------|-------|-----------------|----------|------|
| TC-PWD-001 | Accept valid password (The Internet login) | On The Internet login page. | 1. Enter username tomsmith, password SuperSecretPassword!<br>2. Click Login. | Login succeeds; no invalid/incorrect error. | Critical | Functional |
| TC-PWD-002 | Reject too short password | Same. | 1. Enter username tomsmith, password 123.<br>2. Click Login. | Error message (invalid/incorrect/wrong). | High | Negative |
| TC-PWD-003 | Validate empty password | Same. | 1. Enter username tomsmith; leave password empty.<br>2. Click Login. | Error message visible; login fails. | High | Negative |
| TC-PWD-004 | Handle different formats (special chars, numbers) | Same. | 1. Try passwords: SuperSecretPassword!@#$%, 12345678, Password123, PASSWORD123!.<br>2. Assert visible outcome (success or expected error). | Behaviour consistent with app rules; no unhandled errors. | Medium | Functional/Negative |

---

## 4. Phone Validation

| TC_ID | Summary | Preconditions | Steps | Expected Result | Priority | Type |
|-------|---------|---------------|-------|-----------------|----------|------|
| TC-PHONE-001 | Accept valid 10-digit phone (Practice Form) | On DemoQA Practice Form. | 1. Fill all required fields; enter Mobile `1234567890`.<br>2. Submit. | No mobile/number validation errors. | Critical | Functional |
| TC-PHONE-002 | Reject 9-digit phone | Same. | 1. Enter Mobile `123456789`; fill rest.<br>2. Submit. | Invalid or error (length/pattern). | High | Negative |
| TC-PHONE-003 | Reject phone with letters | Same. | 1. Enter Mobile `123456789a`. | Invalid or error. | High | Negative |
| TC-PHONE-004 | Reject phone with spaces | Same. | 1. Enter Mobile `123456789 `. | Invalid or error. | Medium | Negative |
| TC-PHONE-005 | Handle phone with dashes (per spec) | Same. | 1. Enter `123-456-7890`. | Accept or reject per defined rules; assert accordingly. | Medium | Functional |
| TC-PHONE-006 | Phone too long (11 digits) | Same. | 1. Enter `12345678901`; check maxlength or validation. | Value limited or validation error. | Medium | Negative |
| TC-PHONE-007 | Empty phone (required) | Same. | 1. Fill First Name, Last Name, Email, Gender; leave Mobile empty.<br>2. Submit. | Mobile required/invalid; form does not submit. | High | Negative |

---

## Test Data & Environment

- **DemoQA Practice Form:** URL from config (e.g. demoqa.com/automation-practice-form).
- **DemoQA Text Box:** URL from config (e.g. demoqa.com/text-box).
- **The Internet Login:** URL from config (e.g. the-internet.herokuapp.com/login).
- **Credentials:** No hardcoding; use test data files or config with placeholders in .env.example.

---

## Checklist (pre-implementation)

- [ ] Tests independent; order irrelevant.
- [ ] Locators stable (id, data attributes); no fragile CSS.
- [ ] Explicit waits; no hardcoded sleeps.
- [ ] One logical assertion per test where possible; descriptive messages.
- [ ] Test data in src/test/resources or config; no production data.
