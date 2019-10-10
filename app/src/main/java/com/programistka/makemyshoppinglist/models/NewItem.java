package com.programistka.makemyshoppinglist.models;

public class NewItem {
    private String id;
    private String name;
    private long nextEmptyItemDate;
    private boolean isArchived = false;

    public NewItem() {}

    public NewItem(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getNextEmptyItemDate() {
        return nextEmptyItemDate;
    }

    public void setNextEmptyItemDate(long nextEmptyItemDate) {
        this.nextEmptyItemDate = nextEmptyItemDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isArchived() {
        return isArchived;
    }

    public void setArchived(boolean archived) {
        isArchived = archived;
    }
}
