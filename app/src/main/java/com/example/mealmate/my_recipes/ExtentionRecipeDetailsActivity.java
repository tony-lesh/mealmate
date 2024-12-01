package com.example.mealmate.my_recipes;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import com.example.mealmate.R;
import com.example.mealmate.mealPlan.MealViewPageAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class ExtentionRecipeDetailsActivity extends AppCompatActivity {

    private ViewPager2 vPager;
    private TabLayout tabLayout;
    private MyRecipesViewPageAdapter vPageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.instructions_ingredients_main);
        vPager =findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);
        vPageAdapter = new MyRecipesViewPageAdapter(this);
        vPager.setAdapter(vPageAdapter);

        Toolbar tBar = findViewById(R.id.toolBar);
        setSupportActionBar(tBar);

        new TabLayoutMediator(tabLayout,vPager,(tab, position)-> {
            switch (position){
                case 0:
                    tab.setText("Ingredients");
                    tab.setIcon(R.drawable.daily_meal_plan);
                    break;
                case 1:
                    tab.setText("Instructions");
                    tab.setIcon(R.drawable.weekly_meal_plan);
                    break;
            }
        }).attach();

    }
}
