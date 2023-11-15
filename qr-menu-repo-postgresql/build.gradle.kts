plugins {
    kotlin("jvm")
}

group = rootProject.group
version = rootProject.version

dependencies {
    val exposedVersion: String by project
    val postgresDriverVersion: String by project
    val kmpUUIDVersion: String by project
    val testContainersVersion: String by project

    implementation(kotlin("stdlib"))


    implementation("org.postgresql:postgresql:$postgresDriverVersion")

    implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-dao:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")
    implementation("com.benasher44:uuid:$kmpUUIDVersion")

    testImplementation("org.testcontainers:postgresql:$testContainersVersion")

    implementation(project(":qr-menu-common"))
    implementation(project(":qr-menu-repository-tests"))
}