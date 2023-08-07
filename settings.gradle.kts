
rootProject.name = "qr-menu"

pluginManagement {
    plugins {
        val kotlinVersion: String by settings
        kotlin("jvm") version kotlinVersion apply false
    }
}

include("quickstart")
