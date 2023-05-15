plugins {
    alias(libs.plugins.hilt)
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
}

android {
    namespace = "com.luisfagundes.translation"
    compileSdk = libs.versions.compile.sdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.min.sdk.get().toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = libs.versions.java.get()
    }
}

dependencies {
    // Modules
    implementation(projects.domain)
    implementation(projects.framework)
    implementation(projects.commons.theme)
    implementation(projects.commons.ui)
    implementation(projects.commons.util)
    implementation(projects.commons.testing)
    implementation(projects.commons.provider)

    implementation(libs.compose.ui.ui)
    implementation(libs.compose.foundation.foundation)
    implementation(libs.compose.material3)
    implementation(libs.coil.compose)
    implementation(libs.navigation.compose)
    implementation(libs.compose.material.iconsext)
    debugImplementation(libs.compose.ui.tooling)

    // DI
    implementation(libs.hilt.compose)
    implementation(libs.hilt.library)
    kapt(libs.hilt.compiler)

    // Other
    implementation(libs.icu4j)

    // Accompanist
    implementation(libs.accompanist.permissions)

    // Testing
    testImplementation(libs.junit)
    testImplementation(libs.junit.ext)
    testImplementation(libs.mockk)
    testImplementation(libs.coroutines.test)
}