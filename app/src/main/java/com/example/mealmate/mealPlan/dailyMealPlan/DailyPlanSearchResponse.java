package com.example.mealmate.mealPlan.dailyMealPlan;

import com.example.mealmate.shoppingCart.groceryList.GroceryListBean;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DailyPlanSearchResponse {

    @SerializedName("meals")
    private List<DailyPlanBean> items;

    public List<DailyPlanBean> getItems() {
        return items;
    }

    public void setItems(List<DailyPlanBean> items) {
        this.items = items;
    }

    // Optionally, you can create a method to get grocery items directly
    public List<DailyPlanBean> getGrocery() {
        return items;
    }
}
