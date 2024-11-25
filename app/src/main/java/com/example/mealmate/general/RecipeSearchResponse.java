package com.example.mealmate.general;

import com.example.mealmate.recipe.RecipeBean;
import com.example.mealmate.registration_signin.HomeBean;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RecipeSearchResponse {
        @SerializedName("results")
        private List<RecipeBean> results;

        public List<RecipeBean> getResults() {
            return results;
        }

        public static class Result{
            @SerializedName("id")
            private int id;
            @SerializedName("title")
            private String title;
            @SerializedName("image")
            private String imageURL;
            @SerializedName("ingredients")
            private String ingredients;
            @SerializedName("instructions")
            private String instructions;

            public Result() {
            }

            public Result(int id, String title, String imageURL, String ingredients, String instructions) {
                this.id = id;
                this.title = title;
                this.imageURL = imageURL;
                this.ingredients = ingredients;
                this.instructions = instructions;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getImageURL() {
                return imageURL;
            }

            public void setImageURL(String image) {
                this.imageURL = imageURL;
            }

            public String getIngredients() {
                return ingredients;
            }

            public void setIngredients(String ingredients) {
                this.ingredients = ingredients;
            }

            public String getInstructions() {
                return instructions;
            }

            public void setInstructions(String instructions) {
                this.instructions = instructions;
            }

            @Override
            public String toString() {
                return "Result{" +
                        "id='" + id + '\'' +
                        ", title='" + title + '\'' +
                        ", image=" + imageURL +
                        ", ingredients='" + ingredients + '\'' +
                        ", instructions='" + instructions + '\'' +
                        '}';
            }
        }


    }

