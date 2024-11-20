package com.example.mealmate.registration_signin;

import com.google.gson.annotations.SerializedName;

public class HomeBean {

    @SerializedName("id")
    private String id;
    @SerializedName("title")
    private String title;
    @SerializedName("image")
    private String imageURL;
    @SerializedName("description")
    private String description;

    public HomeBean() {
    }

    public HomeBean(String id, String title, String imageURL, String description) {
        this.id = id;
        this.title = title;
        this.imageURL = imageURL;
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "HomeBean{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", imageURL='" + imageURL + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
