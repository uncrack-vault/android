plugins {
    id ("com.android.application")
    id ("org.jetbrains.kotlin.android")
    id ("kotlin-parcelize")
    id ("kotlin-kapt")
    id ("com.google.gms.google-services")
    id ("com.google.firebase.crashlytics")
    id ("com.google.devtools.ksp")

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
    buildFeatures{
        viewBinding = true
    }
}

dependencies {

    val roomVersion = "2.5.2"
    val viewModelVersion = "2.5.1"
    val navVersion = "2.7.2"

    implementation ("androidx.core:core-ktx:1.10.1")
    implementation ("androidx.appcompat:appcompat:1.6.1")
    implementation ("com.google.android.material:material:1.9.0")
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
    api ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.2")
    api ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.2")

    // Lottie Animation
    implementation ("com.airbnb.android:lottie:6.3.0")

    // Password Strength Meter
    implementation ("nu.aaro.gustav:passwordstrengthmeter:0.4")

    // Firebase
    implementation ("com.google.firebase:firebase-crashlytics-ktx:18.6.3")
    implementation ("com.google.firebase:firebase-analytics-ktx:21.3.0")
    implementation("com.google.firebase:firebase-messaging-ktx:23.2.1")

    // In-app update
    implementation ("com.google.android.play:core:1.10.3")

    // Circular Progress bar
    implementation ("com.mikhaellopez:circularprogressbar:3.1.0")

}