package com.redridgeapps.baking.ui.detail.recipe_step;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.vipulasri.timelineview.LineType;
import com.redridgeapps.baking.R;
import com.redridgeapps.baking.databinding.ItemRecipeIngredientBinding;
import com.redridgeapps.baking.databinding.ItemRecipeLabelBinding;
import com.redridgeapps.baking.databinding.ItemRecipeStepBinding;
import com.redridgeapps.baking.model.Ingredient;
import com.redridgeapps.baking.model.Step;
import com.redridgeapps.baking.util.function.Consumer;

import java.util.ArrayList;
import java.util.List;

public class RecipeStepAdapter extends RecyclerView.Adapter<RecipeStepAdapter.CustomViewHolder> {

    private List<Object> objectList;
    private Consumer<Step> clickListener;
    private Pair<Integer, Integer> stepIndexes;

    RecipeStepAdapter(ArrayList<Object> objectList, Pair<Integer, Integer> stepIndexes, Consumer<Step> clickListener) {
        this.objectList = objectList;
        this.stepIndexes = stepIndexes;
        this.clickListener = clickListener;
    }

    @SuppressWarnings("ConstantConditions")
    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        CustomViewHolder viewHolder = null;

        switch (viewType) {
            case ViewType.LABEL:
                ItemRecipeLabelBinding labelBinding = DataBindingUtil.inflate(
                        LayoutInflater.from(parent.getContext()),
                        R.layout.item_recipe_label,
                        parent,
                        false
                );

                return new ViewHolderLabel(labelBinding);
            case ViewType.INGREDIENT:
                ItemRecipeIngredientBinding ingredientBinding = DataBindingUtil.inflate(
                        LayoutInflater.from(parent.getContext()),
                        R.layout.item_recipe_ingredient,
                        parent,
                        false
                );

                return new ViewHolderIngredient(ingredientBinding);
            case ViewType.STEP_FIRST:
            case ViewType.STEP_MIDDLE:
            case ViewType.STEP_LAST:
                ItemRecipeStepBinding stepBinding = DataBindingUtil.inflate(
                        LayoutInflater.from(parent.getContext()),
                        R.layout.item_recipe_step,
                        parent,
                        false
                );

                final ViewHolderStep viewHolderStep = new ViewHolderStep(stepBinding, getTimeLineViewType(viewType));

                stepBinding.getRoot().setOnClickListener(view -> {
                    int adapterPosition = viewHolderStep.getAdapterPosition();
                    if (adapterPosition != RecyclerView.NO_POSITION) {
                        clickListener.accept((Step) objectList.get(adapterPosition));
                    }
                });

                return viewHolderStep;
        }

        return viewHolder;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.bindTo(objectList.get(position));
    }

    @Override
    public int getItemViewType(int position) {
        Object object = objectList.get(position);

        if (object instanceof String)
            return ViewType.LABEL;
        else if (object instanceof Ingredient)
            return ViewType.INGREDIENT;
        else if (object instanceof Step) {
            if (stepIndexes.first != null && position == stepIndexes.first)
                return ViewType.STEP_FIRST;
            else if (stepIndexes.second != null && position == stepIndexes.second)
                return ViewType.STEP_LAST;
            else return ViewType.STEP_MIDDLE;
        }

        return -1;
    }

    @Override
    public int getItemCount() {
        return objectList.size();
    }

    private int getTimeLineViewType(int viewType) {
        switch (viewType) {
            case ViewType.STEP_FIRST:
                return LineType.BEGIN;
            case ViewType.STEP_LAST:
                return LineType.END;
            default:
                return LineType.NORMAL;
        }
    }

    abstract class CustomViewHolder<T> extends RecyclerView.ViewHolder {

        CustomViewHolder(View itemView) {
            super(itemView);
        }

        abstract void bindTo(T t);
    }

    class ViewHolderLabel extends CustomViewHolder<String> {

        private final ItemRecipeLabelBinding binding;

        ViewHolderLabel(ItemRecipeLabelBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bindTo(String label) {
            binding.setLabel(label);
            binding.executePendingBindings();
        }
    }

    class ViewHolderIngredient extends CustomViewHolder<Ingredient> {

        private final ItemRecipeIngredientBinding binding;

        ViewHolderIngredient(ItemRecipeIngredientBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bindTo(Ingredient ingredient) {
            binding.setIngredient(ingredient);
            binding.executePendingBindings();
        }
    }

    class ViewHolderStep extends CustomViewHolder<Step> {

        private final ItemRecipeStepBinding binding;

        ViewHolderStep(ItemRecipeStepBinding binding, int viewType) {
            super(binding.getRoot());
            this.binding = binding;

            binding.timeMarker.initLine(viewType);
        }

        void bindTo(Step step) {
            binding.setStep(step);
            binding.executePendingBindings();
        }
    }

    private class ViewType {
        static final int LABEL = 0;
        static final int INGREDIENT = 1;
        static final int STEP_FIRST = 2;
        static final int STEP_MIDDLE = 3;
        static final int STEP_LAST = 4;
    }
}
