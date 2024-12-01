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
    // Import the BoM for the Firebase platform
    implementation(libs.firebase.bom)

    // Add the dependency for the Cloud Storage library
    // When using the BoM, you don't specify versions in Firebase library dependencies
    implementation(libs.google.firebase.storage)

    implementation ("androidx.appcompat:appcompat:1.6.1")
    implementation ("androidx.core:core-ktx:1.12.0")
    implementation ("com.google.android.gms:play-services-location:21.0.1")
    implementation ("com.google.android.gms:play-services-maps:18.1.0")

    implementation (libs.firebase.firestore)
    implementation (libs.firebase.auth)  // Optional, if using user authentication

    implementation (libs.drawerlayout)
    implementation (libs.glide.v4110)
    implementation(libs.play.services.maps)
    implementation(libs.firebase.storage)
    annotationProcessor (libs.compiler.v4110)

    implementation (libs.retrofit2.retrofit)
    implementation (libs.converter.gson)

    implementation (libs.glide)
    annotationProcessor (libs.github.compiler)

    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}
