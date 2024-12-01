package com.example.mealmate.my_recipes;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealmate.R;
import com.example.mealmate.mealPlan.MealPlanActivity;
import com.example.mealmate.my_recipes.enter_recipe.EnterRecipeActivity;
import com.example.mealmate.recipe.RecipeActivity;
import com.example.mealmate.registration_signin.LandingPageActivity;
import com.example.mealmate.registration_signin.MainActivity;
import com.example.mealmate.settings.SettingsActivity;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MyRecipesActivity extends AppCompatActivity {

    private DrawerLayout drawer;
    private TextView tagNameTextView;

    private RecyclerView recyclerView;
    private MyRecipeAdapter recipeAdapter;
    private List<MyRecipeBean> recipeBeanList;
    private DatabaseReference dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_recipes_display);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        drawer = findViewById(R.id.drawer_layout);
        // Get the menu icon (optional)
        ImageView menuIcon = findViewById(R.id.drawer);  // Assuming your menu icon has this id
        //  TextView mealPlanTxtView = findViewById(R.id.createMealTextView);
        NavigationView navigationView = findViewById(R.id.nav_view); // Replace R.id.nav_view with your actual ID


        recyclerView = findViewById(R.id.myRecipesRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setLayoutManager(new GridLayoutManager(this, ));


        recipeBeanList = new ArrayList<>();
        recipeAdapter = new MyRecipeAdapter(recipeBeanList);
        recyclerView.setAdapter(recipeAdapter);

        dbRef = FirebaseDatabase.getInstance().getReference("My_Recipes");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                recipeBeanList.clear();
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    MyRecipeBean recipeBean = dataSnapshot.getValue(MyRecipeBean.class);
                    recipeBeanList.add(recipeBean);
                }
                recipeAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MyRecipesActivity.this, "Failed to load data!", Toast.LENGTH_LONG).show();
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

        Button addNewRecordBtn = findViewById(R.id.addNewBtn);
        addNewRecordBtn.setOnClickListener(v ->
                startActivity(new Intent(MyRecipesActivity.this, EnterRecipeActivity.class)));

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
}
