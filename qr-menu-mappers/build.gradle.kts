plugins {
    kotlin("jvm")
}

group = rootProject.group
version = rootProject.version

dependencies {
    implementation(kotlin("stdlib"))
    implementation(project(":qr-menu-api-v1-jackson"))
    implementation(project(":qr-menu-common"))

    testImplementation(kotlin("test-junit"))
}