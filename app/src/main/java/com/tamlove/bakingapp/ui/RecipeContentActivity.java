package com.tamlove.bakingapp.ui;

import android.content.Intent;
import android.os.Parcelable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.tamlove.bakingapp.R;
import com.tamlove.bakingapp.adapters.ViewPagerAdapter;
import com.tamlove.bakingapp.models.Recipe;
import com.tamlove.bakingapp.models.Steps;

import java.util.ArrayList;
import java.util.List;


public class RecipeContentActivity extends AppCompatActivity implements RecipeStepsListFragment.OnStepClickListener {

    private static final String RECIPE_PARCELABLE_KEY = "recipe_parcelable";
    private static final String STEPS_PARCELABLE_KEY = "steps_parcelable";
    private static final String STEPS_ID_PARCELABLE_KEY = "steps_id_parcelable";

    public static Recipe sRecipe;
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_content);

        if(findViewById(R.id.large_layout) != null){
            mTwoPane = true;
        } else {
            mTwoPane = false;
        }

        ViewPager viewPager = findViewById(R.id.pager);
        TabLayout tabLayout = findViewById(R.id.tabs);

        Intent recipeActivity = getIntent();
        if(recipeActivity != null){
            Bundle bundle = recipeActivity.getExtras();
            if(bundle != null){
                sRecipe = bundle.getParcelable(RECIPE_PARCELABLE_KEY);
            }
        }

        if(mTwoPane){
            FragmentManager fragmentManager = getSupportFragmentManager();
            RecipeStepsListFragment recipeStepsListFragment = RecipeStepsListFragment.newInstance(sRecipe);
            fragmentManager.beginTransaction()
                    .add(R.id.recipe_steps_list_container, recipeStepsListFragment)
                    .commit();
            RecipeIngredientsListFragment recipeIngredientsListFragment = RecipeIngredientsListFragment.newInstance(sRecipe);
            fragmentManager.beginTransaction()
                    .add(R.id.recipe_ingredients_container, recipeIngredientsListFragment)
                    .commit();
        } else {
            ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), sRecipe);
            viewPagerAdapter.addFrag(new RecipeIngredientsListFragment(), "Ingredients");
            viewPagerAdapter.addFrag(new RecipeStepsListFragment(), "Steps");
            viewPager.setAdapter(viewPagerAdapter);
            tabLayout.setupWithViewPager(viewPager);
        }
    }

    @Override
    public void onStepSelected(int stepId, List<Steps> steps) {
        if(mTwoPane){

        } else {
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList(STEPS_PARCELABLE_KEY, (ArrayList<? extends Parcelable>) steps);
            bundle.putInt(STEPS_ID_PARCELABLE_KEY, stepId);
            Intent recipeStepsDetailIntent = new Intent(RecipeContentActivity.this, RecipeStepsDetailActivity.class);
            recipeStepsDetailIntent.putExtras(bundle);
            startActivity(recipeStepsDetailIntent);
        }
    }
}
