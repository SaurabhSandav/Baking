package com.redridgeapps.baking;

import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import com.jakewharton.espresso.OkHttp3IdlingResource;
import com.redridgeapps.baking.model.Recipe;
import com.redridgeapps.baking.ui.detail.DetailActivity;
import com.redridgeapps.baking.ui.main.MainActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTest {

    private static final String CHEESECAKE = "Cheesecake";
    private static final String EXTRA_RECIPE = "extra_recipe";

    @Rule
    public IntentsTestRule<MainActivity> intentsTestRule = new IntentsTestRule<>(MainActivity.class);

    private IdlingResource idlingResource;

    @Before
    public void registerIdlingResource() {
        idlingResource = OkHttp3IdlingResource.create("OkHttp", intentsTestRule.getActivity().getOkHttpClient());
        IdlingRegistry.getInstance().register(idlingResource);
    }

    @Test
    public void checkFourthElementIsCheeseCake() {
        onView(withId(R.id.recycler_view))
                .perform(actionOnItemAtPosition(3, click()));

        onView(withText(CHEESECAKE))
                .check(matches(isDisplayed()));
    }

    @Test
    public void checkSecondElementIntent() {
        onView(withId(R.id.recycler_view))
                .perform(actionOnItemAtPosition(1, click()));

        intended(
                allOf(
                        hasComponent(DetailActivity.class.getName()),
                        hasExtra(equalTo(EXTRA_RECIPE), instanceOf(Recipe.class))
                )
        );
    }

    @After
    public void unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(idlingResource);
    }
}
