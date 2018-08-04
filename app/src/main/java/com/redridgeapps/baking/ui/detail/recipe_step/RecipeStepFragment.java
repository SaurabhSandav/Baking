package com.redridgeapps.baking.ui.detail.recipe_step;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.redridgeapps.baking.R;
import com.redridgeapps.baking.databinding.FragmentRecipeStepBinding;
import com.redridgeapps.baking.model.Recipe;
import com.redridgeapps.baking.model.Step;
import com.redridgeapps.baking.ui.base.BaseFragment;
import com.redridgeapps.baking.util.function.Consumer;

public class RecipeStepFragment extends BaseFragment<FragmentRecipeStepBinding> {

    private static final String ARG_RECIPE = "recipe";

    private Recipe recipe;

    public static RecipeStepFragment newInstance(Recipe recipe) {

        Bundle args = new Bundle();
        args.putParcelable(ARG_RECIPE, recipe);

        RecipeStepFragment fragment = new RecipeStepFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            recipe = getArguments().getParcelable(ARG_RECIPE);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);

        setupRecyclerView();

        return rootView;
    }

    @Override
    protected int provideLayout() {
        return R.layout.fragment_recipe_step;
    }

    private void setupRecyclerView() {

        Consumer<Step> clickListener = step -> {
        };

        RecipeStepAdapter stepAdapter = new RecipeStepAdapter(clickListener);

        getBinding().recyclerView.setAdapter(stepAdapter);
        getBinding().recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        getBinding().recyclerView.setHasFixedSize(true);

        stepAdapter.submitList(recipe.getSteps());
    }
}
