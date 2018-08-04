package com.redridgeapps.baking.ui.main;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.GridLayoutManager;

import com.redridgeapps.baking.R;
import com.redridgeapps.baking.databinding.ActivityMainBinding;
import com.redridgeapps.baking.model.Recipe;
import com.redridgeapps.baking.repo.RecipeRepository;
import com.redridgeapps.baking.ui.base.BaseActivity;
import com.redridgeapps.baking.ui.detail.DetailActivity;
import com.redridgeapps.baking.util.function.Consumer;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class MainActivity extends BaseActivity<ActivityMainBinding> {

    public static final String KEY_RECIPE_LIST = "recipe_list";

    @Inject
    RecipeRepository recipeRepo;

    private List<Recipe> recipeList = new ArrayList<>();
    private RecipeAdapter recipeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            recipeList = savedInstanceState.getParcelableArrayList(KEY_RECIPE_LIST);
        } else {
            observeRecipeOperations();
            recipeRepo.fetchRecipes();
        }

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

        Consumer<Recipe> clickListener = recipe -> startActivity(DetailActivity.createIntent(this, recipe));

        recipeAdapter = new RecipeAdapter(widthAndColumns.getItemWidth(), clickListener);

        getBinding().recyclerView.setAdapter(recipeAdapter);
        getBinding().recyclerView.setLayoutManager(new GridLayoutManager(this, widthAndColumns.getColumns()));
        getBinding().recyclerView.setHasFixedSize(true);

        recipeAdapter.submitList(recipeList);
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
