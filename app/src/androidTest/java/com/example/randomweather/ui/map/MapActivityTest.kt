package com.example.randomweather.ui.map

import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withClassName
import androidx.test.espresso.matcher.ViewMatchers.withContentDescription
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.filters.LargeTest
import androidx.test.rule.GrantPermissionRule
import androidx.test.runner.AndroidJUnit4
import com.example.randomweather.R
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class MapActivityTest {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    var activityRule: ActivityScenarioRule<MapActivity> = ActivityScenarioRule(MapActivity::class.java)

    @Rule
    @JvmField
    var mGrantPermissionRule = GrantPermissionRule.grant("android.permission.ACCESS_FINE_LOCATION")

    @Test
    fun mapActivityTest() {
        val floatingActionButton = onView(allOf(withId(R.id.fab_refresh),
                                                withContentDescription("fab"),
                                                childAtPosition(childAtPosition(withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                                                                                0
                                                ), 0
                                                ),
                                                isDisplayed()
        )
        )
        floatingActionButton.perform(click())

        val floatingActionButton2 = onView(allOf(withId(R.id.fab_refresh),
                                                 withContentDescription("fab"),
                                                 childAtPosition(childAtPosition(withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                                                                                 0
                                                 ), 0
                                                 ),
                                                 isDisplayed()
        )
        )
        floatingActionButton2.perform(click())

        val floatingActionButton3 = onView(allOf(withId(R.id.fab_refresh),
                                                 withContentDescription("fab"),
                                                 childAtPosition(childAtPosition(withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                                                                                 0
                                                 ), 0
                                                 ),
                                                 isDisplayed()
        )
        )
        floatingActionButton3.perform(click())

        val floatingActionButton4 = onView(allOf(withId(R.id.fab_refresh),
                                                 withContentDescription("fab"),
                                                 childAtPosition(childAtPosition(withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                                                                                 0
                                                 ), 0
                                                 ),
                                                 isDisplayed()
        )
        )
        floatingActionButton4.perform(click())

        val floatingActionButton5 = onView(allOf(withId(R.id.fab_refresh),
                                                 withContentDescription("fab"),
                                                 childAtPosition(childAtPosition(withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                                                                                 0
                                                 ), 0
                                                 ),
                                                 isDisplayed()
        )
        )
        floatingActionButton5.perform(click())
    }

    private fun childAtPosition(
            parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent) && view == parent.getChildAt(position)
            }
        }
    }
}
