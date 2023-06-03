import org.jetbrains.kotlin.konan.properties.Properties
import java.io.FileInputStream

plugins {
    alias(libs.plugins.hilt)
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
}

fun getApiKey(): Any? {
    val apiKeyPropertiesFile: File = rootProject.file("apikey.properties")
    val apiKeyProperties = Properties()
    apiKeyProperties.load(FileInputStream(apiKeyPropertiesFile))
    return apiKeyProperties["API_KEY"]
}

android {
    namespace = "com.luisfagundes.data"
    compileSdk = libs.versions.compile.sdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.min.sdk.get().toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        buildConfigField("String", "API_KEY", "\"${getApiKey()}\"")
        buildConfigField("String", "API_REGION", "\"brazilsouth\"")
        buildConfigField("String", "BASE_URL", "\"https://api.cognitive.microsofttranslator.com/\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
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
    implementation(projects.framework)
    implementation(projects.domain)
    implementation(projects.commons.testing)

    // Core
    implementation(libs.core)

    // Network
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.okhttp.interceptor)

    // DI
    implementation(libs.hilt.compose)
    implementation(libs.hilt.library)
    kapt(libs.hilt.compiler)

    // Data
    implementation(libs.datastore)
    implementation(libs.room.runtime)
    kapt(libs.room.compiler)

    // Testing
    testImplementation(libs.junit)
    testImplementation(libs.junit.ext)
    testImplementation(libs.coroutines.test)
    testImplementation(libs.mockk)
    testImplementation(libs.roboletric)

    // Logging
    implementation(libs.timber)
}
