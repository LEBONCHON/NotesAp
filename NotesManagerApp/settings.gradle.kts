pluginManagement {
    repositories {
        google()
        mavenCentral()
    }

    plugins {
        id("com.android.application") version "8.0.0"
        id("com.google.gms.google-services") version "4.4.0"
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "NotesManagerApp"
include(":app")

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
