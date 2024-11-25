package com.example.mealmate.recipe.ingredients;

import com.example.mealmate.recipe.instructions.Instruction;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class IngredientSearchResponse {
    @SerializedName("extendedIngredients")
    private List<Ingredient> results;

    public List<Ingredient> getIngredients(){
        return results;
    }

    public static class Result{
        @SerializedName("id")
        private int id;

        @SerializedName("title")
        private String title;

        @SerializedName("extendedIngredients")
        private List<Ingredient> ingredients;  // Changed to List<Ingredient>

        @SerializedName("analyzedInstructions")
        private List<Instruction> instructions;

        @SerializedName("image")
        private String imageURL;

//        @SerializedName("price")
//        private double price;  // Changed to double for price
        public Result() {
        }

        public Result(int id, String title, List<Ingredient> ingredients, List<Instruction> instructions, String imageURL) {
            this.id = id;
            this.title = title;
            this.ingredients = ingredients;
            this.instructions = instructions;
            this.imageURL = imageURL;
           // this.price = price;
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

        public List<Ingredient> getIngredients() {
            return ingredients;
        }

        public void setIngredients(List<Ingredient> ingredients) {
            this.ingredients = ingredients;
        }

        public List<Instruction> getInstructions() {
            return instructions;
        }

        public void setInstructions(List<Instruction> instructions) {
            this.instructions = instructions;
        }

        public String getImageURL() {
            return imageURL;
        }

        public void setImageURL(String imageURL) {
            this.imageURL = imageURL;
        }

//        public double getPrice() {
//            return price;
//        }
//
//        public void setPrice(double price) {
//            this.price = price;
//        }

        @Override
        public String toString() {
            return "Result{" +
                    "id='" + id + '\'' +
                    ", title='" + title + '\'' +
                    ", ingredients=" + ingredients +
                    ", instructions=" + instructions +
                    ", imageURL='" + imageURL +
                    '}';
        }
    }
}
