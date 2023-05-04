package com.example.androidlist


import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.swipeDown

import android.support.test.espresso.action.ViewActions.swipeUp
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4

import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.lang.Thread.sleep


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @JvmField
    @Rule
    var activityActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.androidlist", appContext.packageName)
    }

    @Test
    fun clickRecyclerView() {
        onView(withId(R.id.articlesRecyclerView)).perform(click()).check(matches(isDisplayed()))
    }

    @Test
    fun swipeUpRecyclerView() {
        onView(withId(R.id.articlesRecyclerView)).perform(swipeUp()).check(matches(isDisplayed()))
    }

    @Test
    fun swipeRecyclerView() {
        onView(withId(R.id.articlesRecyclerView)).perform(swipeUp()).check(matches(isDisplayed()))
        sleep(200)
        onView(withId(R.id.articlesRecyclerView)).perform(swipeDown()).check(matches(isDisplayed()))
    }
}
