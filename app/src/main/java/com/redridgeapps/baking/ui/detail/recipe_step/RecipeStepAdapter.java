package com.redridgeapps.baking.ui.detail.recipe_step;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.github.vipulasri.timelineview.TimelineView;
import com.redridgeapps.baking.R;
import com.redridgeapps.baking.databinding.ItemRecipeStepBinding;
import com.redridgeapps.baking.model.Step;
import com.redridgeapps.baking.util.function.Consumer;

public class RecipeStepAdapter extends ListAdapter<Step, RecipeStepAdapter.ViewHolder> {

    private Consumer<Step> clickListener;

    RecipeStepAdapter(Consumer<Step> clickListener) {
        super(Step.DIFF_CALLBACK);
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ItemRecipeStepBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_recipe_step,
                parent,
                false
        );

        ViewHolder viewHolder = new ViewHolder(binding, viewType);

        binding.getRoot().setOnClickListener(view -> {
            if (viewHolder.getAdapterPosition() != RecyclerView.NO_POSITION) {
                clickListener.accept(getItem(viewHolder.getAdapterPosition()));
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindTo(getItem(position));
    }

    @Override
    public int getItemViewType(int position) {
        return TimelineView.getTimeLineViewType(position, getItemCount());
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private final ItemRecipeStepBinding binding;

        ViewHolder(ItemRecipeStepBinding binding, int viewType) {
            super(binding.getRoot());
            this.binding = binding;

            binding.timeMarker.initLine(viewType);
        }

        void bindTo(Step step) {
            binding.setStep(step);
            binding.executePendingBindings();
        }
    }
}
