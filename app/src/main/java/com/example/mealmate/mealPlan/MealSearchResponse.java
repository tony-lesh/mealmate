package com.example.mealmate.mealPlan;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MealSearchResponse {
    @SerializedName("results")
    private List<Meal> meal_recipe_view;

    public List<Meal> getMealRecipe() {
        return meal_recipe_view;
    }

    public static class Result{

        @SerializedName("title")
        public String name;
        @SerializedName("image")
        public String imageUrl;


        public Result() {
        }

        public Result(String name, String imageUrl) {
            this.name = name;
            this.imageUrl = imageUrl;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        @Override
        public String toString() {
            return "Result{" +
                    "name='" + name + '\'' +
                    ", imageUrl='" + imageUrl + '\'' +
                    '}';
        }
    }

}
