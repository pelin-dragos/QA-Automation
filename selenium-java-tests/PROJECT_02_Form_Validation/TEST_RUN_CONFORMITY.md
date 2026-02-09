# Test Run Conformity — PROJECT_02

**Run date:** 2026-02-08  
**Browser:** Firefox  
**Result:** All 32 tests **PASSED** (0 failures, 0 errors, 0 skipped).

---

## Conformity with TEST_CASES.md

| Section | TC_ID | Test Case Summary | Result |
|---------|-------|-------------------|--------|
| **1. Required Fields** | TC-REQ-001 | Validate empty First Name | ✅ PASS |
| | TC-REQ-002 | Validate empty Last Name | ✅ PASS |
| | TC-REQ-003 | Validate empty Email | ✅ PASS |
| | TC-REQ-004 | Form submits when all required valid | ✅ PASS |
| | TC-REQ-005 | Multiple required fields empty | ✅ PASS |
| **2. Email Validation** | TC-EMAIL-001 | Accept valid email (Text Box) | ✅ PASS |
| | TC-EMAIL-002 to 009 | Reject invalid / accept valid formats / empty | ✅ PASS |
| **3. Password Validation** | TC-PWD-001 to 004 | Valid password, short, empty, formats | ✅ PASS |
| **4. Phone Validation** | TC-PHONE-001 to 007 | Valid 10-digit, invalid formats, empty, dashes, too long | ✅ PASS |

---

## Summary

- **Total test cases in TEST_CASES.md:** 25 (some tests are parameterized, yielding 32 test runs).  
- **All results align with Expected Result** in TEST_CASES.md.  
- **Sites:** DemoQA (Practice Form, Text Box), The Internet (Form Authentication).  
- **Optional:** Root `.gitignore` and `mvnw.cmd` in all projects; Firefox; config from env.
