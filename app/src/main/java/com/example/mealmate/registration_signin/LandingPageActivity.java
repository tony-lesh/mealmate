package com.example.mealmate.registration_signin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealmate.R;
import com.example.mealmate.general.HomeSearchResponse;
import com.example.mealmate.general.SpoonAcularAPI;
import com.example.mealmate.general.User;
import com.example.mealmate.mealPlan.MealPlanActivity;
import com.example.mealmate.my_recipes.MyRecipesActivity;
import com.example.mealmate.profile.ProfileActivity;
import com.example.mealmate.recipe.RecipeActivity;
import com.example.mealmate.settings.SettingsActivity;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LandingPageActivity extends Activity {

    private DrawerLayout drawer;
    private TextView tagNameTextView;
    DatabaseReference userRef;

    private RecyclerView recyclerView;
    private HomeAdapter homeAdapter;
    private List<HomeBean> homeList;

    //Spoonacular API key
    private static final String API_KEY = "290447a9ac5f4260a69b9d1abd513523";
    private static final String BASE_URL = "https://api.spoonacular.com/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        drawer = findViewById(R.id.drawer_layout);
        User user = new User();
        // Get the menu icon (optional)
        ImageView menuIcon = findViewById(R.id.drawer);  // Assuming your menu icon has this id
      //  TextView mealPlanTxtView = findViewById(R.id.createMealTextView);
        NavigationView navigationView = findViewById(R.id.nav_view); // Replace R.id.nav_view with your actual ID
        tagNameTextView = findViewById(R.id.tagName);

//        userRef = FirebaseDatabase.getInstance().getReference("Users/" +user.getUserId());
//        userRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                // Get the user's name from the dataSnapshot
//                String name = dataSnapshot.child("name").getValue(String.class);
//
//                // Update the TextView
//                tagNameTextView.setText("Welcome "+name+ "!");
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Log.e("Firebase", "Failed to read user data", error.toException());
//                tagNameTextView.setText("Guest!");
//            }
//        });


        // Initialize FirebaseAuth
        FirebaseAuth auth = FirebaseAuth.getInstance();

// Get the currently signed-in user
        FirebaseUser currentUser = auth.getCurrentUser();

        if (currentUser != null) {
            // Get the user's display name from Firebase Authentication
            String displayName = currentUser.getDisplayName();

            // If the display name is available, show it
            if (displayName != null && !displayName.isEmpty()) {
                tagNameTextView.setText("Welcome " + displayName + "!");
            } else {
                // If no display name is set, show a default welcome message
                tagNameTextView.setText("Welcome User!");
            }
        } else {
            // No user is signed in, handle the case appropriately
            tagNameTextView.setText("Guest!");
        }


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
//        mealPlanTxtView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(v.getContext(), "Meal Plan Clicked!", Toast.LENGTH_LONG).show();
//                Intent mealPlanIntent = new Intent(LandingPageActivity.this, MealPlanActivity.class);
//                startActivity(mealPlanIntent);
//            }
//        });

        Button createMealTextView = findViewById(R.id.createMealButton);
        createMealTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Meal Plan Clicked!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(v.getContext(), RecipeActivity.class);
                startActivity(intent);
            }
        });
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                handleNavigationItemClick(itemId);
                return true;
            }
        });

        recyclerView = findViewById(R.id.homeRecyclerView);
         recyclerView.setLayoutManager(new LinearLayoutManager(this));

        homeList = new ArrayList<>();
        homeAdapter = new HomeAdapter(this, homeList);
        recyclerView.setAdapter(homeAdapter);
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

        Call<HomeSearchResponse> call = spoonacularApi.homeRecipes("food", API_KEY);
        call.enqueue(new Callback<HomeSearchResponse>() {
            @Override
            public void onResponse(@NonNull Call<HomeSearchResponse> call, @NonNull Response<HomeSearchResponse> response) {
                if (response.isSuccessful()) {
                    try {
                        List<HomeBean> home = response.body().getHome_recipe();
                        if (home != null && !home.isEmpty()) {
                            homeList.addAll(home);
                            homeAdapter.notifyDataSetChanged();
                        } else {
                            Log.e("RecipeActivity", "No recipes found for the given query");
                            Toast.makeText(LandingPageActivity.this, "No recipes found", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JsonSyntaxException e) {
                        Log.e("RecipeActivity", "Error parsing JSON: " + e.getMessage());
                        Toast.makeText(LandingPageActivity.this, "Error parsing recipe data", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e("RecipeActivity", "Failed to fetch recipes: " + response.code() + " " + response.message());
                    Toast.makeText(LandingPageActivity.this, "Failed to fetch recipes from Spoonacular!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<HomeSearchResponse> call, @NonNull Throwable t) {
                Log.e("RecipeActivity", "Network error: " + t.getMessage());
                Toast.makeText(LandingPageActivity.this, "Network error fetching recipes! Please try again later.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

