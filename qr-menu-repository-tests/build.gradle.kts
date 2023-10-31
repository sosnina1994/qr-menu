plugins {
    kotlin("jvm")
}

group = rootProject.group
version = rootProject.version
val coroutinesVersion: String by project

dependencies {
    implementation(project(":qr-menu-common"))

    api("org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutinesVersion")
    implementation("junit:junit:4.13.1")
    testImplementation(kotlin("test-junit"))
}