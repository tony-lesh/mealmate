package com.example.mealmate.general;

import com.example.mealmate.mealPlan.MealSearchResponse;
import com.example.mealmate.recipe.ingredients.IngredientSearchResponse;
import com.example.mealmate.recipe.instructions.InstructionsSearchResponse;
import com.example.mealmate.shoppingCart.groceryList.GrocerySearchResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
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
    Call<MealSearchResponse> searchMealRecipes(@Query("query") String query,
                                               @Query("apiKey") String apiKey,
                                               @Query("number") int number);

    default Call<MealSearchResponse> searchMealRecipes(String query, String apiKey){
        return searchMealRecipes(query, apiKey, 1);
    }

    @POST("mealplanner/shopping-list/compute")
    Call<GrocerySearchResponse> shopList(@Query("apiKey") String apiKey,
                                         @Body int requestBody);

    @GET("recipes/complexSearch")
    Call<HomeSearchResponse> homeRecipes(@Query("query") String query,
                                             @Query("apiKey") String apiKey,
                                             @Query("number") int number);

    default Call<HomeSearchResponse> homeRecipes(String query, String apiKey){
        return homeRecipes(query, apiKey, 10);
    }

    @GET("recipes/{id}/information")
    Call<IngredientSearchResponse> singleSearchRecipes(@Path("id") int id,
                                                       @Query("apiKey") String apiKey,
                                                       @Query("includeNutrition") boolean nutrition);


    @GET("recipes/{id}/analyzedInstructions")
    Call<InstructionsSearchResponse> instructionSearchRecipes(@Path("id") int id,
                                                         @Query("apiKey") String apiKey,
                                                         @Query("stepBreakdown") boolean steps);

    @GET("wine/complexSearch")
    Call<RecipeSearchResponse> searchWines(@Query("query") String query,
                                             @Query("apiKey") String apiKey,
                                             @Query("number") int number);

    default Call<RecipeSearchResponse> searchWines(String query, String apiKey){
        return searchWines(query, apiKey, 1000);
    }


}
