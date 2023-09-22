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
    //mainClass.set("ru.drvshare.menu.ApplicationKt")
}

dependencies {
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

    // models
    implementation(project(":qr-menu-api-v1-jackson"))
    implementation(project(":qr-menu-mappers"))
    implementation(project(":qr-menu-common"))
    implementation(project(":qr-menu-stubs"))


    implementation("io.ktor:ktor-server-cors-jvm:2.2.4")
    implementation("io.ktor:ktor-server-call-logging-jvm:2.2.4")
    implementation("io.ktor:ktor-server-partial-content-jvm:2.2.4")
    implementation("io.ktor:ktor-server-conditional-headers-jvm:2.2.4")

}

tasks.withType<Copy> {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}