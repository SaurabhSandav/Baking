package com.redridgeapps.baking.repo;

import android.content.SharedPreferences;

import com.redridgeapps.baking.model.Recipe;
import com.squareup.moshi.Moshi;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PreferenceRepository {

    private static final String WIDGET_PREFIX = "appwidget_";

    private SharedPreferences prefs;
    private Moshi moshi;

    @Inject
    PreferenceRepository(SharedPreferences prefs, Moshi moshi) {
        this.prefs = prefs;
        this.moshi = moshi;
    }

    public void saveIngredientsWidgetRecipe(int appWidgetId, Recipe recipe) {
        prefs.edit()
                .putString(WIDGET_PREFIX + appWidgetId, moshi.adapter(Recipe.class).toJson(recipe))
                .apply();
    }

    public Recipe getIngredientsWidgetRecipe(int appWidgetId) {
        String recipeJson = prefs.getString(WIDGET_PREFIX + appWidgetId, null);

        if (recipeJson == null) return null;

        Recipe recipe;

        try {
            recipe = moshi.adapter(Recipe.class).fromJson(recipeJson);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return recipe;
    }

    public void deleteIngredientsWidgetRecipe(int appWidgetId) {
        prefs.edit()
                .remove(WIDGET_PREFIX + appWidgetId)
                .apply();
    }
}
