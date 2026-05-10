# OrangeHRM Test Automation Framework

> End-to-end test automation framework for [OrangeHRM Demo](https://opensource-demo.orangehrmlive.com) built with **Playwright + Cucumber + Java** following **BDD** and **TDD** principles.

---

## 📋 Table of Contents

- [Overview](#overview)
- [Tech Stack](#tech-stack)
- [Project Structure](#project-structure)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Configuration](#configuration)
- [Running Tests](#running-tests)
- [Test Scenarios](#test-scenarios)
- [Locator Strategy](#locator-strategy)
- [Reports](#reports)
- [Contributing](#contributing)
- [Demo Credentials](#demo-credentials)

---

## Overview

This framework automates functional testing of the **OrangeHRM Human Resource Management** demo application. It uses **Behavior-Driven Development (BDD)** with Cucumber for readable test scenarios and **Test-Driven Development (TDD)** principles for robust test design.

### Key Features

- ✅ BDD with Gherkin feature files (human-readable scenarios)
- ✅ Page Object Model (POM) design pattern
- ✅ Cross-browser testing (Chromium, Firefox, WebKit)
- ✅ Data-driven testing with Scenario Outline
- ✅ Tag-based test filtering (`@smoke`, `@regression`, `@security`)
- ✅ HTML & JSON test reports
- ✅ Headless and headed execution modes
- ✅ Screenshot on failure
- ✅ Parallel test execution support

---

## Tech Stack

| Tool | Version | Purpose |
|---|---|---|
| Java | 17+ | Core programming language |
| Playwright | 1.43.0 | Browser automation |
| Cucumber | 7.15.0 | BDD framework |
| JUnit | 5.10.2 | Test runner |
| Maven | 3.9+ | Build & dependency management |
| Extent Reports | 5.x | Test reporting |

---

## Project Structure

```
orangehrm-automation/
│
├── src/
│   ├── main/
│   │   └── java/
│   │       └── pages/
│   │           ├── BasePage.java          # Common page actions
│   │           ├── LoginPage.java         # Login page object
│   │           ├── DashboardPage.java     # Dashboard page object
│   │           └── ForgotPasswordPage.java
│   │
│   └── test/
│       ├── java/
│       │   ├── runner/
│       │   │   └── TestRunner.java        # Cucumber JUnit runner
│       │   ├── steps/
│       │   │   ├── LoginSteps.java        # Login step definitions
│       │   │   └── CommonSteps.java       # Shared step definitions
│       │   ├── tests/
│       │   │   └── LoginPageTest.java     # TDD unit tests
│       │   └── utils/
│       │       ├── BaseTest.java          # Browser setup/teardown
│       │       ├── ConfigReader.java      # Config file reader
│       │       └── ScreenshotUtil.java    # Screenshot helper
│       │
│       └── resources/
│           ├── features/
│           │   └── login/
│           │       └── login.feature      # Login BDD scenarios
│           └── config.properties          # Test configuration
│
├── reports/                               # Generated test reports
├── screenshots/                           # Failure screenshots
├── pom.xml                                # Maven dependencies
└── README.md
```

---

## Prerequisites

Before you begin, ensure you have the following installed:

- [Java JDK 17+](https://adoptium.net/)
- [Apache Maven 3.9+](https://maven.apache.org/download.cgi)
- [Git](https://git-scm.com/)

Verify installations:

```bash
java -version
mvn -version
git --version
```

---

## Installation

### 1. Clone the repository

```bash
git clone https://github.com/your-username/orangehrm-automation.git
cd orangehrm-automation
```

### 2. Install dependencies

```bash
mvn clean install -DskipTests
```

### 3. Install Playwright browsers

```bash
mvn exec:java -e -D exec.mainClass=com.microsoft.playwright.CLI -D exec.args="install"
```

---

## Configuration

Edit `src/test/resources/config.properties`:

```properties
# Application URL
base.url=https://opensource-demo.orangehrmlive.com

# Browser settings
browser=chromium
headless=false
slow.motion=0

# Test credentials
admin.username=Admin
admin.password=admin123

# Timeouts (milliseconds)
default.timeout=30000
navigation.timeout=60000
```

---

## Running Tests

### Run all tests

```bash
mvn test
```

### Run by tag

```bash
# Smoke tests only
mvn test -Dcucumber.filter.tags="@smoke"

# Regression suite
mvn test -Dcucumber.filter.tags="@regression"

# Negative tests
mvn test -Dcucumber.filter.tags="@negative"

# Security tests
mvn test -Dcucumber.filter.tags="@security"

# Validation tests
mvn test -Dcucumber.filter.tags="@validation"

# Exclude a tag
mvn test -Dcucumber.filter.tags="not @wip"
```

### Run in headless mode

```bash
mvn test -Dheadless=true
```

### Run on a specific browser

```bash
mvn test -Dbrowser=firefox
mvn test -Dbrowser=webkit
mvn test -Dbrowser=chromium
```

### Run TDD unit tests only

```bash
mvn test -Dtest=LoginPageTest
```

---

## Test Scenarios

### Login Module

| ID | Scenario | Tags |
|---|---|---|
| TC-01 | Login page should display OrangeHRM logo | `@smoke` |
| TC-02 | Admin should login with valid credentials | `@smoke @regression` |
| TC-03 | Login with wrong password should show error | `@regression @negative` |
| TC-04 | Login with wrong username should show error | `@regression @negative` |
| TC-05 | Empty username should show required error | `@regression @validation` |
| TC-06 | Empty password should show required error | `@regression @validation` |
| TC-07 | Both fields empty should show required errors | `@regression @validation` |
| TC-08 | Password field should be masked by default | `@regression @security` |
| TC-09 | Forgot password link should navigate to reset page | `@smoke @regression` |
| TC-10 | Logged in user should be able to logout | `@regression` |
| TC-11 | Multiple invalid login attempts (data-driven) | `@regression @negative` |
| TC-12 | SQL injection in username should be blocked | `@regression @security` |
| TC-13 | XSS script in username should be sanitized | `@regression @security` |

### Feature File Sample

```gherkin
@smoke @regression
Scenario: Admin should login with valid credentials
  Given the user is on the OrangeHRM login page
  When the user enters username "Admin" and password "admin123"
  And the user clicks the login button
  Then the user should be redirected to the dashboard
  And the dashboard header "Dashboard" should be visible
```

---

## Locator Strategy

This framework follows Playwright's recommended locator priority:

```
✅ BEST     →  getByRole()          Semantic, accessibility-friendly
✅ GOOD     →  getByLabel()         For form fields with labels
✅ GOOD     →  getByPlaceholder()   For input fields with placeholders
⚠️  OKAY    →  getByText()          Can break if text changes
⚠️  OKAY    →  CSS Selector         When semantic locators unavailable
❌ AVOID    →  XPath                Verbose and fragile
```

### OrangeHRM Locator Reference

```java
// Login page
page.getByPlaceholder("Username");                          // Username field
page.getByPlaceholder("Password");                          // Password field
page.getByRole(AriaRole.BUTTON, .setName("Login"));         // Login button
page.getByText("Forgot your password?");                    // Forgot link
page.getByAltText("company-branding");                      // Logo
page.locator(".oxd-alert-content p");                       // Error message
page.locator(".oxd-input-field-error-message");             // Field errors
```

---

## Reports

After test execution, reports are generated in the `reports/` folder.

### HTML Report

```
reports/cucumber-report.html
```

Open in any browser for a full visual test report.

### JSON Report

```
reports/cucumber.json
```

Used for CI/CD integration and third-party reporting tools.

### Console Output

Enable pretty printing in `TestRunner.java`:

```java
plugin = { "pretty", "html:reports/cucumber-report.html", "json:reports/cucumber.json" }
```

---

## CI/CD Integration

### GitHub Actions (`.github/workflows/test.yml`)

```yaml
name: OrangeHRM Automation Tests

on:
  push:
    branches: [ main, develop ]
  pull_request:
    branches: [ main ]
  schedule:
    - cron: '0 8 * * *'   # Daily at 8 AM

jobs:
  test:
    runs-on: ubuntu-latest

    steps:
      - name: Checkout code
        uses: actions/checkout@v3

      - name: Set up Java 17
        uses: actions/setup-java@v3
        with:
          java-version: '17'
          distribution: 'temurin'

      - name: Install Playwright browsers
        run: mvn exec:java -e -D exec.mainClass=com.microsoft.playwright.CLI -D exec.args="install --with-deps"

      - name: Run smoke tests
        run: mvn test -Dcucumber.filter.tags="@smoke" -Dheadless=true

      - name: Upload test report
        uses: actions/upload-artifact@v3
        if: always()
        with:
          name: test-reports
          path: reports/
```

---

## Maven Dependencies (`pom.xml`)

```xml
<dependencies>

  <!-- Playwright -->
  <dependency>
    <groupId>com.microsoft.playwright</groupId>
    <artifactId>playwright</artifactId>
    <version>1.43.0</version>
  </dependency>

  <!-- Cucumber Java -->
  <dependency>
    <groupId>io.cucumber</groupId>
    <artifactId>cucumber-java</artifactId>
    <version>7.15.0</version>
  </dependency>

  <!-- Cucumber JUnit 5 -->
  <dependency>
    <groupId>io.cucumber</groupId>
    <artifactId>cucumber-junit-platform-engine</artifactId>
    <version>7.15.0</version>
  </dependency>

  <!-- JUnit 5 -->
  <dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter</artifactId>
    <version>5.10.2</version>
    <scope>test</scope>
  </dependency>

</dependencies>
```

---

## Demo Credentials

| Field | Value |
|---|---|
| URL | https://opensource-demo.orangehrmlive.com |
| Username | `Admin` |
| Password | `admin123` |

> **Note:** This is a public demo site. Credentials are shared and data may be reset periodically.

---

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/add-dashboard-tests`)
3. Write your tests following existing conventions
4. Commit your changes (`git commit -m 'Add dashboard test scenarios'`)
5. Push to the branch (`git push origin feature/add-dashboard-tests`)
6. Open a Pull Request

### Code Conventions

- Follow Page Object Model for all page interactions
- Use `getByRole()` or `getByPlaceholder()` as first-choice locators
- Tag all scenarios appropriately (`@smoke`, `@regression`, etc.)
- Add `@DisplayName` to all TDD test methods
- Keep step definitions thin — logic lives in page objects

---

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

---

## Author

**Your Name**
- GitHub: [Nisali760](https://github.com/Nisali760)

> Built with Playwright + Cucumber + Java | OrangeHRM Demo Application
