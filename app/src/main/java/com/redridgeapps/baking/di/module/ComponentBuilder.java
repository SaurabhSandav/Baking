package com.redridgeapps.baking.di.module;

import com.redridgeapps.baking.di.scope.PerActivity;
import com.redridgeapps.baking.di.scope.PerBroadcastReceiver;
import com.redridgeapps.baking.di.scope.PerFragment;
import com.redridgeapps.baking.di.scope.PerService;
import com.redridgeapps.baking.ui.detail.DetailActivity;
import com.redridgeapps.baking.ui.detail.recipe_step.RecipeStepFragment;
import com.redridgeapps.baking.ui.detail.step_detail.StepDetailFragment;
import com.redridgeapps.baking.ui.main.MainActivity;
import com.redridgeapps.baking.ui.widgets.ingredient.IngredientsListWidgetService;
import com.redridgeapps.baking.ui.widgets.ingredient.RecipeIngredientsWidget;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ComponentBuilder {

    // Activities

    @PerActivity
    @ContributesAndroidInjector()
    public abstract MainActivity bindMainActivity();

    @PerActivity
    @ContributesAndroidInjector()
    public abstract DetailActivity bindDetailActivity();

    // Fragments

    @PerFragment
    @ContributesAndroidInjector()
    public abstract RecipeStepFragment bindRecipeStepFragment();

    @PerFragment
    @ContributesAndroidInjector()
    public abstract StepDetailFragment bindStepDetailFragment();

    // Broadcast Receivers

    @PerBroadcastReceiver
    @ContributesAndroidInjector()
    public abstract RecipeIngredientsWidget bindRecipeIngredientsWidget();

    // Services

    @PerService
    @ContributesAndroidInjector()
    public abstract IngredientsListWidgetService bindIngredientsListWidgetService();
}
