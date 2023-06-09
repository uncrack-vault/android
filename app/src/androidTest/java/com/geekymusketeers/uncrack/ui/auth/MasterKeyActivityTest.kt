package com.geekymusketeers.uncrack.ui.auth

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches

import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.geekymusketeers.uncrack.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class MasterKeyActivityTest{

    fun ViewInteraction.isGone() = getViewAssertion(Visibility.GONE)

    fun ViewInteraction.isVisible() = getViewAssertion(Visibility.VISIBLE)

    fun ViewInteraction.isInvisible() = getViewAssertion(Visibility.INVISIBLE)

    private fun getViewAssertion(visibility: Visibility): ViewAssertion? {
        return ViewAssertions.matches(withEffectiveVisibility(visibility))
    }


    @get:Rule
    val scenario = ActivityScenarioRule(MasterKeyActivity::class.java)

    @Test
    fun all_details_are_correct() {
        val masterKey = "123456"
        val confirmMasterKey = "123456"
        onView(withId(R.id.masterKey)).perform(ViewActions.typeText(masterKey),closeSoftKeyboard())
        onView(withId(R.id.confirm_masterKey)).perform(ViewActions.typeText(confirmMasterKey),closeSoftKeyboard())
        onView(withId(R.id.btnSaveMasterKey)).perform(click())
    }

    @Test
    fun confirm_master_key_incorrect() {
        val masterKey = "123456"
        val confirmMasterKey = "123450"
        onView(withId(R.id.masterKey)).perform(ViewActions.typeText(masterKey),closeSoftKeyboard())
        onView(withId(R.id.confirm_masterKey)).perform(ViewActions.typeText(confirmMasterKey),closeSoftKeyboard())
        onView(withId(R.id.btnSaveMasterKey)).perform(click())
        onView(withId(R.id.confirmMasterKeyHelperTV)).isVisible()
        onView(withId(R.id.confirmMasterKeyHelperTV)).check(matches(withText("Please enter the correct Master Key")))
    }

    @Test
    fun master_key_length_less_then_6() {
        val masterKey = "123"
        val confirmMasterKey = "123"
        onView(withId(R.id.masterKey)).perform(ViewActions.typeText(masterKey),closeSoftKeyboard())
        onView(withId(R.id.confirm_masterKey)).perform(ViewActions.typeText(confirmMasterKey),closeSoftKeyboard())
        onView(withId(R.id.btnSaveMasterKey)).perform(click())
        onView(withId(R.id.masterKeyHelperTV)).isVisible()
        onView(withId(R.id.masterKeyHelperTV)).check(matches(withText("Your master key should be at least 6 letters long")))
    }

//    @Test
//    fun master_key_isEmpty() {
//        val masterKey = ""
//        val confirmMasterKey = ""
//        onView(withId(R.id.masterKey)).perform(ViewActions.typeText(masterKey),closeSoftKeyboard())
//        onView(withId(R.id.confirm_masterKey)).perform(ViewActions.typeText(confirmMasterKey),closeSoftKeyboard())
//        onView(withId(R.id.btnSaveMasterKey)).perform(click())
//        onView(withId(R.id.masterKeyHelperTV)).isVisible()
//        onView(withId(R.id.masterKeyHelperTV)).check(matches(withText("Please set a Master Key")))
//    }
}