package com.redridgeapps.baking.ui.detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.redridgeapps.baking.R;
import com.redridgeapps.baking.databinding.ActivityDetailBinding;
import com.redridgeapps.baking.model.Recipe;
import com.redridgeapps.baking.model.Step;
import com.redridgeapps.baking.ui.base.BaseActivity;
import com.redridgeapps.baking.ui.detail.recipe_step.RecipeStepFragment;
import com.redridgeapps.baking.ui.detail.step_detail.StepDetailFragment;

public class DetailActivity extends BaseActivity<ActivityDetailBinding>
        implements RecipeStepFragment.FragmentInteractionListener,
        StepDetailFragment.FragmentInteractionListener {

    private static final String EXTRA_RECIPE = "extra_recipe";
    private static final String TAG_STEPS_FRAGMENT = "steps_fragment";
    private static final String TAG_DETAIL_FRAGMENT = "detail_fragment";
    private static final String KEY_SELECTED_STEP = "key_selected_step";

    private Recipe recipe;
    private boolean isLayoutMultiPane;
    private int selectedStepIndex = -1;

    public static Intent createIntent(AppCompatActivity activity, Recipe recipe) {
        Intent intent = new Intent(activity, DetailActivity.class);
        intent.putExtra(EXTRA_RECIPE, recipe);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        recipe = getIntent().getParcelableExtra(EXTRA_RECIPE);

        if (savedInstanceState != null)
            selectedStepIndex = savedInstanceState.getInt(KEY_SELECTED_STEP);

        setupLayout();
    }

    @Override
    protected int provideLayout() {
        return R.layout.activity_detail;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_SELECTED_STEP, selectedStepIndex);
    }

    @Override
    public void onStepSelected(Step step) {
        selectedStepIndex = recipe.getSteps().indexOf(step);

        Fragment newFragment = StepDetailFragment.newInstance(step, getLocation());

        replaceFragmentMultiPane(newFragment);
    }

    @Override
    public void onPreviousStep() {
        if (--selectedStepIndex < 0)
            throw new UnsupportedOperationException("No previous step!");

        Fragment fragment = StepDetailFragment.newInstance(recipe.getSteps().get(selectedStepIndex), getLocation());
        replaceFragmentMultiPane(fragment);
    }

    @Override
    public void onNextStep() {
        if (++selectedStepIndex >= recipe.getSteps().size())
            throw new UnsupportedOperationException("No next step!");

        Fragment fragment = StepDetailFragment.newInstance(recipe.getSteps().get(selectedStepIndex), getLocation());
        replaceFragmentMultiPane(fragment);
    }

    private void setupLayout() {
        isLayoutMultiPane = getBinding().fragmentDetail != null;

        setTitle(recipe.getName());

        getSupportFragmentManager().addOnBackStackChangedListener(this::observeFragmentTransaction);

        if (getSupportFragmentManager().findFragmentByTag(TAG_STEPS_FRAGMENT) == null)
            showStepsFragment();
        else {
            Fragment oldFragment = getSupportFragmentManager().findFragmentByTag(TAG_DETAIL_FRAGMENT);
            if (oldFragment != null) {
                getSupportFragmentManager().popBackStack();
                replaceFragmentMultiPane(StepDetailFragment.newInstance(oldFragment.getArguments()));
            }
        }
    }

    private void showStepsFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(getBinding().fragmentContainer.getId(), RecipeStepFragment.newInstance(recipe), TAG_STEPS_FRAGMENT)
                .commit();
    }

    private void replaceFragmentMultiPane(Fragment fragment) {

        int containerId = getBinding().fragmentContainer.getId();
        if (isLayoutMultiPane) containerId = getBinding().fragmentDetail.getId();

        getSupportFragmentManager().popBackStack();

        getSupportFragmentManager().beginTransaction()
                .replace(containerId, fragment, TAG_DETAIL_FRAGMENT)
                .addToBackStack(null)
                .commit();
    }

    private StepDetailFragment.Location getLocation() {
        if (selectedStepIndex == 0)
            return StepDetailFragment.Location.FIRST;
        else if (selectedStepIndex == recipe.getSteps().size() - 1)
            return StepDetailFragment.Location.LAST;
        else
            return StepDetailFragment.Location.MIDDLE;
    }

    private void observeFragmentTransaction() {
        if (isLayoutMultiPane) {
            Fragment fragment = getSupportFragmentManager().findFragmentByTag(TAG_DETAIL_FRAGMENT);

            if (fragment == null)
                getBinding().selectStep.setVisibility(View.VISIBLE);
            else
                getBinding().selectStep.setVisibility(View.GONE);
        }
    }
}
