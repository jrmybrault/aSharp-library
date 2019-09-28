package com.jbr.asharplibrary.experimentationlegacy

import android.os.Bundle
import androidx.navigation.NavDeepLinkBuilder
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.jbr.asharplibrary.MainActivity
import com.jbr.asharplibrary.R
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.module.Module

@RunWith(AndroidJUnit4::class)
abstract class BaseFragmentTest(private val fragmentId: Int, private val bundle: Bundle? = null) {

    @get:Rule
    val activityRule = ActivityTestRule(MainActivity::class.java, true, false)

    abstract fun createTestModule(): Module

    @Before
    fun before() {
        val mockModule = createTestModule()
        startKoin { modules(listOf(mockModule)) }

        // Fake navigation to the test fragment.
        val intent = NavDeepLinkBuilder(InstrumentationRegistry.getInstrumentation().targetContext)
            .setGraph(R.navigation.navigation_graph)
            .setDestination(fragmentId)
            .setArguments(bundle)
            .createTaskStackBuilder().intents[0]

        activityRule.launchActivity(intent)
    }

    @After
    fun after() {
        activityRule.finishActivity()
        stopKoin()
    }
}
