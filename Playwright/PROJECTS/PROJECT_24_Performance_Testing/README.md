# Project 24: Performance Testing with Playwright

## 🎯 Objective
Complete measurement of web application performance: page load time, network timing, action timing, and comparison with baseline for regression detection.

## 📋 Requirements
- ✅ Page load time measurement
- ✅ Measurement of time for specific actions
- ✅ Network timing (via Performance API)
- ✅ Performance metrics collection
- ✅ Performance reporting
- ✅ Baseline and threshold management

## 🛠️ Technologies
- **Playwright** - Modern automation framework with native Performance API support
- **TypeScript** - Strongly typed JavaScript
- **Performance API** - Navigation Timing, Resource Timing
- **Node.js** - Runtime environment

## 📁 Project Structure

```
PROJECT_24_Performance_Testing/
├── package.json                  # Node.js dependencies
├── tsconfig.json                 # TypeScript configuration
├── playwright.config.ts          # Playwright configuration
├── README.md                     # This file
│
├── utils/                         # Utilities
│   ├── PerformanceMetrics.ts    # PerformanceMetrics class
│   ├── BaselineManager.ts        # BaselineManager class
│   └── PerformanceReporter.ts   # PerformanceReporter class
│
├── pages/                         # Page Object Pattern
│   ├── LoginPage.ts             # Login page
│   └── ProductsPage.ts          # Products page
│
├── tests/                         # Test suite
│   └── test_performance.spec.ts
│
├── reports/                       # Reports (generated)
│   ├── performance_report.json  # JSON report
│   └── performance_report.txt    # Text report
│
└── baselines/                     # Baseline metrics (generated)
    └── performance_baseline.json # Baseline storage
```

## ✨ Features

### 1. Page Load Time Measurement
- **DOM Content Loaded**: Time until DOMContentLoaded event
- **Load Complete**: Time until load event complete
- **DOM Interactive**: Time until DOM interactive
- **DOM Complete**: Time until DOM complete

### 2. Network Timing
- **DNS Lookup**: DNS resolution time
- **TCP Connection**: TCP connection time
- **Request Time**: Time to send request
- **Response Time**: Time to receive response
- **DOM Processing**: DOM processing time
- **Total Time**: Total page load time

### 3. Resource Timing
- **Duration**: Resource load duration
- **Size**: Resource transfer size
- **Type**: Resource type (script, css, img, etc.)
- **Slowest Resources**: Identification

### 4. Action Timing
- **Login Action**: Time for login
- **Navigation**: Time for navigation
- **Custom Actions**: Measurement for any action

### 5. Baseline Management
- **Save Baseline**: Save metrics as baseline
- **Compare**: Compare with baseline
- **Threshold**: Configurable threshold (default 15%)
- **Regression Detection**: Detect performance regressions

### 6. Reporting
- **JSON Report**: Structured data
- **Text Report**: Human-readable
- **HTML Report**: Playwright HTML
- **Summary**: Pass/Fail summary

## 📝 Deliverables
- ✅ Test suite for performance (page load, network, actions)
- ✅ Metrics collection (PerformanceMetrics class)
- ✅ Performance reports (JSON, text)
- ✅ Baseline definition and comparison (BaselineManager)
- ✅ Threshold management (configurable)
- ✅ Complete documentation

## ✅ Evaluation Criteria
- ✅ Correct metrics collected (page load, network, actions)
- ✅ Clear reports for performance (JSON, text)
- ✅ Functional baseline and threshold management
- ✅ Functional regression detection
- ✅ Test assertions for performance thresholds

## 🚀 Quick Start

### 1. Setup
```bash
cd PROJECTS/PROJECT_24_Performance_Testing
npm install
npx playwright install --with-deps chromium
mkdir -p reports baselines
```

### 2. Run Tests
```bash
# All performance tests
npm test

# Specific categories
npm run test:page-load
npm run test:network
npm run test:performance

# Smoke tests
npm run test:smoke
```

### 3. View Reports
```bash
# JSON report
cat reports/performance_report.json

# Text report
cat reports/performance_report.txt

# HTML report
npm run report
```

## 📚 Documentation

### Code Examples:

**Page Load Measurement:**
```typescript
import { PerformanceMetrics } from '../utils/PerformanceMetrics';

const metrics = new PerformanceMetrics(page);
await page.goto(url);
await page.waitForLoadState('networkidle');

const pageLoadMetrics = await metrics.getPageLoadTime();
console.log(`Load time: ${pageLoadMetrics.loadComplete.toFixed(2)}s`);
```

**Network Measurement:**
```typescript
const networkMetrics = await metrics.getNetworkTiming();
console.log(`DNS: ${networkMetrics.dns.toFixed(2)}s`);
console.log(`Total: ${networkMetrics.totalTime.toFixed(2)}s`);
```

**Baseline Comparison:**
```typescript
import { BaselineManager } from '../utils/BaselineManager';

const baselineManager = new BaselineManager();
const comparison = baselineManager.compareWithBaseline(
  'test_name',
  currentMetrics,
  15 // threshold percent
);

if (comparison.hasRegression) {
  console.warn('Performance regression detected!');
}
```

**Action Timing:**
```typescript
const actionTime = await metrics.measureActionTime(async () => {
  await loginPage.login('user', 'pass');
});
console.log(`Action time: ${actionTime.toFixed(2)}s`);
```

## 📊 Performance Metrics Details

### Navigation Timing API:
- **navigationStart**: Start of navigation
- **domContentLoaded**: DOMContentLoaded event
- **loadEventEnd**: Load event complete
- **domInteractive**: DOM interactive
- **domComplete**: DOM complete

### Network Timing:
- **DNS**: domainLookupEnd - domainLookupStart
- **TCP**: connectEnd - connectStart
- **Request**: responseStart - requestStart
- **Response**: responseEnd - responseStart

### Resource Timing:
- **getEntriesByType('resource')**: All resources
- **duration**: Load duration
- **transferSize**: Resource size
- **initiatorType**: Resource type

## 📊 Implementation Status

| Feature | Status | Notes |
|---------|--------|-------|
| Page Load Metrics | ✅ Implemented | Navigation Timing API |
| Network Timing | ✅ Implemented | Performance API |
| Resource Timing | ✅ Implemented | Resource Timing API |
| Action Timing | ✅ Implemented | Custom measurement |
| Baseline Manager | ✅ Implemented | JSON storage |
| Threshold Comparison | ✅ Implemented | Configurable |
| Reporting | ✅ Implemented | JSON, text |
| Test Suites | ✅ Implemented | Comprehensive tests |

## 💡 Tips

1. **For Accurate Metrics:**
   - Run tests in a controlled environment
   - Close background applications
   - Use consistent network conditions
   - Run multiple times for average

2. **For Baseline:**
   - Create baseline after optimizations
   - Update baseline after major changes
   - Use reasonable threshold (10-15%)

3. **For Debugging:**
   - Check network tab in browser
   - Review resource timing
   - Check server response times

4. **For CI/CD:**
   - Set thresholds for CI/CD
   - Fail tests if performance degrades
   - Track metrics over time

## 🔧 Configuration

### Performance Thresholds:
```typescript
// In tests
expect(pageLoadMetrics.loadComplete).toBeLessThan(5); // 5 seconds
expect(networkMetrics.totalTime).toBeLessThan(5);
expect(actionTime).toBeLessThan(3);
```

### Baseline Threshold:
```typescript
// Default 15% threshold
const comparison = baselineManager.compareWithBaseline(
  'test_name',
  currentMetrics,
  15 // percent
);
```

---

**Good luck with Performance Testing! 🚀**
