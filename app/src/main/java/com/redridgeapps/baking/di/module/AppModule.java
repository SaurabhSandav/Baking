package com.redridgeapps.baking.di.module;

import com.redridgeapps.baking.api.RecipeService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

@Module
public abstract class AppModule {

    @Provides
    @Singleton
    static Retrofit provideRetrofit(MoshiConverterFactory moshiCon) {
        return new Retrofit.Builder()
                .baseUrl("http://localhost/")
                .addConverterFactory(moshiCon)
                .build();
    }

    @Provides
    @Singleton
    static MoshiConverterFactory provideMoshiConverterFactory() {
        return MoshiConverterFactory.create();
    }

    @Provides
    @Singleton
    static RecipeService provideRecipeService(Retrofit retrofit) {
        return retrofit.create(RecipeService.class);
    }
}
