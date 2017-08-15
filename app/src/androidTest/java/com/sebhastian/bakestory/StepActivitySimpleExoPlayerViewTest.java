package com.sebhastian.bakestory;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.allOf;

/**
 * Created by Yonathan Sebhastian on 8/16/2017.
 */

@RunWith(AndroidJUnit4.class)
public class StepActivitySimpleExoPlayerViewTest {
    private IdlingResource mIdlingResource;
    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void registerIdlingResource() {
        mIdlingResource = mActivityTestRule.getActivity().getIdlingResource();
        Espresso.registerIdlingResources(mIdlingResource);
    }

    @Test
    public void simpleExoPlayerViewVisibleTest() {
        onView(withId(R.id.recipe_recycler))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.recipe_detail_ingredient_heading)).check(matches(withText("Nutella Pie")));
        onView(withId(R.id.recipe_detail_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(
                allOf(
                        withId(R.id.recipe_step_video),
                        withParent(withParent(withId(R.id.recipe_step_layout))),
                        isDisplayed()))
                .check(matches(isDisplayed()));

    }

    @Test
    public void simpleExoPlayerViewNotVisibleTest() {
        onView(withId(R.id.recipe_recycler))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.recipe_detail_ingredient_heading)).check(matches(withText("Nutella Pie")));
        onView(withId(R.id.recipe_detail_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));

        onView(
                allOf(
                        withId(R.id.recipe_step_video),
                        withParent(withParent(withId(R.id.recipe_step_layout))),
                        isDisplayed()))
                .check(doesNotExist());

    }

    @After
    public void unregisterIdlingResource() {
        if (mIdlingResource != null) {
            Espresso.unregisterIdlingResources(mIdlingResource);
        }
    }

}
