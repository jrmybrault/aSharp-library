object DependencyVersions {

    // SDK

    val minSdk = 19
    val targetSdk = 29
    val compileSdk = 28

    // KOTLIN

    val kotlin = "1.3.41"
    val gradle = "3.5.0"

    val coroutines = "1.0.1"

    // DEPENDENCY INJECTION

    val koin = "2.0.1"

    // UI

    val safeArgs = "2.0.0"
    val recyclerView = "1.0.0"
    val constraintLayout = "1.1.3"
    val navigation = "2.1.0"
    val lifecycle = "2.1.0"

    // NETWORKING

    val retrofit = "2.6.1"
    val retrofitLogging = "4.2.0"
    val retrofitCoroutinesAdapter = "0.9.2"

    val gson = "2.8.5"

    val glide = "4.10.0"

    // DATABASE

    val realm = "5.15.1"

    // LOGGING

    val timber = "4.7.1"

    // MISCELLANEOUS

    val appCompat = "1.1.0"
    val ktx = "1.1.0"

    // UNIT TEST

    val junit = "4.12"

    // INSTRUMENTED TEST

    val testRunner = "1.2.0"
    val espresso = "3.2.0"

    // QUALIMETRY

    val detekt = "1.0.1" // The detekt plugin configuration (in application build.gradle) can't use this constant so do not forget to change it there too
}

object KotlinLibraries {

    val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${DependencyVersions.coroutines}"
}

object AndroidLibraries {

    // KOTLIN

    val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${DependencyVersions.coroutines}"

    // DEPENDENCY INJECTION

    val koinCore = "org.koin:koin-android:${DependencyVersions.koin}"
    val koinScope = "org.koin:koin-android-scope:${DependencyVersions.koin}"
    val koinViewModel = "org.koin:koin-android-viewmodel:${DependencyVersions.koin}"

    // UI

    val recyclerView = "androidx.recyclerview:recyclerview:${DependencyVersions.recyclerView}"
    val constraintLayout = "androidx.constraintlayout:constraintlayout:${DependencyVersions.constraintLayout}"
    val navigationFragment = "androidx.navigation:navigation-fragment-ktx:${DependencyVersions.navigation}"
    val navigationUI = "androidx.navigation:navigation-ui-ktx:${DependencyVersions.navigation}"
    val lifecycle = "androidx.lifecycle:lifecycle-extensions:${DependencyVersions.lifecycle}"

    // NETWORKING

    val retrofit = "com.squareup.retrofit2:retrofit:${DependencyVersions.retrofit}"
    val retrofitLogging = "com.squareup.okhttp3:logging-interceptor:${DependencyVersions.retrofitLogging}"
    val retrofitGsonAdapter = "com.squareup.retrofit2:converter-gson:${DependencyVersions.retrofit}"
    val retrofitCoroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${DependencyVersions.coroutines}"
    val retrofitCoroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${DependencyVersions.coroutines}"
    val retrofitCoroutinesAdapter = "com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:${DependencyVersions.retrofitCoroutinesAdapter}"

    val gson = "com.google.code.gson:gson:${DependencyVersions.gson}"

    val glide = "com.github.bumptech.glide:glide:${DependencyVersions.glide}"
    
    // LOGGING

    val timber = "com.jakewharton.timber:timber:${DependencyVersions.timber}"

    // MISCELLANEOUS

    val appCompat = "androidx.appcompat:appcompat:${DependencyVersions.appCompat}"
    val ktx = "androidx.core:core-ktx:${DependencyVersions.ktx}"
}

object TestLibraries {

    val junit = "junit:junit:${DependencyVersions.junit}"

    val runner = "androidx.test:runner:${DependencyVersions.testRunner}"
    val espresso = "androidx.test.espresso:espresso-core:${DependencyVersions.espresso}"
}