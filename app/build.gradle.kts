plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.salesapp"
    compileSdk = 34

    viewBinding{
        enable = true
    }

    dataBinding{
        enable = true
    }

    defaultConfig {
        applicationId = "com.example.salesapp"
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
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation (libs.google.material.v120)

    //    RoomDataBase Dependency
    implementation (libs.room.runtime)
    annotationProcessor (libs.room.compiler)


    // ViewModel
    implementation (libs.lifecycle.viewmodel)
    // LiveData
    implementation (libs.lifecycle.livedata)
    implementation (libs.lifecycle.extensions)
}