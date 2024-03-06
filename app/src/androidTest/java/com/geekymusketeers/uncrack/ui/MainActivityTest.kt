package com.geekymusketeers.uncrack.ui

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.geekymusketeers.uncrack.MainActivity
import com.geekymusketeers.uncrack.R
import org.junit.Rule
import org.junit.Test


class MainActivityTest{

    fun ViewInteraction.isGone() = getViewAssertion(ViewMatchers.Visibility.GONE)

    fun ViewInteraction.isVisible() = getViewAssertion(ViewMatchers.Visibility.VISIBLE)

    fun ViewInteraction.isInvisible() = getViewAssertion(ViewMatchers.Visibility.INVISIBLE)

    private fun getViewAssertion(visibility: ViewMatchers.Visibility): ViewAssertion? {
        return ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(visibility))
    }

    @get:Rule
    val scenario = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun addAccount() {
        onView(withId(R.id.fab)).perform(click())
//        val selectAccount = "Instagram"
        val email = "aritrarick2002@gmail.com"
        val username = "aritra_das"
        val password = "aritra"
        val note = "2nd account"
//        val category = "Social"

//        onView(withId(R.id.acc_type)).perform(click())
//
//        // Assuming the dropdown menu is displayed as an AutoCompleteTextView, select an item
//        onData(Matchers.`is`("First Item"))
//            .inAdapterView(withId(R.id.acc_type))
//            .perform(click())
//
//        // For example, to assert that the first item was selected
//        onView(withId(R.id.acc_type))
//            .check(matches(withText("First Item")))


        onView(withId(R.id.account_logo)).isVisible()
        onView(withId(R.id.remainingLayout)).isVisible()
        onView(withId(R.id.email)).perform(typeText(email),closeSoftKeyboard())
        onView(withId(R.id.username)).perform(typeText(username),closeSoftKeyboard())
        onView(withId(R.id.password)).perform(typeText(password),closeSoftKeyboard())
        onView(withId(R.id.note)).perform(typeText(note),closeSoftKeyboard())
//        onView(withId(R.id.btnSave)).perform(click())
        onView(withText(email)).check(matches(isDisplayed()))
    }

    @Test
    fun email_is_empty() {
        onView(withId(R.id.email)).perform(typeText(""))
        onView(withId(R.id.password)).perform(typeText("aritra"))
        onView(withId(R.id.btnSave)).perform(click())
        onView(withId(R.id.emailHelperTV)).isVisible()
        onView(withId(R.id.emailHelperTV)).check(matches(withText("Please Enter the Email.")))
    }

    @Test
    fun password_is_empty() {
        onView(withId(R.id.email)).perform(typeText("aritrarick2002@gmail.com"))
        onView(withId(R.id.password)).perform(typeText(""))
        onView(withId(R.id.btnSave)).perform(click())
        onView(withId(R.id.passwordHelperTV)).isVisible()
        onView(withId(R.id.passwordHelperTV)).check(matches(withText("Please Enter the Password.")))
    }

    @Test
    fun email_is_invalid() {
        onView(withId(R.id.email)).perform(typeText("aritrarick2002"))
        onView(withId(R.id.password)).perform(typeText("aritra"))
        onView(withId(R.id.btnSave)).perform(click())
        onView(withId(R.id.emailHelperTV)).isVisible()
        onView(withId(R.id.emailHelperTV)).check(matches(withText("Please check your Email Id")))
    }

}