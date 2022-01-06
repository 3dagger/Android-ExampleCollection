plugins {
    id("com.android.application")
    kotlin("android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}
android {
    compileSdk = ConfigData.compileSdkVersion

    defaultConfig {
        applicationId = "com.dagger.daggerhiltnetworkconnection"
        minSdk = ConfigData.minSdkVersion
        targetSdk = ConfigData.targetSdkVersion
        versionCode = ConfigData.versionCode
        versionName = ConfigData.versionName
        vectorDrawables.useSupportLibrary = true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        javaCompileOptions {
            annotationProcessorOptions {
                arguments["dagger.hilt.disableModulesHaveInstallInCheck"] = "true"
            }
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        dataBinding = true
    }
}

dependencies {
    implementation(project(":data"))

    implementation(Dependencies.kotlin.stdLib)
    implementation(Dependencies.material)
    implementation(Dependencies.androidX.constraintLayout)
    implementation(Dependencies.androidX.fragment)
    implementation(Dependencies.androidX.recyclerView)

    implementation(Dependencies.androidX.lifecycle.extensions)
    implementation(Dependencies.androidX.lifecycle.liveData)
    implementation(Dependencies.androidX.lifecycle.viewModelKtx)
    implementation(Dependencies.androidX.lifecycle.viewModelSavedState)

    implementation(Dependencies.androidX.room.runtime)
    implementation(Dependencies.androidX.room.ktx)
    kapt(Dependencies.androidX.room.compiler)

    implementation(Dependencies.hilt.android)
    kapt(Dependencies.hilt.compiler)
    kapt(Dependencies.hilt.androidXCompiler)

    implementation(Dependencies.retrofit.retrofit)
    implementation(Dependencies.retrofit.gsonConverter)
    implementation(Dependencies.retrofit.moshiConverter)

    implementation(Dependencies.okHttp)
    implementation(Dependencies.okHttp.loggingInterceptor)

    implementation(Dependencies.moshi.kotlin)
    kapt(Dependencies.moshi.codeGen)

    implementation(Dependencies.kotlin.coroutine)
    implementation(Dependencies.kotlin.coroutineAndroid)

    implementation(Dependencies.logger)

    implementation(Dependencies.glide.glide)
    kapt(Dependencies.glide.compiler)
}

