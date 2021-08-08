package com.koshake1.nasapictureoftheday

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.koshake1.nasapictureoftheday.ui.picture.PictureOfTheDayFragment
import junit.framework.TestCase
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PODEspressoTest {

    private lateinit var scenario : FragmentScenario<PictureOfTheDayFragment>

    @Before
    fun setUp() {
        scenario = launchFragmentInContainer(themeResId = R.style.AppTheme_AppThemeYellow)

        scenario.moveToState(Lifecycle.State.STARTED)

    }

    @Test
    fun fragment_AssertNotNull() {
        scenario.onFragment {
            TestCase.assertNotNull(it)
        }
    }

    @Test
    fun fragment_bottomIsVisible_Test() {
        onView(withId(R.id.bottom_sheet_description_info)).check(ViewAssertions
            .matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
    }

    @Test
    fun fragment_bottomSheetBehavior_Test() {
        onView(withId(R.id.bottom_sheet_description_info)).perform(swipeDown())
        onView(withId(R.id.bottom_sheet_description_info)).check(ViewAssertions
            .matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.GONE)))
    }

    @Test
    fun fragment_inputEditText_isDisplayed_Test() {
        onView(withId(R.id.wiki_button)).perform(click())
        onView(withId(R.id.input_edit_text))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun fragment_wikiButton_start_visibility_Test() {
        onView(withId(R.id.wiki_button)).check(ViewAssertions
            .matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
    }

    @Test
    fun fragment_wikiButton_end_visibility_Test() {
        onView(withId(R.id.wiki_button)).perform(click())
        onView(withId(R.id.wiki_button)).check(ViewAssertions
            .matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.GONE)))
    }

    @After
    fun after() {
    }
}