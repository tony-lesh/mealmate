package com.example.mealmate.general;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SpoonAcularAPI {

    @GET("recipes/complexSearch")
    Call<RecipeSearchResponse> searchRecipes(@Query("query") String query,
                                             @Query("apiKey") String apiKey,
                                             @Query("number") int number);

    default Call<RecipeSearchResponse> searchRecipes(String query, String apiKey){
        return searchRecipes(query, apiKey, 1000);
    }


    @GET("recipes/complexSearch")
    Call<HomeSearchResponse> homeRecipes(@Query("query") String query,
                                             @Query("apiKey") String apiKey,
                                             @Query("number") int number);

    default Call<HomeSearchResponse> homeRecipes(String query, String apiKey){
        return homeRecipes(query, apiKey, 10);
    }


    @GET("wine/complexSearch")
    Call<RecipeSearchResponse> searchWines(@Query("query") String query,
                                             @Query("apiKey") String apiKey,
                                             @Query("number") int number);

    default Call<RecipeSearchResponse> searchWines(String query, String apiKey){
        return searchWines(query, apiKey, 1000);
    }

}
