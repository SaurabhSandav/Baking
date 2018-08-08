package com.redridgeapps.baking.ui.main;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.GridLayoutManager;

import com.redridgeapps.baking.R;
import com.redridgeapps.baking.databinding.ActivityMainBinding;
import com.redridgeapps.baking.model.Recipe;
import com.redridgeapps.baking.repo.PreferenceRepository;
import com.redridgeapps.baking.repo.RecipeRepository;
import com.redridgeapps.baking.ui.base.BaseActivity;
import com.redridgeapps.baking.ui.detail.DetailActivity;
import com.redridgeapps.baking.ui.widgets.ingredient.RecipeIngredientsWidget;
import com.redridgeapps.baking.util.function.Consumer;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import okhttp3.OkHttpClient;

public class MainActivity extends BaseActivity<ActivityMainBinding> {

    public static final String KEY_RECIPE_LIST = "recipe_list";

    @Inject
    RecipeRepository recipeRepo;

    @Inject
    PreferenceRepository prefRepo;

    @Inject
    OkHttpClient okHttpClient;

    private List<Recipe> recipeList = new ArrayList<>();
    private RecipeAdapter recipeAdapter;
    private int appWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
    private boolean widgetSelectionActivity = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            recipeList = savedInstanceState.getParcelableArrayList(KEY_RECIPE_LIST);
        } else {
            observeRecipeOperations();
            recipeRepo.fetchRecipes();
        }

        checkIfWidgetSelectionMode(getIntent().getExtras());
        setupRecyclerView();
    }

    @Override
    protected int provideLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (recipeList != null)
            outState.putParcelableArrayList(KEY_RECIPE_LIST, new ArrayList<>(recipeList));
    }

    public OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }

    private void observeRecipeOperations() {

        recipeRepo.getRecipeLiveData().observe(this, newRecipeList -> {
            recipeList = newRecipeList;
            recipeAdapter.submitList(newRecipeList);
        });

        recipeRepo.getErrorLiveData().observe(this, event -> {
            if (event == null || event.hasBeenHandled()) return;

            Snackbar.make(getBinding().getRoot(), R.string.error_fetch_failed, Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.text_retry, view -> recipeRepo.fetchRecipes())
                    .show();
        });
    }

    private void setupRecyclerView() {

        WidthAndColumns widthAndColumns = calculateWidthAndColumns();

        Consumer<Recipe> clickListener;

        if (widgetSelectionActivity)
            clickListener = this::updateWidget;
        else
            clickListener = recipe -> startActivity(DetailActivity.createIntent(this, recipe));

        recipeAdapter = new RecipeAdapter(widthAndColumns.getItemWidth(), clickListener);

        getBinding().recyclerView.setAdapter(recipeAdapter);
        getBinding().recyclerView.setLayoutManager(new GridLayoutManager(this, widthAndColumns.getColumns()));
        getBinding().recyclerView.setHasFixedSize(true);

        recipeAdapter.submitList(recipeList);
    }

    private void checkIfWidgetSelectionMode(Bundle extras) {
        if (extras == null) return;

        appWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        widgetSelectionActivity = appWidgetId != AppWidgetManager.INVALID_APPWIDGET_ID;
    }

    private void updateWidget(Recipe recipe) {

        prefRepo.saveIngredientsWidgetRecipe(appWidgetId, recipe);

        RecipeIngredientsWidget
                .updateAppWidget(this, AppWidgetManager.getInstance(this), appWidgetId, recipe);

        Intent resultIntent = new Intent().putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    private WidthAndColumns calculateWidthAndColumns() {

        int columns = 1;

        // Calculate poster size to be relative to screen size
        float presetItemWidth = getResources().getDimension(R.dimen.default_recipe_poster_width);
        int fullWidth = getResources().getDisplayMetrics().widthPixels;

        if (fullWidth > presetItemWidth) columns = Math.round(fullWidth / presetItemWidth);

        int itemWidth = (fullWidth / columns);

        return new WidthAndColumns(itemWidth, columns);
    }

    private class WidthAndColumns {

        private int itemWidth;
        private int columns;

        WidthAndColumns(int itemWidth, int columns) {
            this.itemWidth = itemWidth;
            this.columns = columns;
        }

        int getItemWidth() {
            return itemWidth;
        }

        int getColumns() {
            return columns;
        }
    }
}
