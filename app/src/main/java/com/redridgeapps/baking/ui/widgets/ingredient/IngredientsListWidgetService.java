package com.redridgeapps.baking.ui.widgets.ingredient;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViewsService;

import com.redridgeapps.baking.repo.PreferenceRepository;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class IngredientsListWidgetService extends RemoteViewsService {

    @Inject
    PreferenceRepository prefRepo;

    public static Intent createIntent(Context context, int appWidgetId) {
        Intent intent = new Intent(context, IngredientsListWidgetService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
        return intent;
    }

    @Override
    public void onCreate() {
        AndroidInjection.inject(this);
        super.onCreate();
    }

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new IngredientsViewsFactory(getPackageName(), intent, prefRepo);
    }
}