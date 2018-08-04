package com.redridgeapps.baking.ui.detail.recipe_step;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.redridgeapps.baking.R;
import com.redridgeapps.baking.databinding.FragmentRecipeStepBinding;
import com.redridgeapps.baking.model.Recipe;
import com.redridgeapps.baking.ui.base.BaseFragment;

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
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected int provideLayout() {
        return R.layout.fragment_recipe_step;
    }
}
