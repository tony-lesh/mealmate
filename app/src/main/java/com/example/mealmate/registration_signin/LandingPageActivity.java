package com.example.mealmate.registration_signin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.mealmate.R;
import com.example.mealmate.general.User;
import com.example.mealmate.notifications.NotificationsActivity;
import com.example.mealmate.profile.ProfileActivity;
import com.example.mealmate.recipe.RecipeActivity;
import com.example.mealmate.recipe.RecipeBean;
import com.example.mealmate.settings.SettingsActivity;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LandingPageActivity extends Activity {

    private DrawerLayout drawer;
    private TextView tagNameTextView;
    DatabaseReference userRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);
        drawer = findViewById(R.id.drawer_layout);
        User user = new User();
        // Get the menu icon (optional)
        ImageView menuIcon = findViewById(R.id.drawer);  // Assuming your menu icon has this id
        TextView mealPlanTxtView = findViewById(R.id.createMealTextView);
        NavigationView navigationView = findViewById(R.id.nav_view); // Replace R.id.nav_view with your actual ID
        tagNameTextView = findViewById(R.id.tagName);

        userRef = FirebaseDatabase.getInstance().getReference("Users/" +user.getUserId());
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Get the user's name from the dataSnapshot
                String name = dataSnapshot.child("name").getValue(String.class);

                // Update the TextView
                tagNameTextView.setText("Welcome "+name+ "!");
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Firebase", "Failed to read user data", error.toException());
                tagNameTextView.setText("Guest!");
            }
        });

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
        mealPlanTxtView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   Intent intent = new Intent(LandingPageActivity.this, Meal.class);
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


    final int profileId = R.id.nav_profile;
    final int homeId = R.id.nav_home;
    final int recipeId = R.id.nav_recipe;
    final int notificationsId = R.id.nav_notifications;
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



//            String recipeId = userRef.push().getKey();
//            RecipeBean recipeBean = new RecipeBean(recipeId, name, price, imageRecipe);
//            if(recipeBean !=null){
//
//                assert recipeId != null;
//                userRef.child(recipeId).setValue(recipeBean);
//                Toast.makeText(LandingPageActivity.this, "Data successfully saved!", Toast.LENGTH_LONG).show();
//                Intent intent = new Intent(LandingPageActivity.this, RecipeActivity.class);
//                startActivity(intent);
//            }
//            else {
//                Toast.makeText(LandingPageActivity.this, "Failed to save data!", Toast.LENGTH_LONG).show();
//            }


            // Handle recipe item click
            Intent recipeIntent = new Intent(this, RecipeActivity.class);
            startActivity(recipeIntent);
            // ... and so on for other items
        } else if (itemId == notificationsId) {
            drawer.closeDrawer(GravityCompat.START);
            // Handle default case (optional)
            Intent notificationsIntent = new Intent(this, NotificationsActivity.class);
            startActivity(notificationsIntent);
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
        }else if (itemId == profileId) {
            drawer.closeDrawer(GravityCompat.START);
            // Handle profile item click
            Intent profileIntent = new Intent(this, ProfileActivity.class);
            startActivity(profileIntent);
        }
    }
}

