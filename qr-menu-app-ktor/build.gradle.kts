import org.jetbrains.kotlin.util.suffixIfNot

val ktorVersion: String by project
val serializationVersion: String by project
val logbackVersion: String by project

fun ktor(module: String, prefix: String = "server-", version: String? = this@Build_gradle.ktorVersion): Any =
    "io.ktor:ktor-${prefix.suffixIfNot("-")}$module:$version"

plugins {
    id("application")
    id("io.ktor.plugin")
    kotlin("plugin.serialization")
    kotlin("jvm")
}

repositories {
    maven { url = uri("https://maven.pkg.jetbrains.space/public/p/ktor/eap") }
}

application {

    mainClass.set("io.ktor.server.netty.EngineMain")
}

dependencies {
    val testContainersVersion: String by project
    val kmpUUIDVersion: String by project

    implementation(kotlin("stdlib"))
    implementation(ktor("core")) // "io.ktor:ktor-server-core:$ktorVersion"
    implementation(ktor("netty")) // "io.ktor:ktor-ktor-server-netty:$ktorVersion"

    // jackson
    implementation(ktor("jackson", "serialization")) // io.ktor:ktor-serialization-jackson
    implementation(ktor("content-negotiation")) // io.ktor:ktor-server-content-negotiation
    implementation(ktor("kotlinx-json", "serialization")) // io.ktor:ktor-serialization-kotlinx-json

    implementation("ch.qos.logback:logback-classic:$logbackVersion")

    implementation(kotlin("test-junit"))
    implementation(ktor("test-host")) // "io.ktor:ktor-server-test-host:$ktorVersion"
    implementation(ktor("content-negotiation", prefix = "client-"))
    implementation("com.benasher44:uuid:$kmpUUIDVersion")


    // models
    implementation(project(":qr-menu-api-v1-jackson"))
    implementation(project(":qr-menu-mappers"))
    implementation(project(":qr-menu-common"))
    implementation(project(":qr-menu-stubs"))
    implementation(project(":qr-menu-biz"))
    implementation(project(":qr-menu-auth"))
    implementation(project(":qr-menu-repo-in-memory"))
    implementation(project(":qr-menu-repo-postgresql"))
    implementation("io.ktor:ktor-server-auth-jvm:2.2.4")
    implementation("io.ktor:ktor-server-core-jvm:2.2.4")
    implementation("io.ktor:ktor-client-core-jvm:2.2.4")
    implementation("io.ktor:ktor-client-apache-jvm:2.2.4")
    implementation("io.ktor:ktor-server-auth-jwt-jvm:2.2.4")
    testImplementation(project(":qr-menu-repository-tests"))
    implementation(project(":qr-menu-repository-stubs"))

    testImplementation("org.testcontainers:postgresql:$testContainersVersion")

    implementation(ktor("config-yaml"))
    implementation("io.ktor:ktor-server-cors-jvm:2.2.4")
    implementation("io.ktor:ktor-server-call-logging-jvm:2.2.4")
    implementation("io.ktor:ktor-server-partial-content-jvm:2.2.4")
    implementation("io.ktor:ktor-server-conditional-headers-jvm:2.2.4")

    implementation(ktor("auth")) // "io.ktor:ktor-auth:$ktorVersion"
    implementation(ktor("auth-jwt")) // "io.ktor:ktor-auth-jwt:$ktorVersion"

}


ktor {
    docker {
        localImageName.set(project.name + "-ktor")
        imageTag.set(project.version.toString())
        jreVersion.set(io.ktor.plugin.features.JreVersion.JRE_17)
    }
}

jib {
    container.mainClass = "io.ktor.server.cio.EngineMain"
}

tasks.withType<Copy> {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}