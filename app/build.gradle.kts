plugins {
    alias(libs.plugins.hilt)
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
}

android {
    namespace = "com.luisfagundes.parrotlingo"
    compileSdk = libs.versions.compile.sdk.get().toInt()

    defaultConfig {
        applicationId = "com.luisfagundes.parrotlingo"
        minSdk = libs.versions.min.sdk.get().toInt()
        targetSdk = libs.versions.target.sdk.get().toInt()
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
        getByName("debug") {
            versionNameSuffix = "-debug"
        }
    }

    sourceSets {
        getByName("debug") {
            java.srcDir("src/debug/java")
        }
        getByName("release") {
            java.srcDir("src/release/java")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = libs.versions.java.get()
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }

    packaging {
        resources {
            pickFirsts.add("META-INF/LICENSE.md")
            pickFirsts.add("META-INF/LICENSE-notice.md")
        }
    }

    hilt {
        enableAggregatingTask = true
    }
}

dependencies {
    // Modules
    implementation(project(":commons:theme"))
    implementation(project(":framework"))
    implementation(project(":features:translation"))
    implementation(project(":features:saved"))
    implementation(project(":features:settings"))
    implementation(project(":data"))
    implementation(project(":domain"))
    implementation(project(":commons:ui"))
    implementation(project(":commons:util"))
    implementation(project(":commons:provider"))

    // UI
    implementation(libs.navigation.compose)
    implementation(libs.androidx.core.ktx)
    implementation(libs.compose.ui.ui)
    implementation(libs.compose.foundation.foundation)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.compose.material3)
    implementation(libs.compose.material.iconsext)

    // DI
    implementation(libs.hilt.compose)
    implementation(libs.hilt.library)
    kapt(libs.hilt.compiler)

    // Logging
    implementation(libs.timber)
}
