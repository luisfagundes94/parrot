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
    ":features:translation",
    ":features:saved",
    ":features:settings",
    ":data",
    ":domain",
    ":commons:ui",
    ":commons:util",
    ":commons:testing",
    ":commons:theme",
)
