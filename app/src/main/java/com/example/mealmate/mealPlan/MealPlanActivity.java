package com.example.mealmate.mealPlan;


import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealmate.R;

import java.util.ArrayList;
import java.util.List;

public class MealPlanActivity extends AppCompatActivity {

    private LinearLayout weekIconsLayout;
    private TextView dayTextView;
    private ImageButton previousBtn, nextBtn;
    private FrameLayout foodFrameLayout;

    // Data structures for meal plans (replace with actual data)
    private List<Meal> breakfastList = new ArrayList<>();
    private List<Meal> lunchList = new ArrayList<>();
    private List<Meal> supperList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_plan);

        weekIconsLayout = findViewById(R.id.weekIcons);
        dayTextView = findViewById(R.id.dayTextView);
        previousBtn = findViewById(R.id.previousBtn);
        nextBtn = findViewById(R.id.nextBtn);
        foodFrameLayout = findViewById(R.id.foodFrameLayout);

        // Sample data for meals (replace with actual data fetching)
        breakfastList.add(new Meal("Oatmeal with berries", "Image URL"));
        lunchList.add(new Meal("Chicken salad sandwich", "Image URL"));
        supperList.add(new Meal("Salmon with roasted vegetables", "Image URL"));

        // Set up initial day and food display
        updateDayAndFoodDisplay();

        // Handle button clicks for navigation (optional)
        previousBtn.setOnClickListener(v -> goToPreviousDay());
        nextBtn.setOnClickListener(v -> goToNextDay());
    }

    private void updateDayAndFoodDisplay() {
        // Update day text based on current date (or user selection)
        dayTextView.setText("Today");

        // Set up breakfast adapter and display
        BreakfastAdapter breakfastAdapter = new BreakfastAdapter(breakfastList, this);
        setUpScrollableFoodLayout(breakfastAdapter, R.id.breakfastTextView, R.id.breakfastScrollView);

        // Set up lunch adapter and display
        LunchAdapter lunchAdapter = new LunchAdapter(lunchList, this);
        setUpScrollableFoodLayout(lunchAdapter, R.id.lunchTextView, R.id.lunchScrollView);

        // Set up supper adapter and display
        SupperAdapter supperAdapter = new SupperAdapter(supperList, this);
        setUpScrollableFoodLayout(supperAdapter, R.id.supperTextView, R.id.supperScrollView);
    }

    private void setUpScrollableFoodLayout(RecyclerView.Adapter adapter, int textViewId, int scrollViewId) {
        LinearLayout foodLayout = findViewById(textViewId).findViewById(R.id.iconsLayout);

        // Adjust layout weight for icons based on screen size
        if (isLargeScreen()) {
            foodLayout.setWeightSum(1.0f);
        } else {
            foodLayout.setWeightSum(0.4f);
        }

        RecyclerView recyclerView = new RecyclerView(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // Add RecyclerView to ScrollView
        ((ViewGroup) findViewById(scrollViewId)).addView(recyclerView);
    }

    // Helper methods for day navigation (optional)
    private void goToPreviousDay() {
        // Update day and food display based on previous day logic
        updateDayAndFoodDisplay();
    }

    private void goToNextDay() {
        // Update day and food display based on next day logic
        updateDayAndFoodDisplay();
    }

    // Helper method to check screen size (optional)
    private boolean isLargeScreen() {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        // Adjust width threshold based on your needs
        return width > 1000;
    }
}
