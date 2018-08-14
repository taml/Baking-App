package com.tamlove.bakingapp;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.startsWith;

import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.Intents;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.tamlove.bakingapp.ui.RecipeActivity;
import com.tamlove.bakingapp.ui.RecipeContentActivity;
import com.tamlove.bakingapp.ui.RecipeFragment;
import com.tamlove.bakingapp.ui.RecipeStepsDetailActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class RecipeDetailActivityIntentTest {

    private static final int ITEM_POSITION = 0;

    @Rule
    public ActivityTestRule<RecipeActivity> mActivityRule = new ActivityTestRule<>(RecipeActivity.class);

    @Before
    public void setUpResources(){
        IdlingRegistry.getInstance().register(RecipeFragment.getIdlingResource());
        Intents.init();
    }

    @Test
    public void checkContentIntent(){
        onView(withId(R.id.recycler_view)).check(matches(isDisplayed()));
        onView(ViewMatchers.withId(R.id.recycler_view)).perform(RecyclerViewActions.scrollToPosition(ITEM_POSITION));
        String itemText = mActivityRule.getActivity().getResources().getString(R.string.testIntentRecipeText);
        onView(withText(itemText)).check(matches(isDisplayed()));
        onView(ViewMatchers.withId(R.id.recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(ITEM_POSITION, click()));
        intended(hasComponent(RecipeContentActivity.class.getName()));
    }

    @Test
    public void checkTabletAndMobileViews(){
        onView(withId(R.id.recycler_view)).check(matches(isDisplayed()));
        onView(ViewMatchers.withId(R.id.recycler_view)).perform(RecyclerViewActions.scrollToPosition(ITEM_POSITION));
        String itemText = mActivityRule.getActivity().getResources().getString(R.string.testIntentRecipeText);
        onView(withText(itemText)).check(matches(isDisplayed()));
        onView(ViewMatchers.withId(R.id.recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(ITEM_POSITION, click()));
        intended(hasComponent(RecipeContentActivity.class.getName()));

        boolean isTabletDevice = mActivityRule.getActivity().getResources().getBoolean(R.bool.isTabletDevice);
        if(isTabletDevice) {
            onView(withId(R.id.recipe_detail_step_video)).check(matches(isDisplayed()));
        } else {
            onView(withId(R.id.tabs)).check(matches(isDisplayed()));
            String tabText = mActivityRule.getActivity().getResources().getString(R.string.testIntentTabText);
            onView(withText(tabText)).perform(click());
            onView(withId(R.id.steps_recycler_view)).check(matches(isDisplayed()));
            onView(ViewMatchers.withId(R.id.steps_recycler_view)).perform(RecyclerViewActions.scrollToPosition(ITEM_POSITION));
            String recyclerItemText = mActivityRule.getActivity().getResources().getString(R.string.testIntentRecyclerText);
            onView(withText(recyclerItemText)).check(matches(isDisplayed()));
            onView(ViewMatchers.withId(R.id.steps_recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(ITEM_POSITION, click()));
            intended(hasComponent(RecipeStepsDetailActivity.class.getName()));
        }
    }

    @Test
    public void checkRecipeDetailAndVideo(){
        onView(withId(R.id.recycler_view)).check(matches(isDisplayed()));
        onView(ViewMatchers.withId(R.id.recycler_view)).perform(RecyclerViewActions.scrollToPosition(ITEM_POSITION));
        String itemText = mActivityRule.getActivity().getResources().getString(R.string.testIntentRecipeText);
        onView(withText(itemText)).check(matches(isDisplayed()));
        onView(ViewMatchers.withId(R.id.recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(ITEM_POSITION, click()));
        intended(hasComponent(RecipeContentActivity.class.getName()));

        boolean isTabletDevice = mActivityRule.getActivity().getResources().getBoolean(R.bool.isTabletDevice);
        if(isTabletDevice) {
            onView(withId(R.id.recipe_detail_step_video)).check(matches(isDisplayed()));
            onView(withId(R.id.next_step)).check(matches(isDisplayed()));
            onView(withId(R.id.next_step)).perform(click());
            onView(withId(R.id.recipe_detail_description)).check(matches(isDisplayed()));
            String descriptionText = mActivityRule.getActivity().getResources().getString(R.string.testIntentDescriptionText);
            onView(withId(R.id.recipe_detail_description)).check(matches(withText(startsWith(descriptionText))));
            onView(withId(R.id.next_step)).check(matches(isDisplayed()));
            onView(withId(R.id.next_step)).perform(click());
            onView(withId(R.id.exo_pause)).check(matches(isDisplayed()));
            onView(withId(R.id.exo_pause)).perform(click());
        } else {
            onView(withId(R.id.tabs)).check(matches(isDisplayed()));
            String tabText = mActivityRule.getActivity().getResources().getString(R.string.testIntentTabText);
            onView(withText(tabText)).perform(click());
            onView(withId(R.id.steps_recycler_view)).check(matches(isDisplayed()));
            onView(ViewMatchers.withId(R.id.steps_recycler_view)).perform(RecyclerViewActions.scrollToPosition(ITEM_POSITION));
            String recyclerItemText = mActivityRule.getActivity().getResources().getString(R.string.testIntentRecyclerText);
            onView(withText(recyclerItemText)).check(matches(isDisplayed()));
            onView(ViewMatchers.withId(R.id.steps_recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(ITEM_POSITION, click()));
            intended(hasComponent(RecipeStepsDetailActivity.class.getName()));
            onView(withId(R.id.prev_step)).check(matches(not(isDisplayed())));
            onView(withId(R.id.exo_prev)).check(matches(isDisplayed()));
            onView(withId(R.id.exo_prev)).perform(click());
        }
    }

    @After
    public void unregisterResources(){
        IdlingRegistry.getInstance().unregister(RecipeFragment.getIdlingResource());
        Intents.release();
    }
}
