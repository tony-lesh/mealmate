package com.example.mealmate.recipe;

import android.graphics.drawable.Icon;
import android.widget.ImageView;

import com.google.gson.annotations.SerializedName;

public class RecipeBean {
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
    @SerializedName("description")
    private String description;


    public RecipeBean() {
    }

    public RecipeBean(int id, String title, String imageURL, String ingredients, String instructions,String description) {
        this.id = id;
        this.title = title;
        this.imageURL = imageURL;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "RecipeBean{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", imageURL='" + imageURL + '\'' +
                ", ingredients='" + ingredients + '\'' +
                ", instructions='" + instructions + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
