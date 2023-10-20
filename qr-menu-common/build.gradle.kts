plugins {
    kotlin("jvm")
}

group = rootProject.group
version = rootProject.version

dependencies {
    val datetimeVersion: String by project
    implementation(kotlin("stdlib-common"))
    implementation(kotlin("test-common"))
    implementation(kotlin("test-annotations-common"))
    api("org.jetbrains.kotlinx:kotlinx-datetime:$datetimeVersion")
    api(project(":qr-menu-logging"))
}
