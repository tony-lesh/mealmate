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
import com.example.mealmate.mealPlan.MealPlanActivity;
import com.example.mealmate.my_recipes.MyRecipesActivity;
import com.example.mealmate.recipe.RecipeActivity;
import com.example.mealmate.settings.SettingsActivity;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

        // Get the userName passed from MainActivity
        String userName = getIntent().getStringExtra("userName");

        tagNameTextView = findViewById(R.id.tagName);
        if (userName != null && !userName.isEmpty()) {
            tagNameTextView.setText("Welcome, " + userName + "!");
        } else {
            displayFirebaseUserName();
        }

        drawer = findViewById(R.id.drawer_layout);
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

        Button createMealTextView = findViewById(R.id.createMealButton);
        createMealTextView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), RecipeActivity.class);
            startActivity(intent);
        });

        recyclerView = findViewById(R.id.homeRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        homeList = new ArrayList<>();
        homeAdapter = new HomeAdapter(this, homeList);
        recyclerView.setAdapter(homeAdapter);
        fetchRecipes();
    }

    private void displayFirebaseUserName() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String displayName = currentUser.getDisplayName();
            if (displayName != null && !displayName.isEmpty()) {
                tagNameTextView.setText("Welcome, " + displayName.toUpperCase() + "!");
            } else {
                fetchUserNameFromDatabase(currentUser.getUid());
            }
        } else {
            tagNameTextView.setText("Welcome, Guest!");
        }
    }

    private void fetchUserNameFromDatabase(String userId) {
        userRef = FirebaseDatabase.getInstance().getReference("Users").child(userId);
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child("name").getValue(String.class);
                if (name != null && !name.isEmpty()) {
                    tagNameTextView.setText("Welcome, " + name.toUpperCase() + "!");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Firebase", "Failed to read user data", error.toException());
                tagNameTextView.setText("Welcome, Guest!");
            }
        });
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
        spoonacularApi.homeRecipes("food", API_KEY).enqueue(new Callback<HomeSearchResponse>() {
            @Override
            public void onResponse(@NonNull Call<HomeSearchResponse> call, @NonNull Response<HomeSearchResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    homeList.addAll(response.body().getHome_recipe());
                    homeAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(@NonNull Call<HomeSearchResponse> call, @NonNull Throwable t) {
                Log.e("RecipeActivity", "Network error: " + t.getMessage());
            }
        });
    }
}
