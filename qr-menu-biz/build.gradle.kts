plugins {
    kotlin("jvm")
}

kotlin {

    sourceSets {
        val coroutinesVersion: String by project

        sourceSets {
            @Suppress("UNUSED_VARIABLE")
            val main by getting {
                dependencies {
                    implementation(kotlin("stdlib-common"))
                    implementation(kotlin("stdlib-jdk8"))
                    implementation(project(":qr-menu-common"))
                    implementation(project(":qr-menu-stubs"))
                    implementation(project(":qr-menu-lib-cor"))
                    implementation(project(":qr-menu-repository-stubs"))
                    implementation(project(":qr-menu-repository-tests"))
                    implementation(project(":qr-menu-auth"))
                }

                @Suppress("UNUSED_VARIABLE")
                val test by getting {
                    dependencies {
                        implementation(kotlin("test-junit"))
                        api("org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutinesVersion")
                    }
                }
            }
        }
    }
}
