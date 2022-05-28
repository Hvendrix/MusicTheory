import androidx.test.espresso.DataInteraction
import androidx.test.espresso.ViewInteraction
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent

import androidx.test.InstrumentationRegistry.getInstrumentation
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*

import com.example.musictheory.R

import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.IsInstanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.anything
import org.hamcrest.Matchers.`is`

@LargeTest
@RunWith(AndroidJUnit4::class)
class LoginInAccauntTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun loginInAccauntTest() {
        val bottomNavigationItemView = onView(
            allOf(withId(R.id.nested_personal_account), withContentDescription("�������"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.nav_view),
                        0),
                    3),
                isDisplayed()))
        bottomNavigationItemView.perform(click())

        val materialButton = onView(
            allOf(withId(R.id.enter_button), withText("����"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.login_body),
                        0),
                    4),
                isDisplayed()))
        materialButton.perform(click())

        val bottomNavigationItemView2 = onView(
            allOf(withId(R.id.nested_navigation_home), withContentDescription("����"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.nav_view),
                        0),
                    0),
                isDisplayed()))
        bottomNavigationItemView2.perform(click())
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int,
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
