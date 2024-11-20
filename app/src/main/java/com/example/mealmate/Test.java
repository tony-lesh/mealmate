package com.example.mealmate;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealmate.general.RecipeSearchResponse;
import com.example.mealmate.general.SpoonAcularAPI;
import com.example.mealmate.recipe.RecipeActivity;
import com.example.mealmate.recipe.RecipeBean;
import com.example.mealmate.recipe.UserAdapter;
import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Test extends AppCompatActivity {

    private DrawerLayout drawer;
    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private List<RecipeBean> recipeList;

    //Spoonacular API key
    private static final String API_KEY = "1ed546123a3b467cba5ed81d4462abef";
    private static final String BASE_URL = "https://api.spoonacular.com/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);

        recyclerView = findViewById(R.id.testRecyclerView);
        int numberOfColumns = 2; // Adjust this to your desired number of columns
        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        // recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recipeList = new ArrayList<>();
        userAdapter = new UserAdapter(recipeList);
        recyclerView.setAdapter(userAdapter);
        fetchRecipes();

    }

    private  void fetchRecipes() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        SpoonAcularAPI spoonacularApi = retrofit.create(SpoonAcularAPI.class);

        Call<RecipeSearchResponse> call = spoonacularApi.searchRecipes("recipes", API_KEY);
        call.enqueue(new Callback<RecipeSearchResponse>() {
            @Override
            public void onResponse(@NonNull Call<RecipeSearchResponse> call, @NonNull Response<RecipeSearchResponse>
                    response) {

                if (response.isSuccessful()) {
//                    assert response.body() != null;
//                    List<RecipeBean> recipes = response.body().getResults();
//                    Log.d("RecipeActivity", "Fetched " + recipes.size() + " recipes");
//                    recipeList.addAll(recipes);
//                    userAdapter.notifyDataSetChanged();

                    try {
                        assert response.body() != null;
                        List<RecipeBean> recipes = response.body().getResults();
                        recipeList.addAll(recipes);
                        userAdapter.notifyDataSetChanged();
                    } catch (JsonSyntaxException e) {
                        Log.e("RecipeActivity", "Error parsing JSON: " + e.getMessage());
                        Toast.makeText(Test.this, "Error parsing recipe data", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e("RecipeActivity", "Failed to fetch recipes: " + response.code() + " " + response.message());
                    Toast.makeText(Test.this, "Failed to fetch recipes from Spoonacular!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<RecipeSearchResponse> call, @NonNull Throwable t) {
                Log.e("RecipeActivity", "Network error: " + t.getMessage());
                Toast.makeText(Test.this, "Network error fetching recipes! Please check your internet connection.", Toast.LENGTH_SHORT).show();

                //   Toast.makeText(RecipeActivity.this, "Network error fetching recipes!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
