plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
}

configurations.all {
    resolutionStrategy {
        val coroutinesVersion = libs.versions.kotlinxCoroutinesTest.get()
        force("org.jetbrains.kotlin:kotlin-stdlib:${libs.versions.kotlin.get()}")
        force("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
        force("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion")
        force("org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutinesVersion")
        force("org.jetbrains.kotlinx:kotlinx-coroutines-test-jvm:$coroutinesVersion")
    }
}

android {
    namespace = "com.quriozzity"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.quriozzity"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    signingConfigs {
        create("release") {
            storeFile = file("/Users/debora.deotti/my-release-key.jks")
            storePassword = System.getenv("KEYSTORE_PASSWORD") ?: "dummyPassword"
            keyAlias = "release"
            keyPassword = System.getenv("KEY_PASSWORD") ?: "dummyPassword"
        }
    }

    buildTypes {
        release {
            signingConfig = signingConfigs.getByName("release")
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
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.composeCompiler.get()
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.kotlinx.coroutines.test.jvm)
    testImplementation(libs.turbine)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    // Retrofit for making API calls
    implementation(libs.retrofit)
    // Gson converter for parsing JSON responses
    implementation(libs.converter.gson)
    // Koin
    implementation(libs.koin.android)
    implementation(libs.koin.androidx.compose)
    // Navigation
    implementation(libs.androidx.navigation.compose)
    // Coil
    implementation(libs.coil.compose)
    implementation(libs.coil.svg)
}