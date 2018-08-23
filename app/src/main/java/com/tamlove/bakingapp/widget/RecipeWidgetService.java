package com.tamlove.bakingapp.widget;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.google.gson.Gson;
import com.tamlove.bakingapp.R;
import com.tamlove.bakingapp.models.Ingredients;
import com.tamlove.bakingapp.models.Recipe;

import java.util.List;

import static com.tamlove.bakingapp.widget.RecipeUtils.RECIPE_STRING_WIDGET_KEY;

public class RecipeWidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new RecipeWidgetRemoteViewsFactory(this.getApplicationContext(), intent);
    }
}

class RecipeWidgetRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context mContext;
    private List<Ingredients> mIngredients;
    private Intent mWidgetIntent;

    public RecipeWidgetRemoteViewsFactory(Context context, Intent intent) {
        mContext = context;
        mWidgetIntent = intent;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        String recipeAsGson = "";
        if(mWidgetIntent != null) {
            recipeAsGson = mWidgetIntent.getStringExtra(RECIPE_STRING_WIDGET_KEY);
            Gson gson = new Gson();
            Recipe mRecipe = gson.fromJson(recipeAsGson, Recipe.class);
            mIngredients = mRecipe.getIngredients();
        }
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        if(mIngredients == null) return 0;
        return mIngredients.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        Ingredients ingredients = mIngredients.get(position);
        String quantity = ingredients.getQuantity();
        String measure = ingredients.getMeasure();
        String ingredient = ingredients.getIngredient();
        RemoteViews remoteView = new RemoteViews(mContext.getPackageName(), R.layout.widget_recipe_list);
        remoteView.setTextViewText(R.id.widget_recipe_quantity, quantity);
        remoteView.setTextViewText(R.id.widget_recipe_measure, measure);
        remoteView.setTextViewText(R.id.widget_recipe_ingredient, ingredient);
        return remoteView;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
