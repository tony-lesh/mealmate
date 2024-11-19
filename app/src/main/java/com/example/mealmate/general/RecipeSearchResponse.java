package com.example.mealmate.general;

import android.widget.ImageView;

import com.example.mealmate.recipe.RecipeBean;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RecipeSearchResponse {
        @SerializedName("results")
        private List<RecipeBean> results;

    // ... other fields as needed

        public List<RecipeBean> getResults() {
            return results;
        }

        public static class Result{
            @SerializedName("id")
            private String id;
            @SerializedName("title")
            private String title;
            @SerializedName("image")
            private ImageView image;
            @SerializedName("ingredients")
            private String ingredients;
            @SerializedName("instructions")
            private String instructions;

            public Result() {
            }

            public Result(String id, String title, ImageView image, String ingredients, String instructions) {
                this.id = id;
                this.title = title;
                this.image = image;
                this.ingredients = ingredients;
                this.instructions = instructions;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public ImageView getImage() {
                return image;
            }

            public void setImage(ImageView image) {
                this.image = image;
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
                        ", image=" + image +
                        ", ingredients='" + ingredients + '\'' +
                        ", instructions='" + instructions + '\'' +
                        '}';
            }
        }


    }

