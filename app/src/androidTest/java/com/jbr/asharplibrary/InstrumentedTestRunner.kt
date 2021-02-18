package com.jbr.asharplibrary

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import com.github.tmurakami.dexopener.DexOpener


/**
 * Custom instrumented test runner that :
 * - run the test application instead of the real one;
 * - use DexOpener to be able to mock final classses.
 */
class InstrumentedTestRunner : AndroidJUnitRunner() {

    @Throws(ClassNotFoundException::class, IllegalAccessException::class, InstantiationException::class)
    override fun newApplication(cl: ClassLoader, className: String, context: Context): Application {
        DexOpener.install(this) // Call me first!
        return super.newApplication(cl, KoinTestApplication::class.java.name, context)
    }
}