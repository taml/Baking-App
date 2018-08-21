package com.tamlove.bakingapp.widget;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.google.gson.Gson;
import com.tamlove.bakingapp.R;
import com.tamlove.bakingapp.models.Ingredients;
import com.tamlove.bakingapp.models.Recipe;

import java.util.List;

import static com.tamlove.bakingapp.widget.RecipeUtils.RECIPE_STRING_WIDGET_KEY;

public class RecipeWidgetRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context mContext;
    private List<Ingredients> mIngredients;
    private Recipe mRecipe;
    private Intent mWidgetIntent;

    public RecipeWidgetRemoteViewsFactory(Context context, Intent intent) {
        mContext = context;
        mWidgetIntent = intent;
    }

    @Override
    public void onCreate() {
        String recipeAsGson = "";
        if(mWidgetIntent != null) {
            recipeAsGson = mWidgetIntent.getStringExtra(RECIPE_STRING_WIDGET_KEY);
            Gson gson = new Gson();
            mRecipe = gson.fromJson(recipeAsGson, Recipe.class);
            mIngredients = mRecipe.getIngredients();
        }
    }

    @Override
    public void onDataSetChanged() {

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
        String ingredient = ingredients.getIngredient();
        RemoteViews remoteView = new RemoteViews(mContext.getPackageName(), R.layout.widget_recipe_list);
        remoteView.setTextViewText(R.id.widget_list_text, ingredient);
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
