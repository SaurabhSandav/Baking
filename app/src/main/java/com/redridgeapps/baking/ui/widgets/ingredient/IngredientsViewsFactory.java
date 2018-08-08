package com.redridgeapps.baking.ui.widgets.ingredient;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.redridgeapps.baking.R;
import com.redridgeapps.baking.model.Ingredient;
import com.redridgeapps.baking.repo.PreferenceRepository;

import java.util.List;

public class IngredientsViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private String packageName;
    private List<Ingredient> ingredientList;

    IngredientsViewsFactory(String packageName, Intent intent, PreferenceRepository prefRepo) {
        this.packageName = packageName;

        int appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);

        ingredientList = prefRepo.getIngredientsWidgetRecipe(appWidgetId).getIngredients();
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return ingredientList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public RemoteViews getViewAt(int position) {
        Ingredient ingredient = ingredientList.get(position);

        final RemoteViews remoteView = new RemoteViews(packageName, R.layout.recipe_ingredients_row);
        remoteView.setTextViewText(R.id.ingredient, ingredient.getIngredient());

        String quantity = ingredient.getQuantity() + " " + ingredient.getMeasure();
        remoteView.setTextViewText(R.id.quantity, quantity);

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
}