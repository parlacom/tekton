import org.springframework.boot.gradle.tasks.bundling.BootJar

// Module group and version configuration .................................
base.archivesBaseName = "${rootProject.name}-${project.name.substring(4)}"

// Dependencies Project Modules............................................
dependencies {
    implementation(project(":mod-domain"))
}

// Tasks ..................................................................
tasks.getByName<BootJar>("bootJar") {
    enabled = false
}

tasks.getByName<Jar>("jar") {
    enabled = true
}
