buildscript {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
    dependencies {
        classpath(kr.dagger.buildsrc.BuildPlugins.kotlin)
        classpath(kr.dagger.buildsrc.BuildPlugins.hilt)
        classpath(kr.dagger.buildsrc.BuildPlugins.android)
        classpath(kr.dagger.buildsrc.BuildPlugins.ksp)
        classpath("com.android.tools.build:gradle:7.0.2")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

tasks.register("clean", Delete::class){
    delete(rootProject.buildDir)
}