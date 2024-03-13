plugins {
    id ("com.android.application")
    id ("org.jetbrains.kotlin.android")
    id ("kotlin-parcelize")
    id ("kotlin-kapt")
    id ("com.google.gms.google-services")
    id ("com.google.firebase.crashlytics")
    id ("com.google.devtools.ksp")
    id ("com.google.dagger.hilt.android")
}

android {
    namespace = "com.geekymusketeers.uncrack"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.geekymusketeers.uncrack"
        minSdk = 24
        targetSdk = 34
        versionCode = 10
        versionName = "2.1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    lint {
        baseline = file("lint-baseline.xml")
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles (getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
        getByName("debug") {
            isDebuggable = true
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        viewBinding = true
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.4"
    }
}

dependencies {

    val roomVersion = "2.6.1"
    val viewModelVersion = "2.5.1"
    val navVersion = "2.7.2"

    // Compose
    val composeBom = platform("androidx.compose:compose-bom:2023.10.01")
    implementation(composeBom)
    implementation("androidx.compose.runtime:runtime")
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.foundation:foundation")
    implementation("androidx.compose.foundation:foundation-layout")
    implementation("androidx.compose.material:material")
    implementation("androidx.compose.material3:material3:1.2.0")
    implementation("androidx.compose.runtime:runtime-livedata")
    implementation("androidx.compose.ui:ui-tooling")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.constraintlayout:constraintlayout-compose:1.0.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")
    implementation("androidx.activity:activity-compose:1.9.0-alpha03")
    implementation("androidx.navigation:navigation-compose:2.7.7")


    // Compose Test
    androidTestImplementation(composeBom)
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    // Default
    implementation ("androidx.core:core-ktx:1.12.0")
    implementation ("androidx.appcompat:appcompat:1.6.1")
    implementation ("com.google.android.material:material:1.11.0")
    implementation ("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation ("androidx.legacy:legacy-support-v4:1.0.0")
    implementation ("androidx.recyclerview:recyclerview:1.3.1")

    // Test
    testImplementation ("junit:junit:4.13.2")
    androidTestImplementation ("androidx.test.ext:junit:1.1.5")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.5.1")
    testImplementation ("com.google.truth:truth:1.2.0")
    androidTestImplementation ("androidx.test:runner:1.5.2")
    androidTestImplementation ("androidx.test:rules:1.5.0")

    // Room
    implementation ("androidx.room:room-runtime:$roomVersion")
    annotationProcessor ("androidx.room:room-compiler:$roomVersion")
    ksp ("androidx.room:room-compiler:$roomVersion")
    implementation ("androidx.room:room-ktx:$roomVersion")

    //ViewModel
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:$viewModelVersion")
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:$viewModelVersion")

    // Navigation Component
    implementation ("androidx.navigation:navigation-fragment:$navVersion")
    implementation ("androidx.navigation:navigation-fragment-ktx:$navVersion")
    implementation ("androidx.navigation:navigation-ui-ktx:$navVersion")

    // Kotlin components
    implementation ("org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.9.22")
    api ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    api ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    // Hilt
    implementation("com.google.dagger:hilt-android:2.49")
    kapt("com.google.dagger:hilt-android-compiler:2.48")
    implementation("androidx.hilt:hilt-work:1.2.0")
    kapt("androidx.hilt:hilt-compiler:1.2.0")
    implementation("androidx.work:work-runtime-ktx:2.9.0")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")

    // Datastore
    implementation("androidx.datastore:datastore-preferences:1.0.0")

    // Lottie Animation
    implementation ("com.airbnb.android:lottie:6.3.0")

    // Password Strength Meter
    implementation ("nu.aaro.gustav:passwordstrengthmeter:0.4")

    // Firebase
    (platform("com.google.firebase:firebase-bom:31.1.1"))
    implementation ("com.google.firebase:firebase-crashlytics-ktx:18.6.2")
    implementation ("com.google.firebase:firebase-analytics-ktx:21.5.1")
    implementation("com.google.firebase:firebase-messaging-ktx:23.4.1")

    // In-app update
    implementation ("com.google.android.play:core:1.10.3")

    // Compose-lint
    lintChecks("com.slack.lint.compose:compose-lint-checks:1.3.1")

    // Timber
    implementation ("com.jakewharton.timber:timber:5.0.1")

    //kotlinx.collections.immutable
    implementation("org.jetbrains.kotlinx:kotlinx-collections-immutable:0.3.7")

    // Circular Progress bar
    implementation ("com.mikhaellopez:circularprogressbar:3.1.0")

    // Coil
    implementation("io.coil-kt:coil-compose:2.6.0")
}