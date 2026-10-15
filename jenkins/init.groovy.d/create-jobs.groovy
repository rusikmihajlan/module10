import jenkins.model.Jenkins
import hudson.plugins.git.GitSCM
import hudson.plugins.git.UserRemoteConfig
import hudson.plugins.git.BranchSpec
import org.jenkinsci.plugins.workflow.cps.CpsScmFlowDefinition
import org.jenkinsci.plugins.workflow.job.WorkflowJob

Jenkins jenkins = Jenkins.get()
String repository = 'https://github.com/rusikmihajlan/module10.git'
String branch = '*/feature/module-10-quality-gates'

[
    [name: 'module10-ui-tests', script: 'Jenkinsfile.ui', description: 'Runs Module 10 browser UI tests every two hours.'],
    [name: 'module10-api-tests', script: 'Jenkinsfile.api', description: 'Runs Module 10 API tests every two hours.']
].each { config ->
    WorkflowJob job = jenkins.getItem(config.name) ?: jenkins.createProject(WorkflowJob, config.name)
    job.setDescription(config.description)
    GitSCM scm = new GitSCM([new UserRemoteConfig(repository, null, null, null)], [new BranchSpec(branch)], false, [], null, null, [])
    job.setDefinition(new CpsScmFlowDefinition(scm, config.script))
    job.save()
}
jenkins.save()
