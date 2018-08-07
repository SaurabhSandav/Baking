package com.redridgeapps.baking.di.module;

import com.redridgeapps.baking.di.scope.PerBroadcastReceiver;
import com.redridgeapps.baking.ui.widgets.ingredient.RecipeIngredientsWidget;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class BroadcastReceiverBuilder {

    @PerBroadcastReceiver
    @ContributesAndroidInjector()
    public abstract RecipeIngredientsWidget bindRecipeIngredientsWidget();
}
