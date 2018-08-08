package com.redridgeapps.baking.di.module;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.redridgeapps.baking.api.RecipeService;
import com.squareup.moshi.Moshi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

@Module
public abstract class AppModule {

    @Provides
    @Singleton
    static SharedPreferences provideSharedPreferences(Application application) {
        return PreferenceManager.getDefaultSharedPreferences(application);
    }

    @Provides
    @Singleton
    static OkHttpClient provideOkHttpClient() {
        return new OkHttpClient()
                .newBuilder()
                .build();
    }

    @Provides
    @Singleton
    static Retrofit provideRetrofit(MoshiConverterFactory moshiCon, OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl("http://localhost/")
                .client(okHttpClient)
                .addConverterFactory(moshiCon)
                .build();
    }

    @Provides
    @Singleton
    static Moshi provideMoshi() {
        return new Moshi.Builder().build();
    }

    @Provides
    @Singleton
    static MoshiConverterFactory provideMoshiConverterFactory(Moshi moshi) {
        return MoshiConverterFactory.create(moshi);
    }

    @Provides
    @Singleton
    static RecipeService provideRecipeService(Retrofit retrofit) {
        return retrofit.create(RecipeService.class);
    }
}
