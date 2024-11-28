package com.example.mealmate.shoppingCart.groceryList;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GrocerySearchResponse {

    @SerializedName("ingredients")
    private List<GroceryListBean> items;

    public List<GroceryListBean> getItems() {
        return items;
    }

    public void setItems(List<GroceryListBean> items) {
        this.items = items;
    }

    // Optionally, you can create a method to get grocery items directly
    public List<GroceryListBean> getGrocery() {
        return items;
    }
}
