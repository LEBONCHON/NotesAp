plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android") // ✅ Required for Kotlin support
    id("androidx.navigation.safeargs") // ✅ SafeArgs plugin (Java version)
}

android {
    namespace = "com.example.notesmanagerapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.notesmanagerapp"
        minSdk = 23
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }

    buildFeatures {
        viewBinding = true
    }

    // ✅ Required to fix Kotlin JVM target error
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    // Navigation Component
    implementation("androidx.navigation:navigation-fragment:2.7.0")
    implementation("androidx.navigation:navigation-ui:2.7.0")

    // RecyclerView & CardView
    implementation("androidx.recyclerview:recyclerview:1.3.2")
    implementation("androidx.cardview:cardview:1.0.0")
}
