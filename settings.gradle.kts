pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
        includeBuild("plugins")
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = ("ParrotLingo")

include(
    ":app",
    ":framework",
    ":theme",
    ":features:translation",
    ":features:saved",
    ":features:settings",
)
include(":data")
include(":domain")
include(":commons-ui")
include(":commons-util")
