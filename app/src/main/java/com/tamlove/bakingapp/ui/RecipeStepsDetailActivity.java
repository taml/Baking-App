package com.tamlove.bakingapp.ui;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.tamlove.bakingapp.R;
import com.tamlove.bakingapp.models.Steps;

import java.util.ArrayList;

public class RecipeStepsDetailActivity extends AppCompatActivity {

    private static final String STEPS_PARCELABLE_KEY = "steps_parcelable";
    private static final String STEPS_ID_PARCELABLE_KEY = "steps_id_parcelable";
    private static final String RECIPE_NAME_KEY = "recipe";

    private int stepsId;
    private ArrayList<Steps> steps;
    private String recipeName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_steps_detail);

        if(getIntent() != null){
            Intent RecipeStepsListIntent = getIntent();
            Bundle bundle = RecipeStepsListIntent.getExtras();
            if(bundle != null){
                steps = bundle.getParcelableArrayList(STEPS_PARCELABLE_KEY);
                stepsId = bundle.getInt(STEPS_ID_PARCELABLE_KEY);
                recipeName = bundle.getString(RECIPE_NAME_KEY);
                if(getSupportActionBar() != null){
                    if(!TextUtils.isEmpty(recipeName) || recipeName != null){
                        getSupportActionBar().setTitle(recipeName);
                    }
                }
                if(savedInstanceState == null){
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    RecipeStepsDetailFragment recipeStepsDetailFragment = RecipeStepsDetailFragment.newInstance(steps, stepsId);
                    fragmentManager.beginTransaction()
                            .add(R.id.recipe_detail_container, recipeStepsDetailFragment)
                            .commit();
                }
            }
        }
    }
}
