# Copilot / AI Agent Instructions — OrangeHRM Automation

This project is a Java-based Playwright + Cucumber BDD automation framework. The goal of this file is to give an AI coding agent the minimal, actionable context to be productive immediately.

Summary
- Stack: Java 21, Playwright (Java), Cucumber (v7.x), TestNG, Maven
- Structure: `src/test/java` (pages, steps, tests, utils), `src/test/resources` (config.json, features, data)

Key concepts & why
- Page Object Model: `src/test/java/com/spurqlabs/pages` contains POM classes (example: `LoginPage.java`). POMs encapsulate locators and actions; reuse POM methods in step defs.
- Singleton Playwright driver: `PlaywrightDriver.java` manages a single Playwright/Browser/Context/Page. Use `PlaywrightDriver.getPage()` in POMs and `initDriver()`/`quitDriver()` in Cucumber hooks.
- Externalized configuration & data: `ConfigReader` reads `src/test/resources/config.json`; test data lives in `src/test/resources/data/*.json` and is read via `TestDataReader`.
- Cucumber + TestNG runner: `TestRunner.java` (extends `AbstractTestNGCucumberTests`) defines `features`, `glue`, `tags` and report plugins. `testng.xml` runs this runner via Maven Surefire.

Where to look (examples)
- Config: `src/test/resources/config.json` — keys: `baseUrl`, `timeout`, `headless`, `slowMo`, `browser`.
- Driver: `src/test/java/com/spurqlabs/utils/PlaywrightDriver.java` — initialize/quit lifecycle and default timeout/viewport.
- POM example: `src/test/java/com/spurqlabs/pages/LoginPage.java` — locator style and helper methods (navigate, login, checks).
- Steps: `src/test/java/com/spurqlabs/steps/LoginSteps.java` — Cucumber hooks (`@Before`, `@After`) call `PlaywrightDriver` and reuse `TestDataReader`.
- Runner: `src/test/java/com/spurqlabs/tests/TestRunner.java` and `src/test/resources/testng.xml`.

How to run (developer workflows)
- Run full test suite (uses `testng.xml`):
  - `mvn test`
- Test runner notes:
  - `TestRunner` has `tags = "@smoke"` in `@CucumberOptions`. To run a different tag or all scenarios adjust the `tags` value or create a new runner class.
  - Reports are written to `target/cucumber-reports/` (`html`, `json`, `junit`).
- Debugging locally:
  - `config.json` default `headless` is `false` and `slowMo` is `100` — this facilitates interactive debugging in the IDE.
  - To step through a test, run `TestRunner` from your IDE (IntelliJ/Eclipse) with breakpoints in step definitions or POM methods.

Project-specific conventions & patterns
- Locator preference: code uses stable CSS locators (e.g., `input[name='username']`), avoid adding brittle XPath selectors.
- Timeouts: `ConfigReader.getTimeout()` sets Playwright default timeouts — use explicit `waitForSelector` calls when checking for critical elements (see `LoginPage.isDashboardDisplayed`).
- Data and secrets: test credentials and examples live in JSON under `src/test/resources/data` (see `loginData.json`). Do not hardcode credentials elsewhere.
- Code layout:
  - `pages/` — Page objects (stateless wrappers around `Page`)
  - `steps/` — Cucumber step defs and hooks (use `@Before`/`@After` for driver lifecycle)
  - `tests/` — Test runners
  - `utils/` — helpers (config, driver, test data)

Integration points & dependencies
- Maven (`pom.xml`) manages dependencies: Playwright, Cucumber, TestNG, Gson. Use `mvn test` — no extra build steps required.
- Playwright browser binaries: Playwright Java downloads/uses browsers at runtime. If you encounter missing browsers on CI, ensure the runner environment allows Playwright to install or pre-install browsers.

What an AI should do (practical guidance)
- When adding tests: create a Gherkin in `src/test/resources/features`, add/extend a POM in `pages/` for new page interactions, add step defs in `steps/`, and wire a runner or update `TestRunner` tags.
- Always reference `ConfigReader` and `TestDataReader` rather than hardcoding URLs/credentials/timeouts.
- Tests should use POM methods (e.g., `LoginPage.login(...)`) rather than duplicating locator logic.
- Keep changes minimal and consistent with existing style (no new frameworks, follow package layout).

Quick file references
- Runner: `src/test/java/com/spurqlabs/tests/TestRunner.java`
- Hooks/steps: `src/test/java/com/spurqlabs/steps/LoginSteps.java`
- POM example: `src/test/java/com/spurqlabs/pages/LoginPage.java`
- Driver: `src/test/java/com/spurqlabs/utils/PlaywrightDriver.java`
- Config: `src/test/resources/config.json`

If anything is unclear
- Tell me which area you want expanded (e.g., CI instructions, how to run non-`@smoke` tags, Playwright browser install on CI) and I will update this file.

---
Generated from repository inspection on project root; preserve this file when committing further framework changes.
