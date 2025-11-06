plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android") // Important for Kotlin projects
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.uchatapp"
    compileSdk = 36

    buildFeatures {
        viewBinding = true
    }

    defaultConfig {
        applicationId = "com.example.uchatapp"
        minSdk = 26
        targetSdk = 36
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
}

dependencies {
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.activity:activity:1.9.2")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    // Firebase BoM (controls versions automatically)
    implementation(platform("com.google.firebase:firebase-bom:33.2.0"))
    implementation("com.google.firebase:firebase-auth-ktx")
    implementation("com.google.firebase:firebase-database-ktx")
    implementation("com.google.firebase:firebase-storage-ktx")
    implementation(libs.firebase.database)
    implementation("com.google.firebase:firebase-storage")

    // OTP View
    implementation("com.github.mukeshsolanki.android-otpview-pinview:otpview:3.1.0")

    implementation(libs.recyclerview)
    implementation(libs.cardview)
    implementation(libs.firebase.messaging)
    implementation(libs.firebase.config)

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
    //circle image view
    implementation("de.hdodenhof:circleimageview:3.1.0")

    implementation("com.github.bumptech.glide:glide:5.0.5")

    //reactions library
    implementation("com.github.pgreze:android-reactions:1.6")

    //status view
    implementation("com.github.3llomi:CircularStatusView:V1.0.3")

    //story view
    implementation("com.github.OMARIHAMZA:StoryView:1.0.2-alpha")

    //shimmer loader
    implementation("com.facebook.shimmer:shimmer:0.5.0")

    implementation("com.github.mancj:MaterialSearchBar:0.8.5")
    implementation(libs.volley)
}
