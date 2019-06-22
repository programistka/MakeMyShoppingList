package com.programistka.makemyshoppinglist.models;

public class EmptyItemNew {
    private String itemId;
    private String itemName;
    private int daysToRunOut;
    private long nextEmptyItemDate;

    public EmptyItemNew() { }

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

    public int getDaysToRunOut() {
        return daysToRunOut;
    }

    public void setDaysToRunOut(int daysToRunOut) {
        this.daysToRunOut = daysToRunOut;
    }

    public long getNextEmptyItemDate() {
        return nextEmptyItemDate;
    }

    public void setNextEmptyItemDate(long nextEmptyItemDate) {
        this.nextEmptyItemDate = nextEmptyItemDate;
    }
}
