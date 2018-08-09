package com.redridgeapps.baking;

import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import com.jakewharton.espresso.OkHttp3IdlingResource;
import com.redridgeapps.baking.ui.main.MainActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItem;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class DetailActivityTest {

    private static final String NUTELLA_PIE = "Nutella Pie";
    private static final String RECIPE_INTRODUCTION = "Recipe Introduction";
    private static final String START_FILLING_PREP = "Start filling prep";
    private static final String FINISHING_STEPS = "Finishing Steps";

    @Rule
    public IntentsTestRule<MainActivity> intentsTestRule = new IntentsTestRule<>(MainActivity.class);

    private IdlingResource idlingResource;

    @Before
    public void registerIdlingResource() {
        idlingResource = OkHttp3IdlingResource.create("OkHttp", intentsTestRule.getActivity().getOkHttpClient());
        IdlingRegistry.getInstance().register(idlingResource);
    }

    @Test
    public void checkRecipeIntroductionDisplayed() {
        onView(withId(R.id.recycler_view))
                .perform(actionOnItem(hasDescendant(withText(NUTELLA_PIE)), click()));

        onView(withText("Recipe Introduction"))
                .check(matches(isDisplayed()));
    }

    @Test
    public void checkFinishingStepsDisplayed() {
        onView(withId(R.id.recycler_view))
                .perform(actionOnItem(hasDescendant(withText(NUTELLA_PIE)), click()));

        onView(withText(START_FILLING_PREP))
                .check(matches(isDisplayed()));
    }

    @Test
    public void checkExoPlayerDisplayed() {
        onView(withId(R.id.recycler_view))
                .perform(actionOnItemAtPosition(0, click()));

        onView(withId(R.id.recyclerView))
                .perform(actionOnItem(hasDescendant(withText(RECIPE_INTRODUCTION)), click()));

        onView(withId(R.id.exoplayer)).check(matches(isDisplayed()));
    }

    @Test
    public void checkPreviousButtonDisabled() {
        onView(withId(R.id.recycler_view))
                .perform(actionOnItemAtPosition(0, click()));

        onView(withId(R.id.recyclerView))
                .perform(actionOnItem(hasDescendant(withText(RECIPE_INTRODUCTION)), click()));

        onView(withId(R.id.previousStep)).check(matches(not(isDisplayed())));
    }

    @Test
    public void checkNextButtonDisabled() {
        onView(withId(R.id.recycler_view))
                .perform(actionOnItemAtPosition(0, click()));

        onView(withId(R.id.recyclerView))
                .perform(actionOnItem(hasDescendant(withText(FINISHING_STEPS)), click()));

        onView(withId(R.id.nextStep)).check(matches(not(isDisplayed())));
    }

    @Test
    public void checkNextAndPreviousButtonsDisplayed() {
        onView(withId(R.id.recycler_view))
                .perform(actionOnItemAtPosition(0, click()));

        onView(withId(R.id.recyclerView))
                .perform(actionOnItem(hasDescendant(withText(START_FILLING_PREP)), click()));

        onView(withId(R.id.previousStep)).check(matches(isDisplayed()));
        onView(withId(R.id.nextStep)).check(matches(isDisplayed()));
    }

    @After
    public void unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(idlingResource);
    }
}
