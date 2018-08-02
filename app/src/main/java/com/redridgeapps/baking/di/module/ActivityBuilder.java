package com.redridgeapps.baking.di.module;

import com.redridgeapps.baking.di.scope.PerActivity;
import com.redridgeapps.baking.ui.main.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilder {

    @PerActivity
    @ContributesAndroidInjector()
    public abstract MainActivity bindMainActivity();
}
