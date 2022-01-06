buildscript {
    repositories {
        google()
        mavenCentral()
//        maven { url "https://oss.sonatype.org/content/repositories/snapshots/" }
        gradlePluginPortal()
    }
    dependencies {
        classpath(kr.dagger.buildsrc.BuildPlugins.kotlin)
        classpath(kr.dagger.buildsrc.BuildPlugins.hilt)
        classpath(kr.dagger.buildsrc.BuildPlugins.android)
        classpath(kr.dagger.buildsrc.BuildPlugins.ksp)
//        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.10")
//        classpath "com.android.tools.build:gradle:$versions.gradleBuildTool"
//        classpath "org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.10"
//        classpath "com.google.dagger:hilt-android-gradle-plugin:$versions.hiltCoreVersion"

    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

//task clean(type: Delete) {
//    delete rootProject.buildDir
//}

tasks.register("clean", Delete::class){
    delete(rootProject.buildDir)
}