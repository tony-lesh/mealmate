plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.example.mealmate"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.mealmate"
        minSdk = 26
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
    implementation(libs.firebase.database)

    implementation (libs.play.services.auth)

    implementation (libs.play.services.maps.v1802)

    implementation (libs.appcompat.v140)  // Or the latest version

   // implementation ("com.google.android.gms:play-services-maps:19.0.0") // Use the latest version


    implementation (libs.firebase.firestore)
    implementation (libs.firebase.auth)  // Optional, if using user authentication

    implementation (libs.drawerlayout)
    implementation (libs.glide.v4110)
    implementation(libs.play.services.maps)
    annotationProcessor (libs.compiler.v4110)

    implementation (libs.retrofit2.retrofit)
    implementation (libs.converter.gson)

    implementation (libs.glide)
    annotationProcessor (libs.github.compiler)

    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}
