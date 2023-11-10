plugins {
    kotlin("jvm")
}


group = rootProject.group
version = rootProject.version

kotlin {

    sourceSets {
        val coroutinesVersion: String by project
        val datetimeVersion: String by project

        val logbackVersion: String by project
        val logbackEncoderVersion: String by project
        val janinoVersion: String by project

        dependencies {
            implementation(kotlin("stdlib-jdk8"))
            implementation(kotlin("stdlib-common"))
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
            api("org.jetbrains.kotlinx:kotlinx-datetime:$datetimeVersion")


            // logback
            implementation("net.logstash.logback:logstash-logback-encoder:$logbackEncoderVersion")
            implementation("org.codehaus.janino:janino:$janinoVersion")
            api("ch.qos.logback:logback-classic:$logbackVersion")

            implementation(kotlin("test-common"))
            implementation(kotlin("test-junit"))
            implementation(kotlin("test-annotations-common"))
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutinesVersion")
        }
    }
}
