/**
 * 1 - Export Application Environment Configuration
 * ------------------------------------------------
 *
 * vi ~/.zshrc
 *
 * export BOURNVILLE_IMAGE_NAME=registry.parlacom.net:8081:5000/leadingquest/leadingquest-bournville:1.0
 * export REGISTRY_URL=https://registry.parlacom.net:8081
 * export REGISTRY_USERNAME=parla
 * export REGISTRY_PASSWORD=Leodouve10@
 * export REGISTRY_CONTACT=luiz@leadingguest.com
 * export REPOSITORY_NAME=parla
 * export REPOSITORY_URL=https://repository.parlacom.net:8081/repository/parla/
 * export REPOSITORY_USERNAME=parla
 * export REPOSITORY_PASSWORD=apisuccess2018
 *
 * source ~/.zshrc
 *
 * 2 - Setup application environment to access the private repository
 *     Add the "*.parlacom.net" certificate to CACERT
 *
 * --------------------------------------------------
 *
 * keytool -import -trustcacerts -keystore /Users/luiz.henrique/.sdkman/candidates/java/11.0.10.j9-adpt/lib/security/cacerts -storepass changeit -noprompt -alias parlacom_net -file /Users/luiz.henrique/Workbench/Workspace/Parlacom/Java/Parla-Cloud/Butterfinger/Certs/star_parlacom_net.cer
 */

import io.gitlab.arturbosch.detekt.Detekt
import nu.studer.gradle.jooq.JooqEdition
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jooq.meta.jaxb.Property
import org.springframework.boot.gradle.tasks.bundling.BootBuildImage

plugins {
    base
    java

    id("org.springframework.boot") version "3.1.4"
    id("io.spring.dependency-management") version "1.1.3"
    id("io.gitlab.arturbosch.detekt") version "1.17.1"
    id("nu.studer.jooq") version "8.1"
    id("org.sonarqube") version "3.2.0"
    id("jacoco")

    kotlin("jvm") version "1.8.22"
    kotlin("plugin.spring") version "1.8.22"
    kotlin("kapt") version "1.8.22"
}

allprojects {

    // Apply plugins for the all sub-projects .......................
    apply(plugin = "java")
    apply(plugin = "base")
    apply(plugin = "kotlin")
    apply(plugin = "kotlin-spring")
    apply(plugin = "kotlin-kapt")
    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "io.gitlab.arturbosch.detekt")
    apply(plugin = "nu.studer.jooq")
    apply(plugin = "jacoco")

    // Project configuration ........................................
    val PROJECT_GROUP               = "net.parlacom"
    val PROJECT_VERSION             = "1.0.RELEASE"
    val PARLA_COMMON_LIB_VERSION    = "1.0.RELEASE"
    val PARLA_COMMON_DOMAIN_VERSION = "1.0.RELEASE"
    val PARLA_ERROR_LIB_VERSION     = "1.0.RELEASE"
    val SPRING_CLOUD_VERSION        = "2022.0.4"
    val MAPSTRUCT_VERSION           = "1.5.5.Final"
    val SENTRY_VERSION              = "6.19.0"
    val LOKI_VERSION                = "1.4.1"
    val SIMPLE_XML_VERSION          = "2.7.1"
    val DETEKT_FORMATTING_VERSION   = "1.17.1"
    val MARIADB_R2DBC_VERSION       = "1.1.3"
    val CACHE2K_VERSION             = "2.6.1.Final"
    val JOOQ_VERSION                = "3.18.6"
    val JSON_JAVA_VERSION           = "20220320"
    val JOCOCO_VERSION              = "0.8.8"
    val SPRING_DOC_OPENAPI_VERSION  = "2.1.0"
    val JANINO_VERSION              = "3.1.9"

    val IMAGE_NAME          = System.getProperty("IMAGE_NAME")
    val REGISTRY_URL        = System.getProperty("REGISTRY_URL")
    val REGISTRY_USERNAME   = System.getProperty("REGISTRY_USERNAME")
    val REGISTRY_PASSWORD   = System.getProperty("REGISTRY_PASSWORD")
    val REGISTRY_CONTACT    = System.getProperty("REGISTRY_CONTACT")
    val REPOSITORY_NAME     = System.getProperty("REPOSITORY_NAME")
    val REPOSITORY_URL      = System.getProperty("REPOSITORY_URL")
    val REPOSITORY_USERNAME = System.getProperty("REPOSITORY_USERNAME")
    val REPOSITORY_PASSWORD = System.getProperty("REPOSITORY_PASSWORD")
    val ENV_WORKSPACE       = System.getProperty("ENV_WORKSPACE")

    // Project group and version configuration .......................
    group   = PROJECT_GROUP
    version = PROJECT_VERSION

    base.archivesBaseName    = rootProject.name
    java.sourceCompatibility = JavaVersion.VERSION_17
    java.targetCompatibility = JavaVersion.VERSION_17

    kapt.includeCompileClasspath = false

    // Dependencies Libraries .........................................
    dependencies {

        // Parlacom .................
        implementation("net.parlacom:milka:${PARLA_COMMON_LIB_VERSION}") // Common Library
        implementation("net.parlacom:mars:${PARLA_COMMON_DOMAIN_VERSION}") // Domain Model
        implementation("net.parlacom:encore:${PARLA_ERROR_LIB_VERSION}") // Exception Handler Library

        // Spring Framework ..........
        implementation("org.springframework.boot:spring-boot-starter-actuator")
        implementation("org.springframework.boot:spring-boot-starter-webflux")
        implementation("org.springframework.boot:spring-boot-starter-amqp")
        implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
        implementation("org.springframework.boot:spring-boot-starter-data-elasticsearch")

        // Spring Cloud  ..............
        implementation("org.springframework.cloud:spring-cloud-starter-kubernetes-fabric8-all")
        implementation("org.springframework.cloud:spring-cloud-kubernetes-fabric8-istio")
        implementation("org.springframework.cloud:spring-cloud-starter-config")
        implementation("org.springframework.cloud:spring-cloud-starter-bootstrap")

        // Kotlin .....................
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactive")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")

        // Jooq ......................
        implementation("org.jooq:jooq-kotlin-coroutines:${JOOQ_VERSION}")
        jooqGenerator("org.jooq:jooq-meta-extensions:${JOOQ_VERSION}")
        jooqGenerator("org.mariadb:r2dbc-mariadb:${MARIADB_R2DBC_VERSION}")

        // MariaDB R2DBC Driver.....
        implementation("org.mariadb:r2dbc-mariadb:${MARIADB_R2DBC_VERSION}")

        // Micrometer Prometheus ......
        runtimeOnly("io.micrometer:micrometer-registry-prometheus")

        // Json in JAVA ...............
        implementation("org.json:json:${JSON_JAVA_VERSION}")

        // Jackson ....................
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
        implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")

        // Simple XML .................
        implementation("org.simpleframework:simple-xml:${SIMPLE_XML_VERSION}")

        // SpringDoc-OpenApi ..........
        implementation("org.springdoc:springdoc-openapi-starter-webflux-ui:${SPRING_DOC_OPENAPI_VERSION}")

        // Loki Grafana Logging........
        implementation("com.github.loki4j:loki-logback-appender:${LOKI_VERSION}")

        // Sentry .....................
        implementation("io.sentry:sentry-logback:${SENTRY_VERSION}")

        // MapStruct ..................
        implementation("org.mapstruct:mapstruct:${MAPSTRUCT_VERSION}")
        kapt("org.mapstruct:mapstruct-processor:${MAPSTRUCT_VERSION}")

        // Cache2k ....................
        implementation("org.cache2k:cache2k-core:${CACHE2K_VERSION}")
        implementation("org.cache2k:cache2k-api:${CACHE2K_VERSION}")
        implementation("org.cache2k:cache2k-spring:${CACHE2K_VERSION}")

        // Detekt Static Code Analysis for Kotlin ..........
        implementation("io.gitlab.arturbosch.detekt:detekt-formatting:${DETEKT_FORMATTING_VERSION}")

        // Janino Logging Condition
        implementation("org.codehaus.janino:janino:${JANINO_VERSION}")

        // Spring Test ................
        testImplementation("org.springframework.boot:spring-boot-starter-test")
    }

    // Spring Cloud dependency management .....................................
    dependencyManagement {
        imports {
            mavenBom("org.springframework.cloud:spring-cloud-dependencies:${SPRING_CLOUD_VERSION}")
        }
    }

    // Dependencies repositories .....................................
    repositories {
        mavenCentral()
        maven {
            name = REPOSITORY_NAME
            url = uri(REPOSITORY_URL)
            credentials{
                username = REPOSITORY_USERNAME
                password = REPOSITORY_PASSWORD
            }
        }
    }

    // JOOQ Code Generator Plugin .....................................
    jooq {
        version.set(JOOQ_VERSION)
        edition.set(JooqEdition.OSS)

        configurations {
            create("main") {  // name of the jOOQ configuration
                generateSchemaSourceOnCompilation.set(true)

                jooqConfiguration.apply {
                    logging = org.jooq.meta.jaxb.Logging.WARN
                    jdbc = null; // only required for gen from active databases.

                    generator.apply {
                        name = "org.jooq.codegen.KotlinGenerator"
                        database.apply {
                            name = "org.jooq.meta.extensions.ddl.DDLDatabase" // gen from ddl schema.

                            // inputSchema = "public"
                            properties.addAll(
                                listOf(
                                    // Specify the location of your SQL script.
                                    // You may use ant-style file matching, e.g. /path/**/to/*.sql
                                    //
                                    // Where:
                                    // - ** matches any directory subtree
                                    // - * matches any number of characters in a directory / file name
                                    // - ? matches a single character in a directory / file name
                                    Property().apply {
                                        key = "scripts"
                                        value = "src/main/resources/database/schema.sql"
                                    },

                                    // The sort order of the scripts within a directory, where:
                                    //
                                    // - semantic: sorts versions, e.g. v-3.10.0 is after v-3.9.0 (default)
                                    // - alphanumeric: sorts strings, e.g. v-3.10.0 is before v-3.9.0
                                    // - flyway: sorts files the same way as flyway does
                                    // - none: doesn't sort directory contents after fetching them from the directory
                                    Property().apply {
                                        key = "sort"
                                        value = "semantic"
                                    },

                                    // The default schema for unqualified objects:
                                    //
                                    // - public: all unqualified objects are located in the PUBLIC (upper case) schema
                                    // - none: all unqualified objects are located in the default schema (default)
                                    //
                                    // This configuration can be overridden with the schema mapping feature
                                    Property().apply {
                                        key = "unqualifiedSchema"
                                        value = "none"
                                    },

                                    // The default name case for unquoted objects:
                                    //
                                    // - as_is: unquoted object names are kept unquoted
                                    // - upper: unquoted object names are turned into upper case (most databases)
                                    // - lower: unquoted object names are turned into lower case (e.g. PostgreSQL)
                                    Property().apply {
                                        key = "defaultNameCase"
                                        value = "lower"
                                    }
                                )
                            )
                        }
                        generate.apply {
                            isPojosAsKotlinDataClasses = true
                        }
                        target.apply {
                            packageName = "net.parlacom.bi.iot.domain.dao"
                            directory = "build/generated-src/jooq/main"
                        }
                        strategy.name = "org.jooq.codegen.DefaultGeneratorStrategy"
                    }
                }
            }
        }
    }

    // Tasks ..................................................................
    tasks {

        withType<Test> {
            useJUnitPlatform()
            maxParallelForks = 4
        }

        // Kotlin ....................................................
        withType<KotlinCompile> {
            kotlinOptions {
                freeCompilerArgs = listOf("-Xjsr305=strict")
                jvmTarget = java.sourceCompatibility.toString()
            }
        }

        // Spring Boot ................................................
        // Image: Build the image and publish it to the private parlacom registry
        // https://github.com/spring-projects/spring-boot/blob/472c563451e141b2df31d31c7cfbf8bed22b3bc3/spring-boot-project/spring-boot-tools/spring-boot-maven-plugin/src/docs/asciidoc/packaging-oci-image.adoc
        withType<BootBuildImage> {
            imageName.set(IMAGE_NAME)
            publish.set(true)

            builder.set("paketobuildpacks/builder:tiny")

            environment.set(environment.get() + mapOf("BP_NATIVE_IMAGE" to "false",
                                                      "BP_JVM_VERSION" to java.sourceCompatibility.toString(),
                                                      "BPE_JAVA_TOOL_OPTIONS" to "-XX:MaxDirectMemorySize=100M"))
            docker {
                publishRegistry {
                    username.set(REGISTRY_USERNAME)
                    password.set(REGISTRY_PASSWORD)
                    url.set(REGISTRY_URL)
                    email.set(REGISTRY_CONTACT)                }
            }
        }

        // Detekt ....................................................
        // Detekt Static Code Analysis for Kotlin
        withType<Detekt> {
            enabled = false
            description = "Runs the application detekt build."

            setSource(files("src/main/kotlin", "src/test/kotlin"))
            config.setFrom(files("$rootDir/resources/detekt/config.yml"))
            baseline.set(file("$rootDir/resources/detekt/baseline.xml"))

            reports {
                xml {
                    destination = file("build/reports/detekt.xml")
                }
                html.destination = file("build/reports/detekt.html")
            }
            include("**/*.kt")
            include("**/*.kts")
            exclude("resources/")
            exclude("build/")
        }

        // Jacoco ....................................................
        // Jacoco Code Coverage Verification
        withType<JacocoCoverageVerification> {
            jacoco {
                toolVersion = JOCOCO_VERSION
            }
            violationRules {
                rule {
                    limit {
                        minimum = "0.0".toBigDecimal()
                    }
                }
            }
        }
    }

    // Task to build the project and runs the tests with coverage
    tasks.register<JacocoReport>("buildTestCoverage") {
        group = "build"
        description = "Build the project and runs the tests with coverage"

        jacoco {
            toolVersion = JOCOCO_VERSION
        }
        dependsOn(":mod-app:build")
        subprojects {
            val subProject = this
            subProject.plugins.withType<JacocoPlugin>().configureEach {
                subProject.tasks.matching { it.extensions.findByType<JacocoTaskExtension>() != null }.configureEach {
                    val testTask = this
                    sourceSets(subProject.sourceSets.main.get())
                    executionData(testTask)
                }
                subProject.tasks.matching { it.extensions.findByType<JacocoTaskExtension>() != null }.forEach {
                    val jacocoTestCoverage = tasks.findByName("jacocoTestCoverageVerification")
                    rootProject.tasks["buildTestCoverage"].dependsOn(it, jacocoTestCoverage)
                }
            }
        }
        reports {
            html.required.set(true)
            xml.required.set(true)
            csv.required.set(false)
            html.setDestination(file("$ENV_WORKSPACE/mod-app/build/jacoco"))
        }
    }
}
