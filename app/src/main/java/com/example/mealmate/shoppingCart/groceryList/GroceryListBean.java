package com.example.mealmate.shoppingCart.groceryList;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class GroceryListBean {

    @SerializedName("items")
    private List<GrocerySearchResponse.GroceryItem> items;  // Update the type to List<GroceryItem>

    private int servings;

    public GroceryListBean() {}

    public GroceryListBean(List<GrocerySearchResponse.GroceryItem> items, int servings) {
        this.items = items;
        this.servings = servings;
    }

    public List<GrocerySearchResponse.GroceryItem> getItems() {
        return items;
    }

    public void setItems(List<GrocerySearchResponse.GroceryItem> items) {
        this.items = items;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    @Override
    public String toString() {
        return "GroceryListBean{" +
                "items=" + items +
                ", servings=" + servings +
                '}';
    }
}
