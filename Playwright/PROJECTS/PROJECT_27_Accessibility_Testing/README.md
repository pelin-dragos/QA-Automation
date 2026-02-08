# Project 27: Accessibility Testing Automation

## 🎯 Objective
Full automation of web accessibility checks: ARIA labels, keyboard navigation, focus management, alt text, and integration with axe-core for comprehensive WCAG-compliant scanning.

## 📋 Requirements
- ✅ Tests for ARIA labels
- ✅ Tests for keyboard navigation
- ✅ Tests for screen reader compatibility (via ARIA)
- ✅ Color contrast verification (via axe-core)
- ✅ Focus management testing
- ✅ Alt text for images

## 🛠️ Technologies
- **Playwright** - Modern automation framework with native accessibility support
- **TypeScript** - Strongly typed JavaScript
- **axe-core** - Accessibility testing engine (CDN injection)
- **WCAG 2.1** - Web Content Accessibility Guidelines
- **Node.js** - Runtime environment

## 📁 Project Structure

```
PROJECT_27_Accessibility_Testing/
├── package.json                  # Node.js dependencies
├── tsconfig.json                 # TypeScript configuration
├── playwright.config.ts          # Playwright configuration
├── README.md                     # This file
│
├── utils/                         # Utilities
│   ├── AccessibilityChecker.ts  # Accessibility checks (ARIA, keyboard, focus, axe-core)
│   └── AccessibilityReporter.ts # AccessibilityReporter
│
├── pages/                         # Page Object Pattern
│   └── LoginPage.ts             # Login page
│
├── tests/                         # Test suite
│   └── test_accessibility.spec.ts
│
└── reports/                       # Reports (generated)
    ├── accessibility_report.json # JSON report
    └── accessibility_report.txt  # Text report
```

## ✨ Features

### 1. ARIA Labels Testing
- **ARIA Label Check**: Check for aria-label presence
- **aria-labelledby Check**: Check aria-labelledby
- **Element Accessibility**: Check whether elements are accessible

### 2. Keyboard Navigation Testing
- **Tab Navigation**: Navigate through page with Tab key
- **Focusable Elements**: Check focusable elements
- **Keyboard Accessibility**: Full keyboard accessibility test

### 3. Focus Management Testing
- **Focus Indicators**: Check visible focus indicators
- **Focus Visibility**: Check focus styling
- **Focus Order**: Check logical focus order

### 4. Alt Text Testing
- **Image Alt Text**: Check alt text on images
- **Missing Alt Detection**: Detect images without alt text
- **Empty Alt Detection**: Detect empty alt text

### 5. axe-core Integration
- **axe-core Injection**: Inject axe-core into page (CDN)
- **WCAG Compliance**: Scanning per WCAG 2.1 Level AA
- **Violation Detection**: Detect and report violations
- **Comprehensive Analysis**: Full page analysis

### 6. Accessibility Reporting
- **JSON Report**: Structured accessibility data
- **Text Report**: Human-readable format
- **HTML Report**: Playwright HTML integration
- **Violation Summary**: Count and severity

## 📝 Deliverables
- ✅ Test suite for accessibility (ARIA, keyboard, focus, alt text)
- ✅ Integration with axe-core (JavaScript injection)
- ✅ Accessibility report (JSON, text)
- ✅ Documentation on WCAG compliance
- ✅ Accessibility utilities (AccessibilityChecker class)
- ✅ Reporting system (AccessibilityReporter)

## ✅ Evaluation Criteria
- ✅ Tests for multiple accessibility aspects (ARIA, keyboard, focus, alt text)
- ✅ Clear report for accessibility issues (JSON, text)
- ✅ Integration with accessibility tools (axe-core)
- ✅ Functional WCAG compliance checking

## 🚀 Quick Start

### 1. Setup
```bash
cd PROJECTS/PROJECT_27_Accessibility_Testing
npm install
npx playwright install --with-deps chromium
mkdir -p reports
```

### 2. Run Tests
```bash
# All accessibility tests
npm test

# Specific categories
npm run test:aria
npm run test:keyboard
npm run test:focus
npm run test:alt
npm run test:axe
npm run test:accessibility

# Smoke tests
npm run test:smoke
```

### 3. View Reports
```bash
# JSON report
cat reports/accessibility_report.json

# Text report
cat reports/accessibility_report.txt

# HTML report
npm run report
```

## 📚 Documentation

### Code Examples:

**ARIA Labels Check:**
```typescript
import { AccessibilityChecker } from '../utils/AccessibilityChecker';

const checker = new AccessibilityChecker(page);
const result = await checker.checkARIALabels('#username');
console.log(result.message);
```

**Keyboard Navigation:**
```typescript
const result = await checker.navigateWithKeyboard();
console.log(`Found ${result.details?.focusableElements?.length} focusable elements`);
```

**axe-core Analysis:**
```typescript
const result = await checker.runAxeAnalysis();
const violations = result.details?.violations || [];
console.log(`Found ${violations.length} accessibility violations`);
```

**Alt Text Check:**
```typescript
const result = await checker.checkAltText();
console.log(result.message);
```

## 📊 Accessibility Checks Details

### ARIA Labels:
- `aria-label`: Direct label
- `aria-labelledby`: Reference to label element
- Natural accessibility: button, a, input tags

### Keyboard Navigation:
- Tab key for navigation
- Focusable elements check
- Logical tab order verification

### Focus Management:
- Focus indicator visibility
- CSS outline/box-shadow checks
- Focus styling verification

### Alt Text:
- `alt` attribute presence
- Empty alt detection
- Descriptive alt text verification

### axe-core:
- WCAG 2.1 Level A, AA, AAA
- Multiple violation types
- Impact levels: critical, serious, moderate, minor

## 📊 Implementation Status

| Feature | Status | Notes |
|---------|--------|-------|
| ARIA Labels | ✅ Implemented | aria-label, aria-labelledby |
| Keyboard Navigation | ✅ Implemented | Tab navigation, focusable |
| Focus Management | ✅ Implemented | Focus indicators |
| Alt Text | ✅ Implemented | Image alt text checks |
| axe-core Integration | ✅ Implemented | CDN injection |
| WCAG Compliance | ✅ Implemented | Level AA default |
| Reporting | ✅ Implemented | JSON, text |
| Test Suites | ✅ Implemented | Comprehensive tests |

## 💡 Tips

1. **For Accurate Checks:**
   - Run tests on production-like pages
   - Check multiple pages
   - Verify dynamic content accessibility

2. **For axe-core:**
   - Requires internet for CDN
   - Inject before interactions
   - Review violations carefully

3. **For Keyboard Navigation:**
   - Test full tab order
   - Verify Enter/Space work
   - Check Escape key behavior

4. **For WCAG Compliance:**
   - Use WCAG 2.1 Level AA as standard
   - Fix critical violations first
   - Document accessibility improvements

---

**Good luck with Accessibility Testing! ♿**
