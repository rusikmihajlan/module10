# Quality gate demonstration for the mentor

## 1. Show the configuration

Open `.prettierrc.json`, `.prettierignore`, `eslint.config.js` and the scripts in `package.json`. Explain that Prettier checks formatting while ESLint checks JavaScript correctness. `eslint-config-prettier` prevents overlapping formatting rules.

## 2. Show the job dependencies

Open `.github/workflows/ci.yml`. Both `api-tests` and `ui-tests` declare `needs: quality-gates`. The linters run in check mode; CI does not silently fix the code. ESLint still runs when Prettier fails, provided `npm ci` succeeded.

## 3. Show the deliberate failing run

The demonstrated CI run is [38808722946](https://github.com/rusikmihajlan/module10/actions/runs/38808722946).

The demonstration uses a temporary root-level JavaScript file containing:

```js
const unusedQualityGateDemo = true;
```

For the failure demonstration the file also deliberately omits formatting spaces and the semicolon. This triggers both a Prettier failure and ESLint's `no-unused-vars` rule. Show the two failed steps, the failed gate, and both test jobs marked **Skipped**. This is an intentional validation of the gate, not a failure of the final solution.

The demonstration file is deleted from the final branch. It is not imported into application code or tests.

## 4. Show the successful quality gate and test jobs

Open the PR's Checks tab. [Run 38808006878](https://github.com/rusikmihajlan/module10/actions/runs/38808006878) shows:

1. Both linter steps passed.
2. The quality gate summary reports success for Prettier and ESLint.
3. API and UI jobs were queued only after the gate completed.
4. API output lists five completed checks.

GitHub-hosted Chromium currently receives a Cloudflare human-verification page from the public UI demo, so the UI job fails before the eight scenarios can reach the application. Its failure screenshots show the verification page. The same eight scenarios pass locally in this cloud environment. Show this limitation separately from the successful quality gate and API job; the workflow reports it instead of bypassing the verification.

## 5. Run the checks locally if requested

```sh
npm ci
npm run lint:prettier
npm run lint:eslint
```

The failed quality-gate run above was demonstrated on the PR branch: commit the temporary malformed JavaScript file, inspect the failed Prettier and ESLint steps and confirm both dependent test jobs were skipped, then remove the file in a follow-up commit. Do not merge the failing commit.

A live presentation or sending messages to the mentor is performed by the student. The repository contains the configuration, CI run evidence and reproducible demonstration material.
