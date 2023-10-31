plugins {
    kotlin("jvm")
}

group = rootProject.group
version = rootProject.version
val coroutinesVersion: String by project
val kmpUUIDVersion: String by project
val cache4kVersion: String by project

dependencies {
    implementation(project(":qr-menu-common"))
    implementation(project(":qr-menu-repository-tests"))
    implementation("com.benasher44:uuid:$kmpUUIDVersion")
    implementation("io.github.reactivecircus.cache4k:cache4k:$cache4kVersion")

    api("org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutinesVersion")
    implementation(kotlin("stdlib"))
    implementation("junit:junit:4.13.1")
    testImplementation(kotlin("test-junit"))
}