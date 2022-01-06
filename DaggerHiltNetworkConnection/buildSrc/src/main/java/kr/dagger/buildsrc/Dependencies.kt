import kr.dagger.buildsrc.Versions

object Dependencies {
    val kotlin = Kotlin
    val hilt = Hilt
    val androidX = AndroidX
    val retrofit = Retrofit
    val okHttp = OkHttp()
    val moshi = Moshi
    val glide = Glide
    val test = Test
    val androidTest = AndroidTest


    val material by lazy { "com.google.android.material:material:1.4.0" }
    val javaInject by lazy { "javax.inject:javax.inject:1" }
    val logger by lazy { "com.orhanobut:logger:${Versions.loggerVersion}" }


    object Kotlin {
        val stdLib by lazy { "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}" }
        val coroutine by lazy { "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutinesVersion}" }
        val coroutineAndroid by lazy { "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutinesVersion}" }
    }

    object Hilt {
        val android by lazy { "com.google.dagger:hilt-android:${Versions.hiltCoreVersion}" }
        val compiler by lazy { "com.google.dagger:hilt-android-compiler:${Versions.hiltCoreVersion}" }
        val androidXCompiler by lazy { "androidx.hilt:hilt-compiler:${Versions.hiltVersion}" }
    }

    object AndroidX {
        val core by lazy { "androidx.core:core-ktx:1.7.0" }
        val appCompat by lazy { "androidx.appcompat:appcompat:1.4.0" }
        val constraintLayout by lazy { "androidx.constraintlayout:constraintlayout:2.0.4"}
        val fragment by lazy { "androidx.fragment:fragment-ktx:1.3.0" }
        val recyclerView by lazy { "androidx.recyclerview:recyclerview:${Versions.recyclerViewVersion}" }

        val junit by lazy { "androidx.test.ext:junit:1.1.3" }
        val espresso by lazy { "androidx.test.espresso:espresso-core:3.3.0" }

        val lifecycle = LifeCycle
        object LifeCycle {
            val extensions by lazy { "androidx.lifecycle:lifecycle-extensions:${Versions.lifecycleVersion}" }
            val viewModelKtx by lazy { "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycleVersion}" }
            val viewModelSavedState by lazy { "androidx.lifecycle:lifecycle-viewmodel-savedstate:${Versions.lifecycleVersion}" }
            val liveData by lazy { "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycleVersion}" }
        }

        val room = Room
        object Room {
            val runtime by lazy { "androidx.room:room-runtime:${Versions.roomVersion}" }
            val ktx by lazy { "androidx.room:room-ktx:${Versions.roomVersion}" }
            val compiler by lazy { "androidx.room:room-compiler:${Versions.roomVersion}" }
        }
    }

    object Retrofit {
        val retrofit by lazy { "com.squareup.retrofit2:retrofit:${Versions.retrofitVersion}" }
        val gsonConverter by lazy { "com.squareup.retrofit2:converter-gson:${Versions.retrofitVersion}" }
        val moshiConverter by lazy { "com.squareup.retrofit2:converter-moshi:${Versions.retrofitVersion}" }
    }

    object Moshi {
        val kotlin by lazy { "com.squareup.moshi:moshi-kotlin:${Versions.moshiVersion}" }
        val codeGen by lazy { "com.squareup.moshi:moshi-kotlin-codegen:${Versions.moshiVersion}" }
    }

    class OkHttp(private val name: String = "com.squareup.okhttp3:okhttp:${Versions.okhttpVersion}") : CharSequence by name {
        val loggingInterceptor by lazy { "com.squareup.okhttp3:logging-interceptor:${Versions.okhttpVersion}" }
        override fun toString() = name
    }

    object Glide {
        val glide by lazy { "com.github.bumptech.glide:glide:${Versions.glideVersion}" }
        val compiler by lazy {"com.github.bumptech.glide:compiler:${Versions.glideVersion}"  }
    }

    object Test {
        val junit by lazy { "junit:junit:4.13.2" }
        val robolectric by lazy { "org.robolectric:robolectric:4.6.1" }
        val archCore by lazy { "androidx.arch.core:core-testing:2.1.0" }
        val mockito by lazy { "org.mockito:mockito-core:3.8.0" }
        val coroutine by lazy { "org.jetbrains.kotlinx:kotlinx-coroutines-test:1.5.1" }
    }

    object AndroidTest {
        val runner by lazy { "androidx.test:runner:1.4.0" }
        val rules by lazy { "androidx.test:rules:1.4.0" }
        val archCore by lazy { "androidx.arch.core:core-testing:2.1.0" }
        val junit by lazy { "androidx.test.ext:junit-ktx:1.1.3" }
        val espressoCore by lazy { "androidx.test.espresso:espresso-core:3.3.0" }
        val espressoContribute by lazy { "androidx.test.espresso:espresso-contrib:3.3.0" }
        val mockito by lazy { "org.mockito:mockito-android:3.8.0" }
        val hilt by lazy { "com.google.dagger:hilt-android-testing:${Versions.hiltCoreVersion}" }
        val fragment by lazy { "androidx.fragment:fragment-testing:1.3.6" }
    }
}