package com.example.mealmate.general;

import com.example.mealmate.recipe.RecipeBean;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SpoonAcularAPI {

    @GET("recipes/complexSearch")
    Call<RecipeSearchResponse> searchRecipes(@Query("query") String query,
                                             @Query("apiKey") String apiKey);




}
