# Quality gate demonstration for the mentor

## 1. Show the configuration

Open `.prettierrc.json`, `.prettierignore`, `eslint.config.js` and the scripts in `package.json`. Explain that Prettier checks formatting while ESLint checks JavaScript correctness. `eslint-config-prettier` prevents overlapping formatting rules.

## 2. Show the job dependencies

Open `.github/workflows/ci.yml`. Both `api-tests` and `ui-tests` declare `needs: quality-gates`. The linters run in check mode; CI does not silently fix the code. ESLint still runs when Prettier fails, provided `npm ci` succeeded.

## 3. Show the deliberate failing run

The demonstration uses a temporary root-level JavaScript file containing:

```js
const unusedQualityGateDemo = true;
```

For the failure demonstration the file also deliberately omits formatting spaces and the semicolon. This triggers both a Prettier failure and ESLint's `no-unused-vars` rule. Show the two failed steps, the failed gate, and both test jobs marked **Skipped**. This is an intentional validation of the gate, not a failure of the final solution.

The demonstration file is deleted from the final branch. It is not imported into application code or tests.

## 4. Show the corrected run

Open the latest successful run for the PR. Show:

1. Both linter steps passed.
2. The quality gate summary reports success for Prettier and ESLint.
3. API and UI jobs started only after the gate completed.
4. API output lists five completed checks; UI output lists eight completed scenarios.

Explain that an unavailable external demo service can fail a test job after the quality gate passes; this does not bypass or invalidate the linter gate.

## 5. Run the checks locally if requested

```sh
npm ci
npm run lint:prettier
npm run lint:eslint
```

To reproduce a failure, create a temporary JavaScript file with an unused variable and malformed formatting, run both checks and then delete the file. Use a separate demonstration branch if publishing another failing run.

A live presentation or sending messages to the mentor is performed by the student. The repository contains the configuration and reproducible demonstration material.
