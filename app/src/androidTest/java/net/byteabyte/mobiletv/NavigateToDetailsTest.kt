package net.byteabyte.mobiletv

import androidx.lifecycle.Lifecycle
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import net.byteabyte.mobiletv.showdetails.ShowDetailsActivity
import net.byteabyte.mobiletv.toprated.TopRatedActivity
import net.byteabyte.mobiletv.toprated.TopRatedShowViewHolder
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NavigateToDetailsTest {
    @get:Rule
    var activityScenarioRule = activityScenarioRule<TopRatedActivity>()
    @get:Rule
    val intentsTestRule = IntentsTestRule(TopRatedActivity::class.java)

    @Test
    fun tappingAShowTakesUsToTheDetailsForTheShow() {
        val scenario = activityScenarioRule.scenario
        scenario.moveToState(Lifecycle.State.RESUMED)

        onView(withId(R.id.topRatedShowsRecyclerView)).perform(
            actionOnItemAtPosition<TopRatedShowViewHolder>(0, click())
        )

        waitForActivityIntent(ShowDetailsActivity::class.java.name)
    }
}