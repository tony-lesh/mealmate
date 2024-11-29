package com.example.mealmate.mealPlan.dailyMealPlan;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealmate.R;
import com.example.mealmate.general.RecipeSearchResponse;
import com.example.mealmate.general.SpoonAcularAPI;
import com.example.mealmate.recipe.RecipeActivity;
import com.example.mealmate.recipe.RecipeBean;
import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DailyPlanFragment extends Fragment {
    private RecyclerView recyclerView;
    private DailyPlanAdapter dailyPlanAdapter;
    private List<DailyPlanBean> dailyPlanBeanList;

    // Spoonacular API key
    private static final String API_KEY = "290447a9ac5f4260a69b9d1abd513523";
    private static final String BASE_URL = "https://api.spoonacular.com/";

    public DailyPlanFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_daily_planner, container, false);

        // Handle window insets for system bars (status bar, navigation bar)
        ViewCompat.setOnApplyWindowInsetsListener(view.findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Set up RecyclerView
        recyclerView = view.findViewById(R.id.dailyMealPlannerRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initialize data list and adapter
        dailyPlanBeanList = new ArrayList<>();
        dailyPlanAdapter = new DailyPlanAdapter(getContext(), dailyPlanBeanList);
        recyclerView.setAdapter(dailyPlanAdapter);

        // Fetch recipes or daily plan data from the API
     fetchRecipes();

        return view;
    }

    private void fetchRecipes() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        SpoonAcularAPI spoonacularApi = retrofit.create(SpoonAcularAPI.class);

        Call<DailyPlanSearchResponse> call = spoonacularApi.dailyResponse("timeframe", API_KEY);
        call.enqueue(new Callback<DailyPlanSearchResponse>() {
            @Override
            public void onResponse(@NonNull Call<DailyPlanSearchResponse> call, @NonNull Response<DailyPlanSearchResponse> response) {
                if (response.isSuccessful()) {
                    try {
                        List<DailyPlanBean> dayMeal = response.body().getItems();
                        if (dayMeal != null && !dayMeal.isEmpty()) {
                            dailyPlanBeanList.addAll(dayMeal);
                            dailyPlanAdapter.notifyDataSetChanged();
                        } else {
                            Log.e("RecipeActivity", "No recipes found for the given query");
                            Toast.makeText(getContext(), "No recipes found", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JsonSyntaxException e) {
                        Log.e("RecipeActivity", "Error parsing JSON: " + e.getMessage());
                        Toast.makeText(getContext(), "Error parsing recipe data", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e("RecipeActivity", "Failed to fetch recipes: " + response.code() + " " + response.message());
                    Toast.makeText(getContext(), "Failed to fetch recipes from Spoonacular!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<DailyPlanSearchResponse> call, @NonNull Throwable t) {
                Log.e("RecipeActivity", "Network error: " + t.getMessage());
                Toast.makeText(getContext(), "Network error fetching recipes! Please try again later.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
