plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.diaryapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.diaryapp"
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
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        viewBinding = true
        dataBinding = true
    }

}

dependencies {

    implementation("androidx.core:core-ktx:1.10.1")

    implementation("androidx.activity:activity:1.8.1")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.android.gms:play-services-location:21.3.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation (platform("com.google.firebase:firebase-bom:33.5.1"))
    implementation ("com.google.firebase:firebase-messaging")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    //Api호출을 위한 의존성추가
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.squareup.okhttp3:okhttp:4.9.0")
    implementation ("com.squareup.retrofit2:converter-scalars:2.9.0")

    //Material Design
    implementation ("com.google.android.material:material:1.2.0-alpha02")

//Rounded Image View
    implementation ("com.makeramen:roundedimageview:2.3.0")

//Navigation Component
    implementation ("androidx.navigation:navigation-fragment-ktx:2.2.0-rc03")
    implementation ("androidx.navigation:navigation-ui-ktx:2.2.0-rc03")

    implementation ("com.naver.maps:map-sdk:3.19.1")
}