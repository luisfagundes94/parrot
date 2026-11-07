pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "parrot"

// App
include(":app")

// Core
include(":core:common")
include(":core:navigation")

// Feature
include(":feature:search:api")
include(":feature:search:impl")
include(":feature:saved:api")
include(":feature:saved:impl")
include(":feature:review:api")
include(":feature:review:impl")
