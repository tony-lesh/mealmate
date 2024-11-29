package com.example.mealmate.mealPlan;

import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.mealmate.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MealPlanActivity extends AppCompatActivity {

    private ViewPager2 vPager;
    private TabLayout tabLayout;
    private MealViewPageAdapter vPageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_meal_plan);
        vPager =findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);
        vPageAdapter = new MealViewPageAdapter(this);
        vPager.setAdapter(vPageAdapter);

        Toolbar tBar = findViewById(R.id.toolBar);
        setSupportActionBar(tBar);

        new TabLayoutMediator(tabLayout,vPager,(tab, position)-> {
            switch (position){
                case 0:
                    tab.setText("Daily Meal Plan");
                    tab.setIcon(R.drawable.daily_meal_plan);
                    break;
                case 1:
                    tab.setText("Weekly Meal Plan");
                    tab.setIcon(R.drawable.weekly_meal_plan);
                    break;
            }
        }).attach();

    }
}