package com.tamlove.bakingapp.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class RecipeWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new RecipeWidgetRemoteViewsFactory(this.getApplicationContext());
    }
}
