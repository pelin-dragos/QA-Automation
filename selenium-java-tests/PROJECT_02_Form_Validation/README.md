# PROJECT_02 — Form Validation

Automated form validation: **required fields**, **email**, **password**, **phone** (DemoQA Practice Form, Text Box; The Internet login).

## Test cases

See **[TEST_CASES.md](TEST_CASES.md)** for all cases (required, email, password, phone).

## Implementation

- **Config:** `TestConfig` — DemoQA and The Internet base URLs from env (see `.env.example`).
- **Browser:** Firefox (same as PROJECT_01).
- **Page objects:** `DemoQATextBoxPage`, `DemoQAPracticeFormPage`, `TheInternetLoginPage`.
- **Tests:** `RequiredFieldsTest`, `EmailValidationTest`, `PasswordValidationTest`, `PhoneValidationTest` — aligned with TEST_CASES.md TC-IDs.

## Running tests

**Prerequisites:** Java 17+, Firefox installed.

```bash
cd PROJECT_02_Form_Validation
mvnw.cmd test
```

Or from repo root: `mvn test -pl PROJECT_02_Form_Validation`
