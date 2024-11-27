package com.example.mealmate.shoppingCart.groceryList;

import com.example.mealmate.recipe.ingredients.Ingredient;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GrocerySearchResponse {

    @SerializedName("aisles")
    private List<GroceryItem> items;

    public List<GroceryItem> getGrocery(){
        return items;
    }

    // Define the structure of individual grocery items
    public static class GroceryItem {
        private String name;
        private double amount;
        private String unit;

        // Getters and setters
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }
    }

    public List<GroceryItem> getItems() {
        return items;
    }

    public void setItems(List<GroceryItem> items) {
        this.items = items;
    }
}
