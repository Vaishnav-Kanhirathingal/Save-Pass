package com.kenetic.savepass

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.matcher.ViewMatchers.withHint
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Rule

@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @get:Rule
    val scenario = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun shortcut() {
        onView(withId(R.id.add_fab)).perform(click())
        onView(withHint(R.string.set_new_password)).perform(typeText("MasterPassword@1"))
        onView(withId(R.string.confirm_new_password)).perform(typeText("MasterPassword@1"))
        onView(withId(R.id.save_fab)).perform(click())
        Thread.sleep(100)

        for (i in 1..12) {
            onView(withId(R.id.add_fab)).perform(click())
            onView(withHint(R.string.service_name)).perform(typeText("service_name_${i}"))
            onView(withId(R.string.service_password)).perform(typeText("service_password_${i}"))

            if (i % 4 == 1 || i % 4 == 2) {
                onView(withId(R.id.application_service_radio_button)).perform(click())
            } else {
                onView(withId(R.id.internet_service_radio_button)).perform(click())
            }

            if (i % 4 == 2 || i % 4 == 0) {
                onView(withId(R.id.fingerprint_check_box)).perform(click())
            }

            onView(withId(R.id.save_button)).perform(click())
        }
    }
}