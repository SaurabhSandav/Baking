package com.redridgeapps.baking.ui.detail.recipe_step;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.util.Pair;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.redridgeapps.baking.R;
import com.redridgeapps.baking.databinding.FragmentRecipeStepBinding;
import com.redridgeapps.baking.model.Recipe;
import com.redridgeapps.baking.model.Step;
import com.redridgeapps.baking.ui.base.BaseFragment;

import java.util.ArrayList;

public class RecipeStepFragment extends BaseFragment<FragmentRecipeStepBinding> {

    private static final String ARG_RECIPE = "recipe";

    private Recipe recipe;

    private FragmentInteractionListener activityListener;

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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentInteractionListener) {
            activityListener = (FragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement FragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        activityListener = null;
    }

    private void setupRecyclerView() {

        ArrayList<Object> objectList = new ArrayList<>();
        objectList.add("Ingredients");
        objectList.addAll(recipe.getIngredients());
        objectList.add("Steps");

        int stepStartIndex = objectList.size();
        objectList.addAll(recipe.getSteps());
        int stepEndIndex = objectList.size() - 1;

        Pair<Integer, Integer> stepIndexes = new Pair<>(stepStartIndex, stepEndIndex);

        RecipeStepAdapter stepAdapter = new RecipeStepAdapter(objectList, stepIndexes, activityListener::onStepSelected);

        getBinding().recyclerView.setAdapter(stepAdapter);
        getBinding().recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        getBinding().recyclerView.setHasFixedSize(true);
    }

    public interface FragmentInteractionListener {

        void onStepSelected(Step step);
    }
}
