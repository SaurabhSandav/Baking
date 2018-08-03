package com.redridgeapps.baking.ui.main;

import android.os.Bundle;
import android.support.design.widget.Snackbar;

import com.redridgeapps.baking.R;
import com.redridgeapps.baking.databinding.ActivityMainBinding;
import com.redridgeapps.baking.model.Recipe;
import com.redridgeapps.baking.repo.RecipeRepository;
import com.redridgeapps.baking.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class MainActivity extends BaseActivity<ActivityMainBinding> {

    public static final String KEY_RECIPE_LIST = "recipe_list";

    @Inject
    RecipeRepository recipeRepo;

    private List<Recipe> recipeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            recipeList = savedInstanceState.getParcelableArrayList(KEY_RECIPE_LIST);
        } else {
            observeRecipeOperations();
            recipeRepo.fetchRecipes();
        }
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

        recipeRepo.getRecipeLiveData().observe(this, newRecipeList -> recipeList = newRecipeList);

        recipeRepo.getErrorLiveData().observe(this, event -> {
            if (event == null || event.hasBeenHandled()) return;

            Snackbar.make(getBinding().getRoot(), R.string.error_fetch_failed, Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.text_retry, view -> recipeRepo.fetchRecipes())
                    .show();
        });
    }
}
