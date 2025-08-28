plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.google.gms.google.services)
    id("org.jetbrains.kotlin.plugin.compose")
    id("com.google.dagger.hilt.android")
    id("kotlin-kapt")
}

android {
    namespace = "com.example.medicine_reminder"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.medicine_reminder"
        minSdk = 26
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.14"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    // --- Networking ---
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // --- Firebase + Google Auth ---
    implementation("com.google.android.gms:play-services-auth:21.1.0")
    implementation(platform("com.google.firebase:firebase-bom:34.1.0"))
    implementation("com.google.firebase:firebase-analytics")
    implementation(libs.firebase.auth)

    // --- Core AndroidX ---
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    // --- Jetpack Compose ---
    implementation(platform("androidx.compose:compose-bom:2025.01.00"))

    // Core UI
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.ui.test)
    debugImplementation("androidx.compose.ui:ui-tooling")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-text")
    implementation(libs.androidx.ui.text)

    // Material
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.material:material-icons-extended")

    // Foundation (layouts, gestures, scrolling, etc.)
    implementation("androidx.compose.foundation:foundation")
    implementation("androidx.compose.foundation:foundation-layout")

    // Activity & Lifecycle
    implementation("androidx.activity:activity-compose")
    implementation("androidx.lifecycle:lifecycle-runtime-compose")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.2")

    // Optional: animations
    implementation("androidx.compose.animation:animation")
    implementation("androidx.compose.animation:animation-graphics")

    // UI Tests
    androidTestImplementation(platform("androidx.compose:compose-bom:2025.01.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    // Image Loading
    implementation("io.coil-kt:coil-compose:2.7.0")

    // --- Google Login helpers ---
    implementation(libs.androidx.credentials)
    implementation(libs.androidx.credentials.play.services.auth)
    implementation(libs.googleid)

    // --- Room Database ---
    implementation("androidx.room:room-runtime:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")
    kapt("androidx.room:room-compiler:2.6.1")

    // --- Hilt Dependency Injection ---
    implementation("com.google.dagger:hilt-android:2.51")
    kapt("com.google.dagger:hilt-android-compiler:2.51")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")

    // --- Testing ---
//    androidTestImplementation(platform("androidx.test:runner-bom:1.5.0"))
//    androidTestImplementation("androidx.test.espresso:espresso-core")
//    androidTestImplementation("androidx.test.ext:junit")
}
