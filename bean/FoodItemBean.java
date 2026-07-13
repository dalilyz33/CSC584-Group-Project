package com.careshare.bean;

public class FoodItemBean {
    private String foodItemID;
    private String foodItemName;
    private String category;
    private String foodItemType;
    private String description;
   
    public FoodItemBean() {
    }

    public FoodItemBean(String foodItemID, String foodItemName, String category, 
            String foodItemType, String description) {
        this.foodItemID = foodItemID;
        this.foodItemName = foodItemName;
        this.category = category;
        this.foodItemType = foodItemType;
        this.description = description;
    }

    public String getFoodItemID() {
        return foodItemID;
    }

    public void setFoodItemID(String foodItemID) {
        this.foodItemID = foodItemID;
    }

    public String getFoodItemName() {
        return foodItemName;
    }

    public void setFoodItemName(String foodItemName) {
        this.foodItemName = foodItemName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getFoodItemType() {
        return foodItemType;
    }

    public void setFoodItemType(String foodItemType) {
        this.foodItemType = foodItemType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
}
