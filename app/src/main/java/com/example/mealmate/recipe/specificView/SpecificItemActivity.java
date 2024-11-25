package com.example.mealmate.recipe.specificView;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mealmate.R;
import com.example.mealmate.general.SpoonAcularAPI;

import com.example.mealmate.recipe.ingredients.Ingredient;
import com.example.mealmate.recipe.ingredients.IngredientAdapter;
import com.example.mealmate.recipe.ingredients.SingleRecipeBean;
import com.example.mealmate.recipe.ingredients.IngredientSearchResponse;
import com.example.mealmate.recipe.instructions.Instruction;
import com.example.mealmate.recipe.instructions.InstructionsAdapter;
import com.example.mealmate.recipe.instructions.InstructionsSearchResponse;
import com.example.mealmate.registration_signin.LandingPageActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SpecificItemActivity extends AppCompatActivity {
    ImageView recipeImageView;
    TextView recipeNameTextView;

    private RecyclerView recyclerView;
   // private SingleRecipeAdapter singleRecipeAdapter;
    private List<SingleRecipeBean> recipe;

    private IngredientAdapter ingredientAdapter;
    private List<Ingredient> ingredients;
    private InstructionsAdapter instructionAdapter;
    private List<Instruction> instructions;


    // Spoonacular API key
    private static final String API_KEY = "4fc61c7816c2497f8512e16e1cf4e863";
    private static final String BASE_URL = "https://api.spoonacular.com/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_recipe_display);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Find views by their ID
        recipeImageView = findViewById(R.id.singleImageView);
        recipeNameTextView = findViewById(R.id.recipeTitle);
       // recipeIngredientsTextView = findViewById(R.id.recipeIngredients);
        // Retrieve data from the Intent

        Integer recipeId = getIntent().getIntExtra("id", -1);
        String recipeName = getIntent().getStringExtra("recipeName");
        String recipeImage = getIntent().getStringExtra("recipeImage");
      //  String recipeIngredients = getIntent().getStringExtra("recipeIngredients");

        // Set data to views
        recipeNameTextView.setText(recipeName);
//        recipeIngredientsTextView.setText(recipeIngredients);

        // Load image using Glide
        Glide.with(this)
                .load(recipeImage)
                .into(recipeImageView);


        ImageButton imageButton = findViewById(R.id.imageBackBtn);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backIntent = new Intent(v.getContext(), LandingPageActivity.class);
                startActivity(backIntent);
            }
        });

        recyclerView = findViewById(R.id.singleDisplayRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        if (recipeId != -1) {
            if (isNetworkAvailable()) {
                fetchIngredients(recipeId);
                fetchInstructions(recipeId);
            } else {
                Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "No Recipe ID passed", Toast.LENGTH_SHORT).show();
        }

    }

    private void fetchIngredients(int recipeId) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        SpoonAcularAPI spoonacularApi = retrofit.create(SpoonAcularAPI.class);

        Call<IngredientSearchResponse> call = spoonacularApi.singleSearchRecipes(recipeId, API_KEY, false);
        call.enqueue(new Callback<IngredientSearchResponse>() {
            @Override
            public void onResponse(@NonNull Call<IngredientSearchResponse> call, @NonNull Response<IngredientSearchResponse> response) {
                if (response.body() != null) {
                    Log.d("API Response", response.body().toString());
                } else {
                    Log.e("SingleRecipeActivity", "Response body is null");
                }

                if (response.isSuccessful()) {
                    IngredientSearchResponse responseBody = response.body();
                    if (responseBody != null && responseBody.getIngredients() != null) {
                        ingredients = responseBody.getIngredients();  // Assuming this is how ingredients are fetched

                        // Initialize IngredientAdapter and set it to the RecyclerView
                        ingredientAdapter = new IngredientAdapter(SpecificItemActivity.this, ingredients);
                        recyclerView.setAdapter(ingredientAdapter);

                    } else {
                        Log.e("SpecificItemActivity", "No ingredients found for the given recipe ID");
                        Toast.makeText(SpecificItemActivity.this, "No Ingredients found", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e("SpecificItemActivity", "API call failed with response code: " + response.code());
                    Toast.makeText(SpecificItemActivity.this, "Error fetching data: " + response.code(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(@NonNull Call<IngredientSearchResponse> call, @NonNull Throwable t) {
                Log.e("SingleRecipeActivity", "Network error: " + t.getMessage());
                Toast.makeText(SpecificItemActivity.this, "Please check your connection and try again", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchInstructions(int recipeId) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        SpoonAcularAPI spoonacularApi = retrofit.create(SpoonAcularAPI.class);

        Call<InstructionsSearchResponse> call = spoonacularApi.instructionSearchRecipes(recipeId, API_KEY, false);
        call.enqueue(new Callback<InstructionsSearchResponse>() {
            @Override
            public void onResponse(@NonNull Call<InstructionsSearchResponse> call, @NonNull Response<InstructionsSearchResponse> response) {
                if (response.body() != null) {
                    Log.d("API Response", response.body().toString());
                } else {
                    Log.e("SingleRecipeActivity", "Response body is null");
                }

                if (response.isSuccessful()) {
                    InstructionsSearchResponse responseBody = response.body();
                    if (responseBody != null && responseBody.getInstructions() != null) {
                        instructions = responseBody.getInstructions();  // Assuming this is how ingredients are fetched

// Initialize IngredientAdapter and set it to the RecyclerView
                        instructionAdapter = new InstructionsAdapter(SpecificItemActivity.this, instructions);
                        recyclerView.setAdapter(instructionAdapter);

                    } else {
                        Log.e("SpecificItemActivity", "No ingredients found for the given recipe ID");
                        Toast.makeText(SpecificItemActivity.this, "No Ingredients found", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e("SpecificItemActivity", "API call failed with response code: " + response.code());
                    Toast.makeText(SpecificItemActivity.this, "Error fetching data: " + response.code(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(@NonNull Call<InstructionsSearchResponse> call, @NonNull Throwable t) {
                Log.e("SingleRecipeActivity", "Network error: " + t.getMessage());
                Toast.makeText(SpecificItemActivity.this, "Please check your connection and try again", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Method to check network availability
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
