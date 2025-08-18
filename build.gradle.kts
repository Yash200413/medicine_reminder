// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.google.gms.google.services) apply false

    // Required for Kotlin 2.0 + Compose
    id("org.jetbrains.kotlin.plugin.compose") version "2.0.0" apply false
}
