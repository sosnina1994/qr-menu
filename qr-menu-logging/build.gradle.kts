plugins {
    kotlin("jvm")
}


group = rootProject.group
version = rootProject.version

kotlin {

    sourceSets {
        val coroutinesVersion: String by project
        val datetimeVersion: String by project

        dependencies {
            implementation(kotlin("stdlib-jdk8"))
            implementation(kotlin("stdlib-common"))
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
            api("org.jetbrains.kotlinx:kotlinx-datetime:$datetimeVersion")

            implementation(kotlin("test-common"))
            implementation(kotlin("test-junit"))
            implementation(kotlin("test-annotations-common"))
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutinesVersion")
        }
    }
}
