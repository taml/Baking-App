package com.tamlove.bakingapp;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;


import com.tamlove.bakingapp.ui.RecipeActivity;
import com.tamlove.bakingapp.ui.RecipeFragment;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class RecipeIdlingResourceRecyclerViewTest {

    private static final int ITEM_POSITION = 3;

    @Rule
    public ActivityTestRule<RecipeActivity> mActivityRule = new ActivityTestRule<>(RecipeActivity.class);

    @Before
    public void registerIdlingResource(){
        IdlingRegistry.getInstance().register(RecipeFragment.getIdlingResource());
    }

    @Test
    public void checkRecyclerView(){
        onView(withId(R.id.recycler_view)).check(matches(isDisplayed()));
    }

    @Test
    public void checkTextExistsAndClick(){
        onView(ViewMatchers.withId(R.id.recycler_view)).perform(RecyclerViewActions.scrollToPosition(ITEM_POSITION));
        String itemText = mActivityRule.getActivity().getResources().getString(R.string.testRecipeText);
        onView(withText(itemText)).check(matches(isDisplayed()));
        onView(ViewMatchers.withId(R.id.recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(ITEM_POSITION, click()));
    }

    @After
    public void unregisterIdlingResource(){
        IdlingRegistry.getInstance().unregister(RecipeFragment.getIdlingResource());
    }

}
