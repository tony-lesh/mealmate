//package com.example.mealmate.recipe.single_recipe_view_folder;
//
//import com.example.mealmate.recipe.RecipeBean;
//import com.google.gson.annotations.SerializedName;
//
//import java.util.List;
//
//public class SingleRecipeBean {
//
//    @SerializedName("id")
//    private String id;
//    @SerializedName("title")
//    private String title;
//    @SerializedName("extendedIngredients")
//    private String ingredients;
//    @SerializedName("instructions")
//    private String instructions;
//    @SerializedName("image")
//    private String imageURL;
//    @SerializedName("price")
//    private String price;
//
//    public SingleRecipeBean() {
//    }
//
//    public SingleRecipeBean(String id, String title, String ingredients, String instructions, String imageURL, String price) {
//        this.id = id;
//        this.title = title;
//        this.ingredients = ingredients;
//        this.instructions = instructions;
//        this.imageURL = imageURL;
//        this.price = price;
//    }
//
//    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }
//
//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    public String getIngredients() {
//        return ingredients;
//    }
//
//    public void setIngredients(String ingredients) {
//        this.ingredients = ingredients;
//    }
//
//    public String getInstructions() {
//        return instructions;
//    }
//
//    public void setInstructions(String instructions) {
//        this.instructions = instructions;
//    }
//
//    public String getImageURL() {
//        return imageURL;
//    }
//
//    public void setImageURL(String imageURL) {
//        this.imageURL = imageURL;
//    }
//
//    public String getPrice() {
//        return price;
//    }
//
//    public void setPrice(String price) {
//        this.price = price;
//    }
//
//    @Override
//    public String toString() {
//        return "SingleRecipeBean{" +
//                "id='" + id + '\'' +
//                ", title='" + title + '\'' +
//                ", ingredients='" + ingredients + '\'' +
//                ", instructions='" + instructions + '\'' +
//                ", imageURL='" + imageURL + '\'' +
//                ", price='" + price + '\'' +
//                '}';
//    }
//}

package com.example.mealmate.recipe.ingredients;

import com.example.mealmate.recipe.instructions.Instruction;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SingleRecipeBean {

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

//    @SerializedName("price")
//    private double price;  // Changed to double for price

    public SingleRecipeBean() {}

    public SingleRecipeBean(int id, String title, List<Ingredient> ingredients, List<Instruction> instructions, String imageURL) {
        this.id = id;
        this.title = title;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.imageURL = imageURL;
      //  this.price = price;
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

    @Override
    public String toString() {
        return "SingleRecipeBean{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", ingredients=" + ingredients +  // Now this will show the list of ingredients
                ", instructions='" + instructions + '\'' +
                ", imageURL='" + imageURL +
                '}';
    }
}

