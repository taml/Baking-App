package com.tamlove.bakingapp;

import android.os.Handler;
import android.support.annotation.Nullable;

import com.tamlove.bakingapp.models.Recipe;
import com.tamlove.bakingapp.UITests.SimpleIdlingResource;

import java.util.List;


class RecipeDelayer {

    private static final int DELAY_MILLIS = 3000;

    interface DelayerCallback {
        void onDone(List<Recipe> recipeList);
    }

    static void processRecipes(final List<Recipe> recipeList, final DelayerCallback delayerCallback, @Nullable final SimpleIdlingResource idlingResource){
        if(idlingResource != null){
            idlingResource.setIdleState(false);
        }
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(delayerCallback != null){
                    delayerCallback.onDone(recipeList);
                    if(idlingResource != null){
                        idlingResource.setIdleState(true);
                    }
                }
            }
        }, DELAY_MILLIS);
    }
}
