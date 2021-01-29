package com.example.foodapiapp;

public class Food {
    private String foodName;
    private String foodImage;
    private String foodId;
    private String foodDesc;

    Food(){}

    public Food(String foodName, String foodImage, String foodId, String foodDesc) {
        this.foodName = foodName;
        this.foodImage = foodImage;
        this.foodId = foodId;
        this.foodDesc = foodDesc;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getFoodImage() {
        return foodImage;
    }

    public void setFoodImage(String foodImage) {
        this.foodImage = foodImage;
    }

    public String getFoodId() {
        return foodId;
    }

    public void setFoodId(String foodId) {
        this.foodId = foodId;
    }

    public String getFoodDesc() {
        return foodDesc;
    }

    public void setFoodDesc(String foodDesc) {
        this.foodDesc = foodDesc;
    }
}
