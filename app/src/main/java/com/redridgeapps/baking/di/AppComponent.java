package com.redridgeapps.baking.di;

import android.app.Application;

import com.redridgeapps.baking.App;
import com.redridgeapps.baking.di.module.ActivityBuilder;
import com.redridgeapps.baking.di.module.AppModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(
        modules = {
                AndroidSupportInjectionModule.class,
                ActivityBuilder.class,
                AppModule.class
        }
)
interface AppComponent extends AndroidInjector<App> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        AppComponent.Builder application(Application application);

        AppComponent build();
    }
}
