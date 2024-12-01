package com.example.mealmate.recipe;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealmate.R;
import com.example.mealmate.general.RecipeSearchResponse;
import com.example.mealmate.general.SpoonAcularAPI;
import com.example.mealmate.general.User;
import com.example.mealmate.mealPlan.MealPlanActivity;
import com.example.mealmate.my_recipes.MyRecipesActivity;
import com.example.mealmate.profile.ProfileActivity;
import com.example.mealmate.registration_signin.LandingPageActivity;
import com.example.mealmate.registration_signin.MainActivity;
import com.example.mealmate.settings.SettingsActivity;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RecipeActivity extends AppCompatActivity {

    private DrawerLayout drawer;
    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private List<RecipeBean> recipeList;

    //Spoonacular API key
    private static final String API_KEY = "290447a9ac5f4260a69b9d1abd513523";
    private static final String BASE_URL = "https://api.spoonacular.com/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        drawer = findViewById(R.id.drawer_layout);

        User user = new User();
        ImageView menuIcon = findViewById(R.id.drawer);  // Assuming your menu icon has this id
        NavigationView navigationView = findViewById(R.id.nav_view);

        // Handle menu icon click (optional)
        if (menuIcon != null) {
            menuIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isDrawerOpen()) {
                        closeDrawer();
                    } else {
                        openDrawer();
                    }
                }
            });
        }

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                handleNavigationItemClick(itemId);
                return true;
            }
        });

        recyclerView = findViewById(R.id.recipeRecyclerView);
        int numberOfColumns = 2; // Adjust this to your desired number of columns
        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
       // recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recipeList = new ArrayList<>();
        userAdapter = new UserAdapter(recipeList);
        recyclerView.setAdapter(userAdapter);
        fetchRecipes();

    }

    private boolean isDrawerOpen() {
        return drawer.isDrawerOpen(GravityCompat.START);
    }

    private void openDrawer() {
        drawer.openDrawer(GravityCompat.START);
    }

    private void closeDrawer() {
        drawer.closeDrawer(GravityCompat.START);
    }


    final int myRecipeId = R.id.nav_my_recipe;
    final int homeId = R.id.nav_home;
    final int recipeId = R.id.nav_recipe;
    final int mealPlanId = R.id.nav_meal_plan;
    final int settingsId = R.id.nav_settings;
    final int logoutId = R.id.nav_logout;
// ... define other item IDs

    private void handleNavigationItemClick(int itemId) {
        if (itemId == homeId) {
            drawer.closeDrawer(GravityCompat.START);
            // Handle home item click
            Intent homeIntent = new Intent(this, LandingPageActivity.class);
            startActivity(homeIntent);
        } else if (itemId == recipeId) {
            drawer.closeDrawer(GravityCompat.START);
            Intent recipeIntent = new Intent(this, RecipeActivity.class);
            startActivity(recipeIntent);
            // ... and so on for other items
        } else if (itemId == mealPlanId) {
            drawer.closeDrawer(GravityCompat.START);
            // Handle default case (optional)
            Intent mealPlanIntent = new Intent(this, MealPlanActivity.class);
            startActivity(mealPlanIntent);
        }else if (itemId == settingsId) {
            drawer.closeDrawer(GravityCompat.START);
            // Handle default case (optional)
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
        }else if (itemId == logoutId) {
            drawer.closeDrawer(GravityCompat.START);
            // Handle default case (optional)
            Intent logoutIntent = new Intent(this, MainActivity.class);
            startActivity(logoutIntent);
            FirebaseAuth.getInstance().signOut();

        }else if (itemId == myRecipeId) {
            drawer.closeDrawer(GravityCompat.START);
            // Handle profile item click
            Intent myRecipeIntent = new Intent(this, MyRecipesActivity.class);
            startActivity(myRecipeIntent);
        }
    }

    private void fetchRecipes() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        SpoonAcularAPI spoonacularApi = retrofit.create(SpoonAcularAPI.class);

        Call<RecipeSearchResponse> call = spoonacularApi.searchRecipes("recipes", API_KEY);
        call.enqueue(new Callback<RecipeSearchResponse>() {
            @Override
            public void onResponse(@NonNull Call<RecipeSearchResponse> call, @NonNull Response<RecipeSearchResponse> response) {
                if (response.isSuccessful()) {
                    try {
                        List<RecipeBean> recipes = response.body().getResults();
                        if (recipes != null && !recipes.isEmpty()) {
                            recipeList.addAll(recipes);
                            userAdapter.notifyDataSetChanged();
                        } else {
                            Log.e("RecipeActivity", "No recipes found for the given query");
                            Toast.makeText(RecipeActivity.this, "No recipes found", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JsonSyntaxException e) {
                        Log.e("RecipeActivity", "Error parsing JSON: " + e.getMessage());
                        Toast.makeText(RecipeActivity.this, "Error parsing recipe data", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e("RecipeActivity", "Failed to fetch recipes: " + response.code() + " " + response.message());
                    Toast.makeText(RecipeActivity.this, "Failed to fetch recipes from Spoonacular!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<RecipeSearchResponse> call, @NonNull Throwable t) {
                Log.e("RecipeActivity", "Network error: " + t.getMessage());
                Toast.makeText(RecipeActivity.this, "Network error fetching recipes! Please try again later.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
