import org.gradle.kotlin.dsl.*

plugins {
    id("com.android.application") version Versions.gradleBuildTool
    kotlin("android") version Versions.kotlin
    kotlin("kapt") version Versions.kotlin
    id("org.jlleitschuh.gradle.ktlint") version Versions.ktlintGradle
    id("com.github.ben-manes.versions") version Versions.gradleVersions
    id("androidx.navigation.safeargs") version "1.0.0"
}

// Manifest version
val versionMajor = 1
val versionMinor = 0
val versionPatch = 0

android {
    compileSdkVersion(Versions.compileSdk)
    dataBinding.isEnabled = true

    defaultConfig {
        applicationId = "com.example.matsubajun.githubsample"
        minSdkVersion(Versions.minSdk)
        targetSdkVersion(Versions.targetSdk)
        versionCode = versionMajor * 10000 + versionMinor * 100 + versionPatch
        versionName = "$versionMajor.$versionMinor.$versionPatch"
        testInstrumentationRunner = "com.example.matsubajun.githubsample.test.TestAppRunner"
        renderscriptTargetApi = 25
        renderscriptSupportModeEnabled = true

        buildConfigField("String", "GITHUB_USER_NAME", "\"" + Properties.githubUserName + "\"")
        buildConfigField("String", "GITHUB_ACCESS_TOKEN", "\"" + Properties.githubAccessToken + "\"")
        resValue("string", "github_user_name", "\"" + Properties.githubUserName + "\"")
    }

    signingConfigs {
        getByName("debug") {
            storeFile = rootProject.file("conf/debug.keystore")
            storePassword = "android"
            keyAlias = "androiddebugkey"
            keyPassword = "android"
        }
        create("release") {
            storeFile = rootProject.file("conf/matsubajun")
            storePassword = "matsuba"
            keyAlias = "key0"
            keyPassword = "matsuba"
        }
    }

    buildTypes {
        getByName("debug") {
            signingConfig = signingConfigs.getByName("debug")
            applicationIdSuffix = ".debug"
            isMinifyEnabled = false
        }
        getByName("release") {
            signingConfig = signingConfigs.getByName("release")
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), file("proguard-rules.pro"))
        }
    }

    lintOptions {
        isAbortOnError = false
    }

    compileOptions {
        setSourceCompatibility(1.8)
        setTargetCompatibility(1.8)
    }
    dexOptions {
        javaMaxHeapSize = "4g"
        preDexLibraries = true
        maxProcessCount = 8
    }
    aaptOptions {
        cruncherEnabled = false
    }
}


kapt {
    useBuildCache = true
}


repositories {
    mavenCentral()
    jcenter()
    google()
    flatDir {
        dirs("libs")
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to arrayOf("*.jar"))))
    implementation(project(":domain"))
    // Kotlin
    implementation(Depends.Kotlin.stdlib)

//==================== Support Library ====================
    implementation(Depends.Support.appcompat)
    implementation(Depends.Support.cardView)
    implementation(Depends.Support.constraint)
    implementation(Depends.Support.recyclerView)
    implementation(Depends.Support.material)

//==================== Network ====================
    implementation(Depends.OkHttp3.loggingIntercepter)

    implementation(Depends.Retrofit.core)
    implementation(Depends.Retrofit.converterMoshi)
    implementation(Depends.fresco)
    implementation(Depends.moshi)

//==================== Structure ====================
    implementation(Depends.LifeCycle.runtime)
    implementation(Depends.LifeCycle.extensions)
    implementation(Depends.LifeCycle.livedata)

    implementation(Depends.Room.runtime)
    kapt(Depends.Room.compiler)

    implementation(Depends.Koin.scope)
    implementation(Depends.Koin.viewModel)

    implementation(Depends.Coroutines.core)
    implementation(Depends.Coroutines.android)
    implementation(Depends.Coroutines.adapter)
    implementation(Depends.Coroutines.cache)

    implementation(Depends.Navigation.ui)
    implementation(Depends.Navigation.fragment)

//==================== UI ====================

    implementation(Depends.Epoxy.core)
    kapt(Depends.Epoxy.processor)
    implementation(Depends.Epoxy.databinding)
    implementation(Depends.viewPager2)


//==================== Util ====================
    implementation(Depends.kotpref)

//==================== Test ====================
    testImplementation(Depends.junit)
    androidTestImplementation(Depends.SupportTest.runner)
    androidTestImplementation(Depends.SupportTest.espresso)
}


//==================== Ktlint ====================
val ktlint by configurations.creating

dependencies {
    ktlint(Depends.ktlint)
}

tasks {
    register("ktlint", JavaExec::class.java) {
        group = "verification"
        classpath = ktlint
        main = "com.github.shyiko.ktlint.Main"
        args("src/**/*.kt")
    }

    register("ktlintFormat", JavaExec::class.java) {
        group = "formatting"
        classpath = ktlint
        main = "com.github.shyiko.ktlint.Main"
        args("-F", "src/**/*.kt")
    }
}
