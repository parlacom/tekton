#!/usr/bin/env groovy

/**
 * A Jenkinsfile can be written using two types of syntax - Declarative and Scripted
 *
 * Import the Parlacom Chain certificate to the JVM
 * keytool -import -trustcacerts -keystore /Users/luiz.henrique/.sdkman/candidates/java/13.0.2-open/lib/security/cacerts -storepass changeit -noprompt -alias mycert -file /Users/luiz.henrique/Downloads/parlacom-net-chain.pem
 *
 * Jenkins Environment
 *
 * BRANCH_NAME:master
 * BUILD_DISPLAY_NAME:#36
 * BUILD_ID:36
 * BUILD_NUMBER:36
 * BUILD_TAG:jenkins-Malagos Microservice (sigfox RESTful)-master-36
 * BUILD_URL:https://jenkins.parlacom.net/job/Malagos%20Microservice%20(sigfox%20RESTful)/job/master/36/
 * CLASSPATH:
 * HUDSON_HOME:/var/jenkins_home
 * HUDSON_SERVER_COOKIE:44719063ed24cbeb
 * HUDSON_URL:https://jenkins.parlacom.net/
 * JENKINS_HOME:/var/jenkins_home
 * JENKINS_SERVER_COOKIE:44719063ed24cbeb
 * JENKINS_URL:https://jenkins.parlacom.net/
 * JOB_BASE_NAME:master
 * JOB_DISPLAY_URL:https://jenkins.parlacom.net/job/Malagos%20Microservice%20(sigfox%20RESTful)/job/master/display/redirect
 * JOB_NAME:Malagos Microservice (sigfox RESTful)/master
 * JOB_URL:https://jenkins.parlacom.net/job/Malagos%20Microservice%20(sigfox%20RESTful)/job/master/
 * RUN_CHANGES_DISPLAY_URL:https://jenkins.parlacom.net/job/Malagos%20Microservice%20(sigfox%20RESTful)/job/master/36/display/redirect?page=changes
 * RUN_DISPLAY_URL:https://jenkins.parlacom.net/job/Malagos%20Microservice%20(sigfox%20RESTful)/job/master/36/display/redirect
 *
 * WORKSPACE:/var/jenkins_home/workspace/oservice_sigfox_RESTful_master *
 */

def PROJ_GIT = "ParlaCloud-Musketeers.git"

def APP_NAME    = "api-bi-prod"
def APP_MODULE  = "mod-app"

def JDK_TOOL_NAME = "jdk17"
def JAVA_HOME = "/jdk-17.0.5"

def GIT_REPO_URL       = "https://github.com/parlacom/"+PROJ_GIT
def GIT_REPO_BRANCH    = "master"
def GITHUB_CREDENTIALS = "github-parlacom-key"

def KUBERNETES_CREDENTIALS     = "kubernetes-config-credentials2"
def KUBERNETES_SERVER_URL      = "https://82.165.210.49:6443"
def KUBERNETES_DEPLOY          = "k8s-deploy.yml"

def JACOCO_REPORT = env.WORKSPACE + "/build/reports/jacoco/index.xml"

if (env.BRANCH_NAME != GIT_REPO_BRANCH) {
    echo "This is not a master branch"
    return
}

withEnv(["IMAGE_NAME=registry.parlacom.net:8081/leadingquest/leadingquest-musketeers:prod-v1.0",
         "REGISTRY_URL=registry.parlacom.net:8081",
         "REGISTRY_USERNAME=parla",
         "REGISTRY_PASSWORD=Leodouve10@",
         "REGISTRY_CONTACT=luiz@leadingguest.com",
         "REPOSITORY_NAME=parla",
         "REPOSITORY_URL=https://repository.parlacom.net:8081/repository/parla/",
         "REPOSITORY_USERNAME=parla",
         "REPOSITORY_PASSWORD=Leodouve10@",
         "ENV_WORKSPACE="+env.WORKSPACE]) {

    node {
       /**
        * Defines a specific JDK version to compile the application
        */
        JDK_TOOL = tool name: JDK_TOOL_NAME
        env.JAVA_HOME = "${JDK_TOOL}/${JAVA_HOME}"
        echo "JDK Tool Name: ${JDK_TOOL}"
        echo "JDK Installation Path: ${env.JAVA_HOME}"

        try {
            stage("Stage 1 - Checkout the Source") {
                git branch: GIT_REPO_BRANCH,
                    url: GIT_REPO_URL,
                    credentialsId: GITHUB_CREDENTIALS,
                    poll: true
            }

            stage("Stage 2 - Build the App and Execute the Unit & Integration Tests") {
                sh """
                    ./gradlew clean --info
                    # ./gradlew :buildTestCoverage --refresh-dependencies --info
                    ./gradlew :${APP_MODULE}:bootBuildImage --refresh-dependencies --info -Dskip.tests
                   """
            }

            stage("Stage 3 - Kubernetes Delete Deploy and Service") {
                withKubeConfig([credentialsId: KUBERNETES_CREDENTIALS,
                                serverUrl: KUBERNETES_SERVER_URL]) {
                    try{
                        sh "/usr/bin/kubectl delete svc ${APP_NAME}"
                    } catch (error) {
                        echo "Warning: K8s Service doesnt exists"
                    }
                    try{
                       sh "/usr/bin/kubectl delete statefulset ${APP_NAME}"
                    } catch (error) {
                        echo "Warning: K8s Stateful doesnt exists"
                    }
                    try{
                        sh "/usr/bin/kubectl delete horizontalpodautoscalers ${APP_NAME}"
                    } catch (error) {
                        echo "Warning: K8s Horizontal Autoscaler doesnt exists"
                    }
                    try{
                        sh "/usr/bin/kubectl delete servicemonitor ${APP_NAME}-service-monitor"
                    } catch (error) {
                        echo "Warning: K8s Service Monitor doesnt exists"
                    }
                    try{
                       sh "/usr/bin/kubectl delete secret ${APP_NAME}"
                    } catch (error) {
                        echo "Warning: K8s Secret doesnt exists"
                    }
                }
            }

            stage("Stage 4 - Kubernetes Deploy the App to the Cluster") {
                withKubeConfig([credentialsId: KUBERNETES_CREDENTIALS,
                                serverUrl: KUBERNETES_SERVER_URL]) {

                    sh "/usr/bin/kubectl create -f ${KUBERNETES_DEPLOY}"
                }
            }

//             stage("Stage 5 - Publishing Jacoco Report") {
//                 // https://www.jenkins.io/projects/gsoc/2018/code-coverage-api-plugin/
//                 // https://github.com/jenkinsci/code-coverage-api-plugin
//                 echo "Current workspace is ${env.WORKSPACE}"
//                 echo "Jacoco Report: ${JACOCO_REPORT}"
//                 publishCoverage adapters: [jacocoAdapter(JACOCO_REPORT)]
//             }

        } catch (ex) {
            // https://plugins.jenkins.io/email-ext/
            //
            // Set up Jenkins Extended E-mail Notification
            // ...........................................
            // Left Menu: Manage Jenkins -> Configure System -> Extended E-mail Notification
            // SMTP server: smtp.1and1.com
            // SMTP Port: 25
            // SMTP Username: luiz@leadingquest.com
            // SMTP Password: Leodouve10@
            // Use SSL: false
            // Use TLS: false

            emailext (
                from: "luiz@leadingquest.com",
                to: "lhmoraes@gmail.com",
                subject: "FAILED: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]'",
                body: """<p>FAILED: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]':</p>
                <p>Check console output at <a href='${env.BUILD_URL}'>${env.JOB_NAME} [${env.BUILD_NUMBER}]</a></p>"""
            )
            throw ex
        }
    }
}