package com.careshare.bean;

import java.io.Serializable;

public class FoodItemBean implements Serializable {
    private int foodItemId;
    private String foodItemName;
    private String foodItemType;
    private String foodItemDescription;
    private int categoryId;

    public FoodItemBean() {}

    public int getFoodItemId() { return foodItemId; }
    public void setFoodItemId(int foodItemId) { this.foodItemId = foodItemId; }
    public String getFoodItemName() { return foodItemName; }
    public void setFoodItemName(String foodItemName) { this.foodItemName = foodItemName; }
    public String getFoodItemType() { return foodItemType; }
    public void setFoodItemType(String foodItemType) { this.foodItemType = foodItemType; }
    public String getFoodItemDescription() { return foodItemDescription; }
    public void setFoodItemDescription(String foodItemDescription) { this.foodItemDescription = foodItemDescription; }
    public int getCategoryId() { return categoryId; }
    public void setCategoryId(int categoryId) { this.categoryId = categoryId; }
}
