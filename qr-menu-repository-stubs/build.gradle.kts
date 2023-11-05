plugins {
    kotlin("jvm")
}

group = rootProject.group
version = rootProject.version

dependencies {

    dependencies {
        implementation(project(":qr-menu-common"))
        implementation(project(":qr-menu-stubs"))
        implementation(project(":qr-menu-repository-tests"))

        implementation(kotlin("test-common"))
        implementation(kotlin("test-annotations-common"))

    }
}