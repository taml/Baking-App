package com.tamlove.bakingapp.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.tamlove.bakingapp.R;
import com.tamlove.bakingapp.ui.RecipeActivity;

public class RecipeWidgetProvider extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for(int appWidgetId : appWidgetIds) {
            Intent recipeIntent = new Intent(context, RecipeActivity.class);
            PendingIntent recipePendingIntent = PendingIntent.getActivity(context, 0, recipeIntent, 0);
            RemoteViews remoteView = new RemoteViews(context.getPackageName(), R.layout.widget_recipe);
            remoteView.setTextViewText(R.id.widget_textview, "Testing Widget");

            appWidgetManager.updateAppWidget(appWidgetId, remoteView);
        }
    }
}
