package com.careshare.bean;

import java.io.Serializable;

public class NotificationCategoryBean implements Serializable {
    private int categoryId;
    private String categoryName;

    public NotificationCategoryBean() {}

    public int getCategoryId() { return categoryId; }
    public void setCategoryId(int categoryId) { this.categoryId = categoryId; }
    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }
}
