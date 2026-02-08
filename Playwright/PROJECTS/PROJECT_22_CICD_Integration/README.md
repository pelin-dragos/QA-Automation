# Project 22: CI/CD Integration (GitHub Actions/Jenkins)

## 🎯 Objective
Full integration into CI/CD pipeline with automated reporting, matrix testing, and notifications for test results.

## 📋 Requirements
- ✅ GitHub Actions workflow or Jenkins pipeline
- ✅ Automated test execution on commit/push
- ✅ Generate and publish reports
- ✅ Notifications for test results
- ✅ Artifacts management (screenshots, logs)
- ✅ Matrix testing (multiple Node.js versions/browsers)

## 🛠️ Technologies
- **Playwright** - Modern automation framework
- **TypeScript** - Strongly typed JavaScript
- **GitHub Actions** - CI/CD workflows
- **Jenkins** - CI/CD pipeline
- **Node.js** - Runtime environment

## 📁 Project Structure

```
PROJECT_22_CICD_Integration/
├── .github/
│   └── workflows/                # GitHub Actions workflows
│       ├── ci-tests.yml          # CI tests (push/PR)
│       ├── matrix-tests.yml      # Matrix testing (OS/Node.js)
│       └── nightly-tests.yml     # Nightly regression
│
├── scripts/                       # CI/CD scripts
│   ├── run_tests.sh             # Bash script (Linux/Mac)
│   └── run_tests.ps1            # PowerShell script (Windows)
│
├── pages/                         # Page Object Pattern
│   ├── LoginPage.ts             # Login page
│   └── ProductsPage.ts          # Products page
│
├── tests/                         # Test suite
│   └── test_cicd_integration.spec.ts
│
├── package.json                  # Node.js dependencies
├── tsconfig.json                 # TypeScript configuration
├── playwright.config.ts          # Playwright configuration (CI/CD optimized)
├── Jenkinsfile                   # Jenkins pipeline definition
└── README.md                     # This file
```

## ✨ Features

### 1. GitHub Actions Workflows
- **ci-tests.yml**: CI tests for push/PR
- **matrix-tests.yml**: Matrix testing on multiple OS/Node.js
- **nightly-tests.yml**: Nightly regression tests

### 2. Jenkins Pipeline
- **Jenkinsfile**: Complete pipeline definition
- **Multi-stage**: Checkout → Setup → Test → Report
- **Post-build**: Artifacts, notifications, reports

### 3. CI/CD Optimizations
- **Headless Mode**: Automatic in CI/CD environments
- **Environment Detection**: Detects CI/CD environment
- **Optimized Waits**: Longer waits in CI for stability
- **Single Worker**: Single worker in CI for stability

### 4. Reporting
- **HTML Reports**: Playwright HTML (self-contained)
- **JUnit XML**: For tooling integration
- **JSON Reports**: For programmatic access

### 5. Artifacts Management
- **Screenshots**: Automatic upload for failed tests
- **Reports**: Upload for all report types
- **Retention**: Configurable retention days (30 days)

### 6. Matrix Testing
- **Multiple OS**: Ubuntu, Windows, macOS
- **Multiple Node.js**: 18, 20
- **Parallel Execution**: Fail-fast disabled for coverage

## 📝 Deliverables
- ✅ Functional CI/CD pipeline (GitHub Actions + Jenkins)
- ✅ Workflow files (3 GitHub Actions workflows)
- ✅ Fully configured Jenkinsfile
- ✅ Pipeline documentation
- ✅ Examples of triggered runs (workflow_dispatch, schedule)
- ✅ Scripts for local CI/CD simulation

## ✅ Evaluation Criteria
- ✅ Pipeline runs automatically (on push/PR)
- ✅ Reports generated and accessible (artifacts)
- ✅ Functional notifications (email, PR comments)
- ✅ Correct matrix testing setup (OS/Node.js matrix)
- ✅ Headless mode in CI/CD
- ✅ Functional artifacts management

## 🚀 Quick Start

### 1. Install Dependencies

```bash
cd PROJECTS/PROJECT_22_CICD_Integration
npm install
npx playwright install --with-deps chromium
```

### 2. GitHub Actions

**Activation:**
```bash
# Workflow files are in .github/workflows/
# Commit and push for automatic activation
git add .github/workflows/
git commit -m "Add CI/CD workflows"
git push
```

**Verification:**
- Go to GitHub repository → Actions tab
- View workflow runs

### 3. Jenkins

**Setup:**
1. Create Pipeline Job
2. Configure SCM (Git)
3. Script Path: `Jenkinsfile`
4. Build Now

### 4. Local CI/CD Simulation

```bash
# Set CI environment
export CI=true

# Run tests (headless mode)
npm run test:ci
```

## 📚 Documentation

### GitHub Actions Workflows:

**ci-tests.yml:**
- Trigger: push/PR on main/develop
- Steps: Checkout → Setup → Install → Test → Report
- Artifacts: HTML report, screenshots, test results

**matrix-tests.yml:**
- Trigger: push/PR, schedule, manual
- Matrix: OS × Node.js
- Artifacts: Reports per combination

**nightly-tests.yml:**
- Trigger: Schedule (daily 3 AM), manual
- Full regression suite
- PR comments on failure

### Jenkins Pipeline:

**Stages:**
1. Checkout: Git checkout
2. Setup: Install dependencies
3. Install Playwright: Browser setup
4. Run Tests: Playwright execution
5. Generate Reports: HTML, JUnit

**Post-build:**
- Publish JUnit results
- Archive artifacts
- Publish HTML report
- Email notifications

## 🔧 CI/CD Features

### Environment Detection
```typescript
// Automatic detection in playwright.config.ts
const isCI = !!process.env.CI || !!process.env.GITHUB_ACTIONS || !!process.env.JENKINS_URL;
```

### Headless Mode
```typescript
// Automatic headless in CI/CD
use: {
  headless: isCI,
}
```

### Matrix Testing
```yaml
# GitHub Actions matrix
strategy:
  matrix:
    os: [ubuntu-latest, windows-latest, macos-latest]
    node-version: ['18', '20']
```

## 📊 Implementation Status

| Feature | Status | Notes |
|---------|--------|-------|
| GitHub Actions | ✅ Implemented | 3 workflows |
| Jenkins Pipeline | ✅ Implemented | Complete Jenkinsfile |
| Matrix Testing | ✅ Implemented | OS × Node.js matrix |
| Headless Mode | ✅ Implemented | Automatic in CI |
| Reports Generation | ✅ Implemented | HTML, JUnit, JSON |
| Artifacts Upload | ✅ Implemented | Screenshots, reports |
| Notifications | ✅ Implemented | Email, PR comments |

## 💡 Tips

1. **For GitHub Actions:**
   - Workflow files must be in `.github/workflows/`
   - YAML syntax must be correct
   - Check Actions tab for errors

2. **For Jenkins:**
   - Install required plugins (HTML Publisher, JUnit)
   - Configure email notifications
   - Check console output for debugging

3. **For Matrix Testing:**
   - Fail-fast: false for full coverage
   - Exclude unnecessary combinations for speed
   - Artifacts per combination for review

4. **For Local Testing:**
   - Use `export CI=true` for simulation
   - Test scripts before commit
   - Verify reports generation

## 🧪 Test Scripts

### Run Tests Locally (CI Mode):
```bash
# Linux/Mac
./scripts/run_tests.sh

# Windows
.\scripts\run_tests.ps1

# With markers
./scripts/run_tests.sh smoke
./scripts/run_tests.sh regression
```

### NPM Scripts:
```bash
# Run all tests
npm test

# Run CI tests
npm run test:ci

# Run smoke tests
npm run test:smoke

# Run regression tests
npm run test:regression
```

---

**Good luck with CI/CD integration! 🚀**
