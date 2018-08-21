package com.tamlove.bakingapp.widget;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViewsService;

import com.tamlove.bakingapp.models.Recipe;

import static com.tamlove.bakingapp.widget.RecipeUtils.RECIPE_STRING_WIDGET_KEY;

public class RecipeWidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new RecipeWidgetRemoteViewsFactory(this.getApplicationContext(), intent);
    }
}
