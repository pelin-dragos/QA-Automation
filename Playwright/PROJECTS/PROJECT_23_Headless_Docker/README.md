# Project 23: Headless Browser Testing + Docker

## 🎯 Objective
Full containerization of Playwright tests in Docker containers with headless browser configuration and Docker Compose orchestration.

## 📋 Requirements
- ✅ Dockerfile for Playwright tests
- ✅ Docker Compose for orchestration
- ✅ Headless browser configuration
- ✅ Volume mounting for results
- ✅ Network configuration for services
- ✅ Multi-container setup (tests container)

## 🛠️ Technologies
- **Docker** - Containerization
- **Docker Compose** - Orchestration
- **Playwright** - Modern automation framework with native headless support
- **TypeScript** - Strongly typed JavaScript
- **Node.js** - Runtime environment

## 📁 Project Structure

```
PROJECT_23_Headless_Docker/
├── Dockerfile                    # Docker image definition
├── docker-compose.yml            # Docker Compose orchestration
├── .dockerignore                 # Files to exclude from build
├── Makefile                      # Make commands for Docker
├── package.json                  # Node.js dependencies
├── tsconfig.json                 # TypeScript configuration
├── playwright.config.ts          # Playwright configuration (Docker optimized)
├── README.md                     # This file
│
├── scripts/                       # Scripts
│   └── run_docker_tests.sh      # Bash script for Docker
│
├── pages/                         # Page Object Pattern
│   ├── LoginPage.ts             # Login page
│   └── ProductsPage.ts           # Products page
│
├── tests/                         # Test suite
│   └── test_docker_headless.spec.ts
│
├── test-results/                 # Test results (generated, mounted volume)
├── playwright-report/            # HTML reports (generated, mounted volume)
├── screenshots/                   # Screenshots (generated, mounted volume)
└── logs/                          # Logs (generated, mounted volume)
```

## ✨ Features

### 1. Dockerfile
- **Base Image**: mcr.microsoft.com/playwright (official Playwright image)
- **Playwright Browsers**: Chromium with dependencies
- **Dependencies**: Node.js packages from package.json
- **Headless Configuration**: Environment variables

### 2. Docker Compose
- **Service**: playwright-tests container
- **Volumes**: Mount for results, reports, screenshots, logs
- **Network**: Isolated network for services
- **Environment**: CI, DOCKER, HEADLESS variables

### 3. Headless Browser
- **Automatic Headless**: Active in Docker
- **Optimized Options**: no-sandbox, disable-dev-shm-usage, disable-gpu
- **Window Size**: Consistent for screenshots

### 4. Volume Mounting
- **Results**: Accessible after execution
- **Reports**: HTML reports persist
- **Screenshots**: Failure screenshots persist
- **Logs**: Log files persist

### 5. Make Commands
- **build**: Build Docker image
- **test**: Run tests in Docker
- **test-smoke**: Run smoke tests
- **shell**: Shell in container
- **clean**: Clean up containers and images

## 📝 Deliverables
- ✅ Functional Dockerfile (Playwright official image)
- ✅ Complete docker-compose.yml (orchestration + volumes)
- ✅ Documentation for build and run
- ✅ Deployment instructions (README + Makefile)
- ✅ Automation scripts (run_docker_tests.sh)

## ✅ Evaluation Criteria
- ✅ Full containerization (Dockerfile + docker-compose)
- ✅ Tests run in Docker (headless mode)
- ✅ Simple setup with docker-compose (one command)
- ✅ Results accessible after run (volume mounting)
- ✅ Correct headless browser configuration
- ✅ Functional network configuration

## 🚀 Quick Start

### 1. Prerequisites
```bash
# Verify Docker
docker --version
docker-compose --version
```

### 2. Build and Run
```bash
cd PROJECTS/PROJECT_23_Headless_Docker

# Build image
docker-compose build

# Run tests
docker-compose up --abort-on-container-exit
```

### 3. With Make (Simplified)
```bash
# Build
make build

# Run tests
make test

# Run smoke tests
make test-smoke

# Shell in container
make shell
```

## 📚 Documentation

### Code Examples:

**Build and Run:**
```bash
# Build image
docker-compose build

# Run tests
docker-compose up --abort-on-container-exit

# Or with Make
make build
make test
```

**Custom Command:**
```bash
# Run specific tests
docker-compose run --rm playwright-tests npm run test:smoke

# Or with Make
make test-custom ARGS="--grep @regression"
```

**Shell in Container:**
```bash
# Debug in container
docker-compose run --rm playwright-tests /bin/bash

# Or with Make
make shell
```

## 🐳 Docker Setup Details

### Dockerfile Components:
1. **Base Image**: mcr.microsoft.com/playwright (official)
2. **Node.js**: Included in base image
3. **Playwright Browsers**: Chromium with dependencies
4. **Dependencies**: npm ci for production install
5. **Working Directory**: /app
6. **Default Command**: npm run test:docker

### Docker Compose Services:
- **playwright-tests**: Main test container

### Volumes:
- `./test-results:/app/test-results` - Test results
- `./playwright-report:/app/playwright-report` - HTML reports
- `./screenshots:/app/screenshots` - Screenshots
- `./logs:/app/logs` - Log files

## 📊 Implementation Status

| Feature | Status | Notes |
|---------|--------|-------|
| Dockerfile | ✅ Implemented | Playwright official image |
| Docker Compose | ✅ Implemented | Orchestration + volumes |
| Headless Mode | ✅ Implemented | Automatic in Docker |
| Volume Mounting | ✅ Implemented | Results, reports, screenshots |
| Network Config | ✅ Implemented | Isolated network |
| Make Commands | ✅ Implemented | Simplified operations |
| Test Suites | ✅ Implemented | Docker-optimized tests |

## 💡 Tips

1. **For Docker:**
   - Use `docker-compose` for orchestration
   - Volume mounting for accessible results
   - Headless mode for performance

2. **For Debugging:**
   - Use `make shell` for shell in container
   - Check logs: `make logs`
   - Verify Playwright: `npx playwright --version` in container

3. **For Performance:**
   - Build cache for faster builds
   - Parallel execution with multiple containers
   - Headless mode for speed

4. **For Deployment:**
   - Build image once
   - Run tests in isolated containers
   - Results persist via volumes

## 🔧 Configuration

### Environment Variables:
```bash
# In docker-compose.yml
CI=true
DOCKER=true
HEADLESS=true
BASE_URL=https://www.saucedemo.com
```

### Headless Options:
```typescript
// In playwright.config.ts
launchOptions: {
  args: [
    '--no-sandbox',
    '--disable-setuid-sandbox',
    '--disable-dev-shm-usage',
    '--disable-gpu',
  ],
}
```

## 🧪 Local Testing (Headless)

```bash
# Run tests locally in headless mode
HEADLESS=true npm test

# Or set in playwright.config.ts
# headless: true
```

---

**Good luck with Docker containerization! 🐳**
