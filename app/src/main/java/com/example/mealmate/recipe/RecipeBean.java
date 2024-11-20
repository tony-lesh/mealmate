package com.example.mealmate.recipe;

import android.graphics.drawable.Icon;
import android.widget.ImageView;

import com.google.gson.annotations.SerializedName;

public class RecipeBean {
    @SerializedName("id")
    private String id;
    @SerializedName("title")
    private String title;
    @SerializedName("image")
    private String imageURL;
    @SerializedName("ingredients")
    private String ingredients;
    @SerializedName("instructions")
    private String instructions;

    public RecipeBean() {
    }

    public RecipeBean(String id, String title, String imageURL, String ingredients, String instructions) {
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
        return "RecipeBean{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", image=" + imageURL +
                ", ingredients='" + ingredients + '\'' +
                ", instructions='" + instructions + '\'' +
                '}';
    }
}
