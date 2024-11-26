// Thêm các import cần thiết
import org.gradle.api.JavaVersion

plugins {
    alias(libs.plugins.android.application)
    id("com.google.gms.google-services") // Thêm plugin Google services
}

android {
    namespace = "com.example.cake"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.cake"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    // Thêm các thư viện cần thiết
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.firebase.database)
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.storage)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation(libs.gson)
    implementation(libs.glide)
    implementation(libs.dotsindicator)
    implementation ("androidx.annotation:annotation:1.6.0")
    implementation("org.jetbrains.kotlin:kotlin-script-runtime:1.8.0") // Thay 1.8.0 với phiên bản Kotlin của bạn
    implementation(platform("com.google.firebase:firebase-bom:33.2.0")) // BoM của Firebase
    implementation(libs.firebase.auth) // Thư viện Firebase Authentication

    // Thư viện dịch vụ Google Play
    implementation(libs.play.services.auth)
    implementation ("androidx.recyclerview:recyclerview:1.2.1")
    implementation ("androidx.cardview:cardview:1.0.0")
    implementation ("androidx.appcompat:appcompat:1.6.1")
    implementation ("com.google.android.material:material:1.9.0")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.15.1")
    implementation ("com.google.gms:google-services:4.4.2")
    implementation("com.google.firebase:firebase-storage")
    implementation ("com.google.firebase:firebase-firestore:24.0.0")
    implementation(platform("com.google.firebase:firebase-bom:33.5.1"))
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.gms:google-services")




// Dành cho Google SignIn

}
