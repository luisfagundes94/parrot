import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.DetektPlugin

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.ben.manes.versions)
    alias(libs.plugins.version.catalog.update)
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.detekt)
    alias(libs.plugins.kotlin.kapt) apply false
    alias(libs.plugins.kotlin.parcelize) apply false
}

tasks.withType<Detekt>().configureEach {
    reports {
        html.required.set(true)
        md.required.set(true)
    }
}

tasks {
    val detektAll by registering(Detekt::class) {
        autoCorrect = true
        parallel = true
        setSource(files(projectDir))
        include("**/*.kt")
        include("**/*.kts")
        exclude("**/resources/**")
        exclude("**/build/**")
        config.setFrom(files("${rootDir}/config/detekt/detekt.yml"))
        buildUponDefaultConfig = true
    }
}

versionCatalogUpdate {
    keep {
        sortByKey.set(false)
        keepUnusedVersions.set(true)
        keepUnusedLibraries.set(true)
        keepUnusedPlugins.set(true)
    }
}

tasks.named<DependencyUpdatesTask>("dependencyUpdates").configure {
    rejectVersionIf {
        isNonStable(this.candidate.version)
    }
}

fun isNonStable(version: String): Boolean {
    val stableKeyword = listOf("RELEASE", "FINAL", "GA").any {
        version.lowercase(java.util.Locale.getDefault())
            .contains(it)
    }
    val regex = "^[0-9,.v-]+(-r)?$".toRegex()
    val isStable = stableKeyword || regex.matches(version)
    return isStable.not()
}

dependencies {
    detektPlugins(libs.detekt.formatting)
}
