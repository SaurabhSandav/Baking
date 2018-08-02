package com.redridgeapps.baking.api;

import com.redridgeapps.baking.model.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface RecipeService {

    String RECIPE_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";

    @GET
    Call<List<Recipe>> getRecipes(@Url String url);
}
