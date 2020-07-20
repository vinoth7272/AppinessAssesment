package com.example.appinesstest

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Root
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.example.appinesstest.view.ImageListActivity
import org.hamcrest.Matcher
import org.junit.Rule
import org.junit.Test


@LargeTest
class ImageRecyclerViewTest {

    private val LAST_ITEM_ID: String = "item: 10"

    @get:Rule
    val activityScenario: ActivityScenarioRule<ImageListActivity> =
        ActivityScenarioRule(ImageListActivity::class.java)

    @get:Rule
    val activityRule = ActivityTestRule(ImageListActivity::class.java)

    @Test
    fun scrollToPosition_checkText() {
        getCount()?.let {
            onView(withId(R.id.recycler_view)).perform(scrollToPosition<RecyclerView.ViewHolder>(1));
        }
    }

    @Test
    fun lastItem_NotDisplayed() {
        onView(withText(LAST_ITEM_ID)).check(doesNotExist())
    }

    @Test
    fun row_Click() {
        // Click on one of the rows.
        onView(withId(R.id.recycler_view)).perform(
            actionOnItemAtPosition<RecyclerView.ViewHolder>(
                1,
                click()
            )
        )

        // Check that the activity detected the click on the first column.
            onView(withText("1")).inRoot(ToastMatcher()).check(matches(isDisplayed()))
    }

    @Test
    fun test_searchError() {
        onView(withId(R.id.action_search)).perform(click())
        onView(withId(R.id.search_src_text)).perform(typeText(""), pressImeActionButton())
        val errorString = activityRule.activity.getString(R.string.search_error)
        onView(withText(errorString)).inRoot(isToast()).check(matches(isDisplayed()))
    }

    @Test
    fun test_SearchSuccess() {
        onView(withId(R.id.action_search)).perform(click())
        onView(withId(R.id.search_src_text)).perform(typeText("Cricket"), pressImeActionButton())
        scrollToPosition_checkText()
    }

    fun getCount(): Int? {
        val recyclerView: RecyclerView =
            activityRule.activity.findViewById(R.id.recycler_view) as RecyclerView
        return recyclerView.adapter?.itemCount
    }
}

fun isToast(): Matcher<Root?>? {
    return ToastMatcher()
}