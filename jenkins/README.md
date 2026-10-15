# Jenkins local setup

This setup runs Jenkins LTS in Docker with Node.js 24 and Chromium installed. The startup Groovy script creates two Pipeline jobs automatically. Each job checks out `feature/module-10-quality-gates` from the public GitHub repository, installs locked dependencies with `npm ci`, and runs only its matching suite. The Jenkinsfiles schedule both jobs using `H */2 * * *`, which distributes their runs across each two-hour window.

## Start Jenkins

From the repository root:

```sh
docker compose -f jenkins/compose.yaml up --build -d
```

Open `http://localhost:8080` and complete Jenkins' first-run wizard, installing the suggested plugins if prompted. The Git and Pipeline plugins are preinstalled. Wait for both `module10-ui-tests` and `module10-api-tests` to appear on the dashboard. Trigger each once with **Build Now** to verify checkout and establish the Pipeline-defined two-hour schedule. Scheduled builds then run automatically while the Jenkins container is up.

To inspect the initial administrator password inside the container:

```sh
docker exec module10-jenkins cat /var/jenkins_home/secrets/initialAdminPassword
```

The Jenkins dashboard and initial password are intentionally local. Do not publish them. For UI tests, the container uses headless Chromium via the existing WebdriverIO configuration. The public demo booking site may return a Cloudflare human-verification page to cloud-hosted clients; in that case the UI job correctly reports failure. API checks do not depend on that browser challenge.

## Verify / stop

```sh
docker compose -f jenkins/compose.yaml ps
docker compose -f jenkins/compose.yaml logs --tail=100 jenkins
docker compose -f jenkins/compose.yaml down
```

Jenkins state persists in the `jenkins_home` Docker volume when the container is stopped.

## Mentor walkthrough

1. Open Jenkins at `http://localhost:8080` and show `module10-ui-tests` and `module10-api-tests`.
2. Open each job's configuration to show its GitHub SCM URL and `Jenkinsfile.ui` / `Jenkinsfile.api` script path.
3. Show the Jenkinsfile `triggers` block (`H */2 * * *`) and the job's scheduled build time.
4. Run both jobs with **Build Now**, then open each build's Console Output. UI executes `npm run test:ui`; API executes `npm run test:api`.
