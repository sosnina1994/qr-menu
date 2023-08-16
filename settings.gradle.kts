
rootProject.name = "qr-menu"

pluginManagement {
    val kotlinVersion: String by settings
    val openapiVersion: String by settings

    plugins {
        kotlin("jvm") version kotlinVersion apply false
        kotlin("plugin.serialization") version kotlinVersion apply false

        id("org.openapi.generator") version openapiVersion apply false
    }
}

include("quickstart")
include("openapi-v1-jackson")
