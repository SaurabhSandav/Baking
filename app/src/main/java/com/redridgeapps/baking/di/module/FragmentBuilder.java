package com.redridgeapps.baking.di.module;

import com.redridgeapps.baking.di.scope.PerFragment;
import com.redridgeapps.baking.ui.detail.recipe_step.RecipeStepFragment;
import com.redridgeapps.baking.ui.detail.step_detail.StepDetailFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentBuilder {

    @PerFragment
    @ContributesAndroidInjector()
    public abstract RecipeStepFragment bindRecipeStepFragment();

    @PerFragment
    @ContributesAndroidInjector()
    public abstract StepDetailFragment bindStepDetailFragment();
}
