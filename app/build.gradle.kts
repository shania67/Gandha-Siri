plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
}

android {

    namespace = "com.example.gandha_siri"

    compileSdk = 34

    defaultConfig {

        applicationId = "com.example.gandha_siri"

        minSdk = 26
        targetSdk = 34

        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner =
            "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {

        release {

            isMinifyEnabled = false

            proguardFiles(

                getDefaultProguardFile(
                    "proguard-android-optimize.txt"
                ),

                "proguard-rules.pro"
            )
        }
    }

    compileOptions {

        sourceCompatibility =
            JavaVersion.VERSION_17

        targetCompatibility =
            JavaVersion.VERSION_17
    }

    kotlinOptions {

        jvmTarget = "17"
    }

    buildFeatures {

        compose = true
    }

    composeOptions {

        kotlinCompilerExtensionVersion = "1.5.14"
    }

    packaging {

        resources {

            excludes +=
                "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    // CORE
    implementation(
        "androidx.core:core-ktx:1.13.1"
    )

    implementation(
        "androidx.lifecycle:lifecycle-runtime-ktx:2.8.4"
    )

    implementation(
        "androidx.activity:activity-compose:1.9.2"
    )

    // COMPOSE BOM
    implementation(
        platform(
            "androidx.compose:compose-bom:2024.06.00"
        )
    )

    // COMPOSE UI
    implementation(
        "androidx.compose.ui:ui"
    )

    implementation(
        "androidx.compose.ui:ui-graphics"
    )

    implementation(
        "androidx.compose.ui:ui-tooling-preview"
    )

    implementation(
        "androidx.compose.material3:material3"
    )

    implementation(
        "androidx.compose.material:material-icons-extended"
    )

    debugImplementation(
        "androidx.compose.ui:ui-tooling"
    )

    debugImplementation(
        "androidx.compose.ui:ui-test-manifest"
    )

    // LIFECYCLE COMPOSE
    implementation(
        "androidx.lifecycle:lifecycle-runtime-compose:2.8.4"
    )

    // ROOM DATABASE
    implementation(
        "androidx.room:room-runtime:2.6.1"
    )

    implementation(
        "androidx.room:room-ktx:2.6.1"
    )

    ksp(
        "androidx.room:room-compiler:2.6.1"
    )

    // COIL IMAGE LOADING
    implementation(
        "io.coil-kt:coil-compose:2.6.0"
    )

    // LOCATION SERVICES
    implementation(
        "com.google.android.gms:play-services-location:21.0.1"
    )

    // TESTING
    testImplementation(
        "junit:junit:4.13.2"
    )

    androidTestImplementation(
        "androidx.test.ext:junit:1.2.1"
    )

    androidTestImplementation(
        "androidx.test.espresso:espresso-core:3.6.1"
    )
}