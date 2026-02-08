# Project 25: Security Testing Automation

## 🎯 Objective
Complete test suite for web vulnerabilities (XSS, SQL injection, CSRF, Security headers, HTTPS) with payload collection and automated reporting.

## 📋 Requirements
- ✅ Tests for XSS in input fields
- ✅ Tests for SQL injection in forms
- ✅ Tests for CSRF protection
- ✅ Tests for authentication bypass
- ✅ Security headers verification
- ✅ Secure data transmission (HTTPS)

## 🛠️ Technologies
- **Playwright** - Modern automation framework
- **TypeScript** - Strongly typed JavaScript
- **Security Payloads** - XSS, SQL injection, CSRF payloads
- **Performance API** - For security headers checking
- **Node.js** - Runtime environment

## ⚠️ IMPORTANT - Legal Notice

**🔒 TEST ONLY YOUR OWN APPLICATIONS OR WITH EXPLICIT PERMISSION!**

- ✅ Test your own or demo applications
- ✅ Use only with written permission
- ❌ Do not use on applications without authorization
- ❌ Do not perform unauthorized penetration testing

**Legal consequences:** Unauthorized use may be illegal!

## 📁 Project Structure

```
PROJECT_25_Security_Testing/
├── package.json                  # Node.js dependencies
├── tsconfig.json                 # TypeScript configuration
├── playwright.config.ts          # Playwright configuration
├── README.md                     # This file
│
├── utils/                         # Utilities
│   ├── SecurityPayloads.ts      # XSS, SQL injection, CSRF payloads
│   ├── SecurityChecker.ts        # SecurityChecker class
│   └── SecurityReporter.ts       # SecurityReporter
│
├── pages/                         # Page Object Pattern
│   └── LoginPage.ts             # Login page
│
├── tests/                         # Test suite
│   └── test_security.spec.ts
│
└── reports/                       # Reports (generated)
    ├── security_report.json      # JSON report
    └── security_report.txt       # Text report
```

## ✨ Features

### 1. XSS Testing
- **Payloads Collection**: Basic, encoded, advanced XSS payloads
- **Input Field Testing**: Inject payloads into input fields
- **Vulnerability Detection**: Detect XSS vulnerabilities
- **Alert Detection**: Detect JavaScript alerts

### 2. SQL Injection Testing
- **Payloads Collection**: Basic, time-based, union-based SQL injection
- **Input Field Testing**: Inject payloads into forms
- **Error Detection**: Detect SQL errors in responses
- **Vulnerability Reporting**: Report SQL injection vulnerabilities

### 3. Security Headers Verification
- **Required Headers**: X-Content-Type-Options, X-Frame-Options, X-XSS-Protection
- **Recommended Headers**: CSP, HSTS, Referrer-Policy
- **Header Validation**: Check presence and correct values
- **Missing Headers**: Report missing headers

### 4. HTTPS/SSL Testing
- **HTTPS Verification**: Verify the site uses HTTPS
- **SSL Certificate**: Check SSL certificate validity
- **Secure Transmission**: Verify secure data transmission

### 5. Authentication Security
- **Bypass Attempts**: Test authentication bypass payloads
- **Weak Authentication**: Detect weak authentication mechanisms
- **Session Security**: Verify session management

### 6. CSRF Testing
- **CSRF Form Generation**: Generate CSRF attack forms
- **Token Validation**: Verify CSRF token presence
- **Protection Detection**: Detect CSRF protection mechanisms

### 7. Security Reporting
- **JSON Report**: Structured vulnerability data
- **Text Report**: Human-readable format
- **HTML Report**: Playwright HTML integration
- **Vulnerability Summary**: Count and severity

## 📝 Deliverables
- ✅ Test suite for security (XSS, SQL injection, headers, HTTPS)
- ✅ Payload collection for testing (XSSPayloads, SQLInjectionPayloads)
- ✅ Security report (JSON, text)
- ✅ Documentation on tested vulnerabilities
- ✅ Security checks utilities (SecurityChecker class)
- ✅ Reporting system (SecurityReporter)

## ✅ Evaluation Criteria
- ✅ Tests for multiple vulnerability types (XSS, SQL, CSRF, headers)
- ✅ Effective payloads (diverse payload types)
- ✅ Clear report for security issues (JSON, text)
- ✅ Functional security headers verification
- ✅ Functional HTTPS verification

## 🚀 Quick Start

### 1. Setup
```bash
cd PROJECTS/PROJECT_25_Security_Testing
npm install
npx playwright install --with-deps chromium
mkdir -p reports
```

### 2. Run Tests
```bash
# All security tests
npm test

# Specific categories
npm run test:headers
npm run test:https
npm run test:xss
npm run test:sql
npm run test:csrf

# Smoke tests
npm run test:smoke
```

### 3. View Reports
```bash
# JSON report
cat reports/security_report.json

# Text report
cat reports/security_report.txt

# HTML report
npm run report
```

## 📚 Documentation

### Code Examples:

**XSS Testing:**
```typescript
import { SecurityChecker } from '../utils/SecurityChecker';
import { XSSPayloads } from '../utils/SecurityPayloads';

const checker = new SecurityChecker(page);
const payloads = XSSPayloads.getBasicPayloads();

const result = await checker.checkXSSVulnerability(
  '#username',
  payloads[0]
);
```

**Security Headers:**
```typescript
const checker = new SecurityChecker(page);
const result = await checker.checkSecurityHeaders();
console.log(result.message);
```

**SQL Injection:**
```typescript
import { SQLInjectionPayloads } from '../utils/SecurityPayloads';

const payloads = SQLInjectionPayloads.getBasicPayloads();
const result = await checker.checkSQLInjection('#username', payloads[0]);
```

## 📊 Security Tests Details

### XSS Payloads:
- Basic: `<script>alert('XSS')</script>`
- Encoded: URL-encoded, HTML-encoded
- Advanced: Cookie theft, redirect attacks

### SQL Injection Payloads:
- Basic: `' OR '1'='1`
- Time-based: `'; SELECT SLEEP(5)--`
- Union-based: `' UNION SELECT ...--`

### Security Headers Checked:
- `X-Content-Type-Options: nosniff`
- `X-Frame-Options: DENY|SAMEORIGIN`
- `X-XSS-Protection: 1; mode=block`
- `Strict-Transport-Security`
- `Content-Security-Policy`

## 📊 Implementation Status

| Feature | Status | Notes |
|---------|--------|-------|
| XSS Testing | ✅ Implemented | Payloads + detection |
| SQL Injection | ✅ Implemented | Payloads + error detection |
| Security Headers | ✅ Implemented | Full header verification |
| HTTPS Verification | ✅ Implemented | HTTPS check |
| CSRF Testing | ✅ Implemented | CSRF form generation |
| Authentication Security | ✅ Implemented | Bypass payloads |
| Reporting | ✅ Implemented | JSON, text |
| Payloads Collection | ✅ Implemented | Comprehensive payloads |

## 💡 Tips

1. **For Accuracy:**
   - Adapt tests to the specific application
   - Update locators for input fields
   - Customize error detection

2. **For Safety:**
   - Test ONLY your own applications
   - Use a test environment
   - Document findings

3. **For Debugging:**
   - Review security reports
   - Check payload execution
   - Verify vulnerability detection logic

4. **For CI/CD:**
   - Run security tests in CI/CD
   - Fail builds on critical vulnerabilities
   - Track security metrics over time

## ⚠️ Legal Notice

**This test suite is intended only for:**
- ✅ Testing your own applications
- ✅ Testing applications with explicit permission
- ✅ Education and learning

**Do NOT use for:**
- ❌ Testing applications without authorization
- ❌ Hacking or unauthorized penetration testing
- ❌ Illegal activities

**The authors are not responsible for unauthorized use.**

---

**Good luck with Security Testing (use responsibly)! 🔒**
