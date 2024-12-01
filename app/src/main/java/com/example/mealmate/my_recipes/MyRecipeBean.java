package com.example.mealmate.my_recipes;

import com.google.gson.annotations.SerializedName;

public class MyRecipeBean {


    private String id;
    private String title;
    private String imageURL;
    private String ingredients;
    private String instructions;

    public MyRecipeBean() {
    }

    public MyRecipeBean(String id, String title, String imageURL, String ingredients, String instructions) {
        this.id = id;
        this.title = title;
        this.imageURL = imageURL;
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

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
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
        return "MyRecipeBean{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", imageURL='" + imageURL + '\'' +
                ", ingredients='" + ingredients + '\'' +
                ", instructions='" + instructions + '\'' +
                '}';
    }
}
