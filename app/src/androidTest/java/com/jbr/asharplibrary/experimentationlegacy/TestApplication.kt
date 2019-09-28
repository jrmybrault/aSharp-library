package com.jbr.asharplibrary.experimentationlegacy

import android.annotation.SuppressLint
import android.app.Application

/**
 * Custom application to control Koin modules in instrumented tests.
 */
@SuppressLint("Registered")
class TestApplication : Application()
