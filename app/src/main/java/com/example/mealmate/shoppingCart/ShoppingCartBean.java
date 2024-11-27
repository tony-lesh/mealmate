package com.example.mealmate.shoppingCart;

public class ShoppingCartBean {
    private int recipeId;
    private String recipeName;
    private String recipeImage;

    public ShoppingCartBean() {
    }

    public ShoppingCartBean(int recipeId, String recipeName, String recipeImage) {
        this.recipeId = recipeId;
        this.recipeName = recipeName;
        this.recipeImage = recipeImage;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public void setRecipeImage(String recipeImage) {
        this.recipeImage = recipeImage;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public String getRecipeImage() {
        return recipeImage;
    }
}
