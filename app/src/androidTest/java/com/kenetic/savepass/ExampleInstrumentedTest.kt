package com.kenetic.savepass

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
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
    fun savingLoginCredentials(){
        onView(withId(R.id.add_fab)).perform(click())
        onView(withId(R.id.set_new_password_edit_text)).perform(typeText("MasterPassword@1"))
        onView(withId(R.id.confirm_new_password_edit_text)).perform(typeText("MasterPassword@1"))
        onView(withId(R.id.save_fab)).perform(click())
        Thread.sleep(100)

        onView(withId(R.id.add_fab)).perform(click())
        onView(withId(R.id.service_name_edit_text)).perform(typeText("test_name_1"))
        onView(withId(R.id.service_password_edit_text)).perform(typeText("test_password_1"))
        onView(withId(R.id.internet_service_radio_button)).perform(click())
        onView(withId(R.id.save_button)).perform(click())

        onView(withId(R.id.add_fab)).perform(click())
        onView(withId(R.id.service_name_edit_text)).perform(typeText("test_name_2"))
        onView(withId(R.id.service_password_edit_text)).perform(typeText("test_password_2"))
        onView(withId(R.id.internet_service_radio_button)).perform(click())
        onView(withId(R.id.fingerprint_check_box)).perform(click())
        onView(withId(R.id.save_button)).perform(click())

        onView(withId(R.id.add_fab)).perform(click())
        onView(withId(R.id.service_name_edit_text)).perform(typeText("test_name_3"))
        onView(withId(R.id.service_password_edit_text)).perform(typeText("test_password_3"))
        onView(withId(R.id.application_service_radio_button)).perform(click())
        onView(withId(R.id.save_button)).perform(click())

        onView(withId(R.id.add_fab)).perform(click())
        onView(withId(R.id.service_name_edit_text)).perform(typeText("test_name_4"))
        onView(withId(R.id.service_password_edit_text)).perform(typeText("test_password_4"))
        onView(withId(R.id.application_service_radio_button)).perform(click())
        onView(withId(R.id.fingerprint_check_box)).perform(click())
        onView(withId(R.id.save_button)).perform(click())

        onView(withId(R.id.add_fab)).perform(click())
        onView(withId(R.id.service_name_edit_text)).perform(typeText("test_name_5"))
        onView(withId(R.id.service_password_edit_text)).perform(typeText("test_password_5"))
        onView(withId(R.id.internet_service_radio_button)).perform(click())
        onView(withId(R.id.save_button)).perform(click())

        onView(withId(R.id.add_fab)).perform(click())
        onView(withId(R.id.service_name_edit_text)).perform(typeText("test_name_6"))
        onView(withId(R.id.service_password_edit_text)).perform(typeText("test_password_6"))
        onView(withId(R.id.internet_service_radio_button)).perform(click())
        onView(withId(R.id.fingerprint_check_box)).perform(click())
        onView(withId(R.id.save_button)).perform(click())

        onView(withId(R.id.add_fab)).perform(click())
        onView(withId(R.id.service_name_edit_text)).perform(typeText("test_name_7"))
        onView(withId(R.id.service_password_edit_text)).perform(typeText("test_password_7"))
        onView(withId(R.id.application_service_radio_button)).perform(click())
        onView(withId(R.id.save_button)).perform(click())

        onView(withId(R.id.add_fab)).perform(click())
        onView(withId(R.id.service_name_edit_text)).perform(typeText("test_name_8"))
        onView(withId(R.id.service_password_edit_text)).perform(typeText("test_password_8"))
        onView(withId(R.id.application_service_radio_button)).perform(click())
        onView(withId(R.id.fingerprint_check_box)).perform(click())
        onView(withId(R.id.save_button)).perform(click())

        onView(withId(R.id.add_fab)).perform(click())
        onView(withId(R.id.service_name_edit_text)).perform(typeText("test_name_9"))
        onView(withId(R.id.service_password_edit_text)).perform(typeText("test_password_9"))
        onView(withId(R.id.internet_service_radio_button)).perform(click())
        onView(withId(R.id.save_button)).perform(click())

        onView(withId(R.id.add_fab)).perform(click())
        onView(withId(R.id.service_name_edit_text)).perform(typeText("test_name_10"))
        onView(withId(R.id.service_password_edit_text)).perform(typeText("test_password_10"))
        onView(withId(R.id.internet_service_radio_button)).perform(click())
        onView(withId(R.id.fingerprint_check_box)).perform(click())
        onView(withId(R.id.save_button)).perform(click())

        onView(withId(R.id.add_fab)).perform(click())
        onView(withId(R.id.service_name_edit_text)).perform(typeText("test_name_11"))
        onView(withId(R.id.service_password_edit_text)).perform(typeText("test_password_11"))
        onView(withId(R.id.application_service_radio_button)).perform(click())
        onView(withId(R.id.save_button)).perform(click())

        onView(withId(R.id.add_fab)).perform(click())
        onView(withId(R.id.service_name_edit_text)).perform(typeText("test_name_12"))
        onView(withId(R.id.service_password_edit_text)).perform(typeText("test_password_12"))
        onView(withId(R.id.application_service_radio_button)).perform(click())
        onView(withId(R.id.fingerprint_check_box)).perform(click())
        onView(withId(R.id.save_button)).perform(click())
    }
}