package com.example.mealmate.general;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.mealmate.R;
import com.example.mealmate.mealPlan.MealPlanActivity;
import com.example.mealmate.profile.ProfileActivity;
import com.example.mealmate.recipe.RecipeActivity;
import com.example.mealmate.registration_signin.LandingPageActivity;
import com.example.mealmate.registration_signin.MainActivity;
import com.example.mealmate.settings.SettingsActivity;
import com.google.android.material.navigation.NavigationView;

public class DrawerAdapter extends Activity {

    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        drawer = findViewById(R.id.drawer_layout);
        User user = new User();
        // Get the menu icon (optional)
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
        if (itemId == myRecipeId) {
            drawer.closeDrawer(GravityCompat.START);
            // Handle profile item click
            Intent profileIntent = new Intent(this, ProfileActivity.class);
            startActivity(profileIntent);
        } else if (itemId == homeId) {
            drawer.closeDrawer(GravityCompat.START);
            // Handle home item click
            Intent homeIntent = new Intent(this, LandingPageActivity.class);
            startActivity(homeIntent);
        } else if (itemId == recipeId) {
            drawer.closeDrawer(GravityCompat.START);
            // Handle recipe item click
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
        }
    }
}
