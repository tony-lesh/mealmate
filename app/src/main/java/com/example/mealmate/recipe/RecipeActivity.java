package com.example.mealmate.recipe;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealmate.R;
import com.example.mealmate.general.RecipeSearchResponse;
import com.example.mealmate.general.SpoonAcularAPI;
import com.example.mealmate.general.User;
import com.example.mealmate.notifications.NotificationsActivity;
import com.example.mealmate.profile.ProfileActivity;
import com.example.mealmate.registration_signin.LandingPageActivity;
import com.example.mealmate.registration_signin.MainActivity;
import com.example.mealmate.settings.SettingsActivity;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

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
   // private List<RecipeBean> userList;
    private DatabaseReference dbRef;
    private List<RecipeBean> recipeList;

    // ... (Replace YOUR_API_KEY with your actual Spoonacular API key)
    private static final String API_KEY = "1ed546123a3b467cba5ed81d4462abef";
    private static final String BASE_URL = "https://api.spoonacular.com/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
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

        recipeList = new ArrayList<>();
        userAdapter = new UserAdapter(recipeList);
        recyclerView.setAdapter(userAdapter);

        fetchRecipes("recipes");

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


//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        setContentView(R.layout.activity_recipe);
//
//        recipeRecyclerView
//                = findViewById(R.id.recipeRecyclerView);
//        int numberOfColumns = 2; // Adjust this to your desired number of columns
//        recipeRecyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
//
//        recipeList = new ArrayList<>();
//        recipeAdapter = new UserAdapter(recipeList); // Replace UserAdapter
//
//        recipeRecyclerView.setAdapter(recipeAdapter);
//
//        // ... existing code for Firebase database retrieval ...
//
//        // Fetch recipes using Spoonacular API (optional)
//        fetchRecipes("chicken"); // Replace "chicken" with your desired search query
//    }

    private  void fetchRecipes(String query) {

        String responseJson = new String();

        Gson gson = new Gson();
        RecipeSearchResponse response = gson.fromJson(responseJson, RecipeSearchResponse.class);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        SpoonAcularAPI spoonacularApi = retrofit.create(SpoonAcularAPI.class);

        Call<RecipeSearchResponse> call = spoonacularApi.searchRecipes(query, API_KEY);
        call.enqueue(new Callback<RecipeSearchResponse>() {
            @Override
            public void onResponse(@NonNull Call<RecipeSearchResponse> call, @NonNull Response<RecipeSearchResponse>
                    response) {
                if (response.isSuccessful())
                {
                    assert response.body() != null;
                    List<RecipeBean> recipes = response.body().getResults();

//                    for (RecipeSearchResponse.Result result : response.getResults()) {
//                        RecipeBean recipe = new RecipeBean(
//                                result.getId(),
//                                result.getTitle(),
//                                result.getImage(),
//                                result.getIngredients(),
//                                result.getInstructions()
//                        );
//                        recipes.add(recipe);
//                    }
                    recipeList.addAll(recipes);
                    userAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(RecipeActivity.this, "Failed to fetch recipes from Spoonacular!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<RecipeSearchResponse> call, @NonNull Throwable t) {
                Toast.makeText(RecipeActivity.this, "Network error fetching recipes!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //        dbRef = FirebaseDatabase.getInstance().getReference("Users");
//        dbRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                recipeList.clear();
//                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
//                    RecipeBean rBean = dataSnapshot.getValue(RecipeBean.class);
//                    recipeList.add(rBean);
//                }
//                userAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(RecipeActivity.this, "Failed to load data!", Toast.LENGTH_LONG).show();
//
//            }
//        });
}
