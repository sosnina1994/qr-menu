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

dependencies {
    val rabbitVersion: String by project
    val jacksonVersion: String by project
    val logbackVersion: String by project
    val coroutinesVersion: String by project
    val testContainersVersion: String by project

    implementation(kotlin("stdlib"))
    implementation("com.rabbitmq:amqp-client:$rabbitVersion")
    implementation("com.fasterxml.jackson.core:jackson-databind:$jacksonVersion")
    implementation("ch.qos.logback:logback-classic:$logbackVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")


    implementation(project(":qr-menu-api-v1-jackson"))
    implementation(project(":qr-menu-mappers"))
    implementation(project(":qr-menu-common"))
    implementation(project(":qr-menu-stubs"))
    implementation(project(":qr-menu-biz"))

    testImplementation("org.testcontainers:rabbitmq:$testContainersVersion")
    testImplementation(kotlin("test"))
}
