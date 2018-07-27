package com.tamlove.bakingapp.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.tamlove.bakingapp.R;
import com.tamlove.bakingapp.models.Steps;

import java.util.ArrayList;

public class RecipeStepsDetailActivity extends AppCompatActivity {

    private static final String STEPS_PARCELABLE_KEY = "steps_parcelable";
    private static final String STEPS_ID_PARCELABLE_KEY = "steps_id_parcelable";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_steps_detail);

        if(getIntent() != null){
            Intent RecipeStepsListIntent = getIntent();
            Bundle bundle = RecipeStepsListIntent.getExtras();
            if(bundle != null){
                ArrayList<Steps> steps = bundle.getParcelableArrayList(STEPS_PARCELABLE_KEY);
                int stepsId = bundle.getInt(STEPS_ID_PARCELABLE_KEY);
                Log.v("RecipeStepsDetailAct", "Steps: " + steps + " ***** " + stepsId );
            }
        }
    }
}
