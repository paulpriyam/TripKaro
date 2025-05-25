// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.kotlin.kapt) apply false
    id("com.google.dagger.hilt.android") version "${libs.versions.hilt.get()}" apply false
}

// Define common repositories for all projects
//    repositories {
//        google()
//        mavenCentral()
//    }


