object Depends {
    object Kotlin {
        const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlin}"
    }

    object Support {
        const val appcompat = "androidx.appcompat:appcompat:1.1.0"
        const val cardView = "androidx.cardview:cardview:${Versions.supportLibrary}"
        const val constraint = "androidx.constraintlayout:constraintlayout:2.0.0-beta2"
        const val recyclerView = "androidx.recyclerview:recyclerview:${Versions.supportLibrary}"
        const val material = "com.google.android.material:material:1.1.0-alpha10"
    }

    object OkHttp3 {
        const val loggingIntercepter = "com.squareup.okhttp3:logging-interceptor:4.0.1"
    }

    object Retrofit {
        const val core = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
        const val converterMoshi = "com.squareup.retrofit2:converter-moshi:${Versions.retrofit}"
    }

    const val moshi = "com.squareup.moshi:moshi-kotlin:1.9.2"

    object LifeCycle {
        const val runtime = "androidx.lifecycle:lifecycle-runtime:${Versions.archLifecycle}"
        const val extensions = "androidx.lifecycle:lifecycle-extensions:${Versions.archLifecycle}"
        const val livedata = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.archLifecycle}"
    }

    object Room {
        const val runtime = "androidx.room:room-runtime:${Versions.archRoom}"
        const val compiler = "androidx.room:room-compiler:${Versions.archRoom}"
    }

    object Koin {
        const val scope = "org.koin:koin-androidx-scope:${Versions.koin}"
        const val viewModel = "org.koin:koin-androidx-viewmodel:${Versions.koin}"
    }

    object Coroutines {
        const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.corutines}"
        const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.corutines}"
        const val adapter = "com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:0.9.2"
        const val cache = "com.epam.coroutinecache:coroutinecache:0.9.1"
    }

    const val fresco = "com.facebook.fresco:fresco:2.1.0"

    const val ktlint = "com.github.shyiko:ktlint:0.30.0"

    object Epoxy {
        const val core = "com.airbnb.android:epoxy:${Versions.epoxy}"
        const val processor = "com.airbnb.android:epoxy-processor:${Versions.epoxy}"
        const val databinding = "com.airbnb.android:epoxy-databinding:${Versions.epoxy}"
    }

    const val junit = "junit:junit:4.12"

    object SupportTest {
        const val runner = "androidx.test.ext:junit:1.1.0"
        const val espresso = "androidx.test.espresso:espresso-core:3.1.1"
    }

    object Navigation {
        const val ui = "androidx.navigation:navigation-ui-ktx:${Versions.navigation}"
        const val fragment = "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}"
    }

    const val viewPager2 = "androidx.viewpager2:viewpager2:1.0.0"

    const val kotpref = "com.chibatching.kotpref:kotpref:2.10.0"
}


