package com.example.mealmate.mealPlan.dailyMealPlan;

import android.content.ClipData;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DailyPlanBean {

    @SerializedName("id")
    public int id;
    @SerializedName("title")
    public String title;
    @SerializedName("readyInMinutes")
    public int readyInMinutes;
    @SerializedName("servings")
    public int servings;
    @SerializedName("sourceUrl")
    public String sourceUrl;

    public DailyPlanBean() {
    }

    public DailyPlanBean(int id, String title, int readyInMinutes, int servings, String sourceUrl) {
        this.id = id;
        this.title = title;
        this.readyInMinutes = readyInMinutes;
        this.servings = servings;
        this.sourceUrl = sourceUrl;
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

    public int getReadyInMinutes() {
        return readyInMinutes;
    }

    public void setReadyInMinutes(int readyInMinutes) {
        this.readyInMinutes = readyInMinutes;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    @Override
    public String toString() {
        return "DailyPlanBean{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", readyInMinutes=" + readyInMinutes +
                ", servings=" + servings +
                ", sourceUrl='" + sourceUrl + '\'' +
                '}';
    }
}


