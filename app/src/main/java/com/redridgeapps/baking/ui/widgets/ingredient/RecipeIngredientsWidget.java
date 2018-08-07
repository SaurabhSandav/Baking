package com.redridgeapps.baking.ui.widgets.ingredient;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.redridgeapps.baking.R;
import com.redridgeapps.baking.model.Recipe;
import com.redridgeapps.baking.repo.PreferenceRepository;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class RecipeIngredientsWidget extends AppWidgetProvider {

    @Inject
    PreferenceRepository prefRepo;

    public static void updateAppWidget(
            String packageName,
            AppWidgetManager appWidgetManager,
            int appWidgetId,
            Recipe recipe
    ) {
        RemoteViews views = new RemoteViews(packageName, R.layout.recipe_ingredients_widget);
        views.setTextViewText(R.id.appwidget_text, recipe.getName());

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        AndroidInjection.inject(this, context);
        super.onReceive(context, intent);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        for (int appWidgetId : appWidgetIds) {
            Recipe recipe = prefRepo.getIngredientsWidgetRecipe(appWidgetId);

            if (recipe == null) continue;

            updateAppWidget(
                    context.getPackageName(),
                    appWidgetManager,
                    appWidgetId,
                    recipe
            );
        }
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds)
            prefRepo.deleteIngredientsWidgetRecipe(appWidgetId);
    }
}

