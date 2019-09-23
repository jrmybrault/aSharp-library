object DependencyVersions {

    // SDK

    val minSdk = 19
    val targetSdk = 29
    val compileSdk = 28

    // KOTLIN

    val kotlin = "1.3.41"
    val gradle = "3.5.0"

    val coroutines = "1.0.1"

    // ANDROID X

    val safeArgs = "2.0.0"

    val appCompat = "1.1.0"
    val recyclerView = "1.0.0"
    val ktx = "1.1.0"
    val constraintLayout = "1.1.3"
    val navigation = "2.1.0"
    val lifecycle = "2.1.0"

    // DEPENDENCY INJECTION

    val koin = "2.0.1"

    // UNIT TEST

    val junit = "4.12"

    // INSTRUMENTED TEST

    val testRunner = "1.2.0"
    val espresso = "3.2.0"
}

object KotlinLibraries {

    val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${DependencyVersions.coroutines}"
}

object AndroidLibraries {

    val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${DependencyVersions.coroutines}"

    val koinCore = "org.koin:koin-android:${DependencyVersions.koin}"
    val koinScope = "org.koin:koin-android-scope:${DependencyVersions.koin}"
    val koinViewModel = "org.koin:koin-android-viewmodel:${DependencyVersions.koin}"

    val appCompat = "androidx.appcompat:appcompat:${DependencyVersions.appCompat}"
    val recyclerView = "androidx.recyclerview:recyclerview:${DependencyVersions.recyclerView}"
    val ktx = "androidx.core:core-ktx:${DependencyVersions.ktx}"
    val constraintLayout = "androidx.constraintlayout:constraintlayout:${DependencyVersions.constraintLayout}"
    val navigationFragment = "androidx.navigation:navigation-fragment-ktx:${DependencyVersions.navigation}"
    val navigationUI = "androidx.navigation:navigation-ui-ktx:${DependencyVersions.navigation}"
    val lifecycle = "androidx.lifecycle:lifecycle-extensions:${DependencyVersions.lifecycle}"
}

object TestLibraries {

    val junit = "junit:junit:${DependencyVersions.junit}"

    val runner = "androidx.test:runner:${DependencyVersions.testRunner}"
    val espresso = "androidx.test.espresso:espresso-core:${DependencyVersions.espresso}"
}