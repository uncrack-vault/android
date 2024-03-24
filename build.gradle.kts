// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    dependencies {
        classpath ("androidx.navigation:navigation-safe-args-gradle-plugin:2.5.3")
        classpath ("com.android.tools.build:gradle:8.3.1")
        classpath ("com.google.gms:google-services:4.3.15")
        classpath ("com.google.firebase:firebase-crashlytics-gradle:2.8.1")
    }
}
plugins {
    id ("com.android.application") version "7.4.2" apply false
    id ("com.android.library") version "7.4.2" apply false
    id ("org.jetbrains.kotlin.android") version "1.9.10" apply false
    id("com.google.devtools.ksp") version "1.9.22-1.0.16" apply false
}
allprojects {
    repositories {
        maven ( url = "https://jitpack.io")
    }
}