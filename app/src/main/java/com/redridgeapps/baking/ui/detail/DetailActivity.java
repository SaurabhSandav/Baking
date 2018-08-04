package com.redridgeapps.baking.ui.detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.redridgeapps.baking.R;
import com.redridgeapps.baking.databinding.ActivityDetailBinding;
import com.redridgeapps.baking.model.Recipe;
import com.redridgeapps.baking.ui.base.BaseActivity;
import com.redridgeapps.baking.ui.detail.recipe_step.RecipeStepFragment;

public class DetailActivity extends BaseActivity<ActivityDetailBinding> {

    private static final String EXTRA_RECIPE = "extra_recipe";
    private static final String TAG_STEPS_FRAGMENT = "steps_fragment";

    private Recipe recipe;

    public static Intent createIntent(AppCompatActivity activity, Recipe recipe) {
        Intent intent = new Intent(activity, DetailActivity.class);
        intent.putExtra(EXTRA_RECIPE, recipe);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        recipe = getIntent().getParcelableExtra(EXTRA_RECIPE);

        boolean isLayoutMultiPane = getBinding().fragmentDetail != null;

        setTitle(recipe.getName());

        if (getSupportFragmentManager().findFragmentByTag(TAG_STEPS_FRAGMENT) == null)
            showStepsFragment();
    }

    @Override
    protected int provideLayout() {
        return R.layout.activity_detail;
    }

    private void showStepsFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(getBinding().fragmentContainer.getId(), RecipeStepFragment.newInstance(recipe), TAG_STEPS_FRAGMENT)
                .commit();
    }
}
