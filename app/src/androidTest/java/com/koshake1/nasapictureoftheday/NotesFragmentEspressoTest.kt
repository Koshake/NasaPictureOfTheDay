package com.koshake1.nasapictureoftheday

import android.view.View
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import com.koshake1.nasapictureoftheday.di.injectDependencies
import com.koshake1.nasapictureoftheday.ui.notes.*
import com.koshake1.nasapictureoftheday.ui.notes.adapter.NotesAdapter
import org.hamcrest.Matcher
import org.junit.After
import org.junit.Before
import org.junit.Test

class NotesFragmentEspressoTest{

    private lateinit var scenario: FragmentScenario<NotesFragment>

    @Before
    fun setUp() {
        injectDependencies()
        scenario = launchFragmentInContainer(themeResId = R.style.AppTheme_AppThemeYellow)

    }

    @Test
    fun textFirstNote_Shows_Message() {
        val assertion = ViewAssertions.matches(withText("Add your first note here!"))
        onView(withId(R.id.textFirstNote)).check(assertion)
    }

    @Test
    fun textFirstNote_isVisible() {
        onView(withId(R.id.textFirstNote)).check(ViewAssertions
            .matches(withEffectiveVisibility(Visibility.GONE)))
    }

    @Test
    fun mainRecycler_scrollTo_test() {
        onView(withId(R.id.mainRecycler))
            .perform(
                RecyclerViewActions.scrollTo<NotesAdapter.NoteViewHolder>(
                    hasDescendant(withText("Note 8"))
                )
            )
    }

    private fun tapOnItemWithId(id: Int) = object : ViewAction {
        override fun getConstraints(): Matcher<View>? {
            return null
        }

        override fun getDescription(): String {
            return "Нажимаем на view с указанным id"
        }

        override fun perform(uiController: UiController, view: View) {
            val v = view.findViewById(id) as View
            v.performClick()

        }
    }

    @Test
    fun mainRecycler_itemClick() {
        onView(withId(R.id.mainRecycler))
            .perform(
                RecyclerViewActions
                    .actionOnItemAtPosition<NotesAdapter.NoteViewHolder>(
                        0,
                        tapOnItemWithId(R.id.title)
                    )
            )
    }

    @Test
    fun mainRecycler_itemSwipe() {

        onView(withId(R.id.mainRecycler))
            .perform(
                RecyclerViewActions.actionOnItem<NotesAdapter.NoteViewHolder>(
                    hasDescendant(withText("Note 1")),
                    swipeRight()
                )
            )

        onView(withId(R.id.mainRecycler))
            .perform(
                RecyclerViewActions
                    .actionOnItemAtPosition<NotesAdapter.NoteViewHolder>(
                        0,
                        tapOnItemWithId(R.id.title)
                    )
            ).check(matches(hasDescendant(withText("Note 2"))))
    }

    @After
    fun after() {

    }
}