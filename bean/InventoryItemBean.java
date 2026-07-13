package com.foodbank.model;

/**
 * JavaBean representing a joined FoodItem + Inventory row, used to display
 * the inventory table on the admin dashboard.
 * NOTE: FoodItem and Inventory tables are owned by Lily's module; this bean
 * only reads from them for display purposes on Ainaa's dashboard.
 * Author: Ainaa - Food Booking, Notification & Dashboard Module
 */
public class InventoryItemBean {

    private int inventoryId;
    private int foodItemId;
    private String itemName;
    private String category;
    private String status;       // e.g. AVAILABLE, LOW_STOCK, EXPIRED, DISTRIBUTED
    private int quantity;
    private String expiryDate;   // yyyy-MM-dd

    public InventoryItemBean() {
    }

    public int getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(int inventoryId) {
        this.inventoryId = inventoryId;
    }

    public int getFoodItemId() {
        return foodItemId;
    }

    public void setFoodItemId(int foodItemId) {
        this.foodItemId = foodItemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }
}
