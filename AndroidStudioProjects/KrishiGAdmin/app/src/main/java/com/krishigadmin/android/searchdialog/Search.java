package com.krishigadmin.android.searchdialog;

public class Search {
    private String itemId;
    private String itemName;

    public Search(String itemName) {
        this.itemName = itemName;
    }

    public Search(String itemId, String itemName) {
        this.itemId = itemId;
        this.itemName = itemName;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
}
