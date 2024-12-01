package com.example.mealmate.my_recipes;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.mealmate.mealPlan.dailyMealPlan.DailyPlanFragment;
import com.example.mealmate.mealPlan.weeklyMealPlan.WeeklyPlanFragment;
import com.example.mealmate.my_recipes.ingredient_display.IngredientFragment;
import com.example.mealmate.my_recipes.instruction_display.InstructionFragment;

public class MyRecipesViewPageAdapter extends FragmentStateAdapter {

    public MyRecipesViewPageAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch (position){
            case 0:
                return new IngredientFragment();

            case 1:
                return new InstructionFragment();

            default:
                return new IngredientFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
