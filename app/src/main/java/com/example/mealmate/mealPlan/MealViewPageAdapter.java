package com.example.mealmate.mealPlan;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.mealmate.mealPlan.dailyMealPlan.DailyPlanFragment;
import com.example.mealmate.mealPlan.weeklyMealPlan.WeeklyPlanFragment;

public class MealViewPageAdapter extends FragmentStateAdapter {

    public MealViewPageAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch (position){
            case 0:
                return new DailyPlanFragment();

            case 1:
                return new WeeklyPlanFragment();

            default:
                return new DailyPlanFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
