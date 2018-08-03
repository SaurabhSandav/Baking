package com.redridgeapps.baking.repo;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.redridgeapps.baking.api.RecipeService;
import com.redridgeapps.baking.di.scope.PerActivity;
import com.redridgeapps.baking.model.Recipe;
import com.redridgeapps.baking.util.Event;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@PerActivity
public class RecipeRepository {

    private RecipeService recipeService;
    private MutableLiveData<List<Recipe>> recipeLiveData;
    private MutableLiveData<Event<Throwable>> errorLiveData;

    @Inject
    RecipeRepository(RecipeService recipeService) {
        this.recipeService = recipeService;

        this.recipeLiveData = new MutableLiveData<>();
        this.errorLiveData = new MutableLiveData<>();
    }

    public LiveData<List<Recipe>> getRecipeLiveData() {
        return recipeLiveData;
    }

    public LiveData<Event<Throwable>> getErrorLiveData() {
        return errorLiveData;
    }

    public void fetchRecipes() {
        recipeService.getRecipes(RecipeService.RECIPE_URL).enqueue(new RecipeCallback());
    }

    private class RecipeCallback implements Callback<List<Recipe>> {

        @Override
        public void onResponse(@NonNull Call<List<Recipe>> call, @NonNull Response<List<Recipe>> response) {
            List<Recipe> recipeList = response.body();

            if (recipeList != null)
                recipeLiveData.postValue(response.body());
            else
                errorLiveData.postValue(new Event<>(new Exception("Not data received!")));
        }

        @Override
        public void onFailure(@NonNull Call<List<Recipe>> call, @NonNull Throwable t) {
            errorLiveData.postValue(new Event<>(t));
        }
    }
}
