package com.example.mealmate.mealPlan.dailyMealPlan;

import com.google.gson.annotations.SerializedName;

public class DailyRecipe {

        @SerializedName("name")
        private String name;

        @SerializedName("id")
        private int id;

    public DailyRecipe() {
    }

    // You can add other fields here based on the API response, like ingredients, etc.

        // Constructor
        public DailyRecipe(String name, int id) {
            this.name = name;
            this.id = id;
        }

        // Getters and Setters
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        @Override
        public String toString() {
            return "Recipe{" +
                    "name='" + name + '\'' +
                    ", id=" + id +
                    '}';
        }
}
