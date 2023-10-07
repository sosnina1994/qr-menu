plugins {
    kotlin("jvm")
}

kotlin {

    sourceSets {
        val coroutinesVersion: String by project
        val atomicfuVersion: String by project

        dependencies {
            implementation(kotlin("stdlib-jdk8"))
            implementation(kotlin("stdlib-common"))
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")

            implementation(kotlin("test-common"))
            implementation(kotlin("test-junit"))
            implementation(kotlin("test-annotations-common"))
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutinesVersion")
            implementation("org.jetbrains.kotlinx:atomicfu:$atomicfuVersion")
        }
    }
}
