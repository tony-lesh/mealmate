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
    private ImageView image;
    @SerializedName("ingredients")
    private String ingredients;
    @SerializedName("instructions")
    private String instructions;

    public RecipeBean() {
    }

    public RecipeBean(String id, String title, ImageView image, String ingredients, String instructions) {
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
        return "RecipeBean{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", image=" + image +
                ", ingredients='" + ingredients + '\'' +
                ", instructions='" + instructions + '\'' +
                '}';
    }
}
