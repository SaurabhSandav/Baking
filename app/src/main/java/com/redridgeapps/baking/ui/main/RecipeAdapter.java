package com.redridgeapps.baking.ui.main;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.redridgeapps.baking.R;
import com.redridgeapps.baking.databinding.ItemRecipeListBinding;
import com.redridgeapps.baking.model.Recipe;
import com.redridgeapps.baking.util.function.Consumer;

public class RecipeAdapter extends ListAdapter<Recipe, RecipeAdapter.ViewHolder> {

    private Consumer<Recipe> clickListener;
    private int itemWidth;

    RecipeAdapter(int itemWidth, Consumer<Recipe> clickListener) {
        super(Recipe.DIFF_CALLBACK);
        this.itemWidth = itemWidth;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ItemRecipeListBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_recipe_list,
                parent,
                false
        );

        // Set item aspect ratio to 16:9
        binding.getRoot().setLayoutParams(new ViewGroup.LayoutParams(itemWidth, (int) (itemWidth / 1.77)));

        ViewHolder viewHolder = new ViewHolder(binding);

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

    class ViewHolder extends RecyclerView.ViewHolder {

        private final ItemRecipeListBinding binding;

        ViewHolder(ItemRecipeListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bindTo(Recipe recipe) {
            binding.setRecipe(recipe);
            binding.executePendingBindings();
        }
    }
}
