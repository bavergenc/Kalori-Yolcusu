plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.example.kaloriyolcusu"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.example.kaloriyolcusu"
        minSdk = 24
        targetSdk = 33
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        viewBinding = true
        dataBinding = true
    }

}

dependencies {

    implementation( "androidx.core:core-ktx:1.9.0")
    implementation( "androidx.appcompat:appcompat:1.6.1")
    implementation ("com.google.android.material:material:1.5.0")
    implementation ("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation ("junit:junit:4.13.2")
    androidTestImplementation ("androidx.test.ext:junit:1.1.5")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.5.1")

    // sdp
    implementation( "com.intuit.sdp:sdp-android:1.1.0")

    // navigation
    implementation ("androidx.navigation:navigation-fragment-ktx:2.3.4")
    implementation ("androidx.navigation:navigation-ui-ktx:2.3.4")

    //retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.squareup.retrofit2:adapter-rxjava2:2.3.0")


    // rx java

    implementation ("io.reactivex.rxjava2:rxjava:2.2.19")
    implementation ("io.reactivex.rxjava2:rxandroid:2.1.1")
    //glide
    implementation ("com.github.bumptech.glide:glide:4.12.0")

    // room

    implementation ("androidx.room:room-runtime:2.5.2") // veya en son sürüm
    annotationProcessor ("androidx.room:room-compiler:2.5.2") // veya en son sürüm
    ksp("androidx.room:room-compiler:2.5.2")
    implementation ("androidx.room:room-ktx:2.5.2")

    implementation( "androidx.lifecycle:lifecycle-runtime:2.4.0")
    implementation ("androidx.lifecycle:lifecycle-livedata:2.4.0")


}