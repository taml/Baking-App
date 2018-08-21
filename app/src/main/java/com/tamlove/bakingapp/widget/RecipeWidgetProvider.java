package com.tamlove.bakingapp.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.widget.RemoteViews;

import com.google.gson.Gson;
import com.tamlove.bakingapp.R;
import com.tamlove.bakingapp.models.Recipe;
import com.tamlove.bakingapp.ui.RecipeActivity;
import com.tamlove.bakingapp.ui.RecipeContentActivity;

import static com.tamlove.bakingapp.widget.RecipeUtils.RECIPE_PARCELABLE_KEY;
import static com.tamlove.bakingapp.widget.RecipeUtils.RECIPE_STRING_WIDGET_KEY;
import static com.tamlove.bakingapp.widget.RecipeUtils.RECIPE_PREF;
import static com.tamlove.bakingapp.widget.RecipeUtils.RECIPE_WIDGET_PREF;

public class RecipeWidgetProvider extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for(int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId){
        SharedPreferences sharedPreferences = context.getSharedPreferences(RECIPE_PREF, Context.MODE_PRIVATE);
        if(sharedPreferences.contains(RECIPE_WIDGET_PREF)){
            String recipeAsGson = sharedPreferences.getString(RECIPE_WIDGET_PREF, null);
            Gson gson = new Gson();
            Recipe recipe = gson.fromJson(recipeAsGson, Recipe.class);
            String recipeName = recipe.getName();

            Intent widgetServiceIntent = new Intent(context, RecipeWidgetService.class);
            widgetServiceIntent.putExtra(RECIPE_STRING_WIDGET_KEY, recipeAsGson);
            widgetServiceIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            widgetServiceIntent.setData(Uri.parse(widgetServiceIntent.toUri(Intent.URI_INTENT_SCHEME)));

            RemoteViews remoteView = new RemoteViews(context.getPackageName(), R.layout.widget_recipe);
            remoteView.setTextViewText(R.id.widget_textview, recipeName);
            remoteView.setRemoteAdapter(R.id.widget_listview, widgetServiceIntent);
            Bundle bundle = new Bundle();
            bundle.putParcelable(RECIPE_PARCELABLE_KEY, recipe);
            Intent activityToOpenIntent = new Intent(context, RecipeContentActivity.class);
            activityToOpenIntent.putExtras(bundle);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, activityToOpenIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            remoteView.setOnClickPendingIntent(R.id.widget_button, pendingIntent);
            remoteView.setEmptyView(R.id.widget_listview, R.id.widget_empty_view);

            appWidgetManager.updateAppWidget(appWidgetId, remoteView);
        }
    }

}
