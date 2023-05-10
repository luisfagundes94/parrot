plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.luisfagundes.commons_testing"
    compileSdk = libs.versions.compile.sdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.min.sdk.get().toInt()
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
    api("junit:junit:4.13.2")
    api("androidx.test.ext:junit:1.1.5")
    api("androidx.test.espresso:espresso-core:3.5.1")
    api("org.junit.jupiter:junit-jupiter")
    api("junit:junit:4.13.2")
    api("androidx.test.ext:junit:1.1.5")
    api("androidx.test.espresso:espresso-core:3.5.1")
    api("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4")
    api("io.mockk:mockk:1.13.3")
    api("app.cash.turbine:turbine:0.7.0")
    api("com.google.truth:truth:1.1.3")
}