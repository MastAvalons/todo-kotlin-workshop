import org.jetbrains.kotlin.config.KotlinCompilerVersion

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-android-extensions")
}

android {
    compileSdkVersion(27)
    defaultConfig {
        applicationId = "org.gradle.kotlin.dsl.samples.androidstudio"
        minSdkVersion(21)
        targetSdkVersion(27)
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }

    sourceSets {
        getByName("main").java.srcDirs("src/main/kotlin")
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    // Kotlin
    implementation(kotlin("stdlib-jdk7", KotlinCompilerVersion.VERSION))
    // Support
    implementation("com.android.support:design:27.1.1")
    implementation("com.android.support.constraint:constraint-layout:1.1.2")
    // Test
    testImplementation("junit:junit:4.12")
    androidTestImplementation("com.android.support.test:runner:1.0.2")
    androidTestImplementation("com.android.support.test.espresso:espresso-core:3.0.2")
}