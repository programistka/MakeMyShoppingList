package com.programistka.makemyshoppinglist.models;

public class EmptyItemPredictionEntry {

    private String id;
    private long nextEmptyItemDate;
    private long daysToRunOut;

    public EmptyItemPredictionEntry() {}

    public EmptyItemPredictionEntry(String id, long nextEmptyItemDate, long daysToRunOut) {
        this.id = id;
        this.nextEmptyItemDate = nextEmptyItemDate;
        this.daysToRunOut = daysToRunOut;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getNextEmptyItemDate() {
        return nextEmptyItemDate;
    }

    public void setNextEmptyItemDate(long nextEmptyItemDate) {
        this.nextEmptyItemDate = nextEmptyItemDate;
    }

    public long getDaysToRunOut() {
        return daysToRunOut;
    }

    public void setDaysToRunOut(long daysToRunOut) {
        this.daysToRunOut = daysToRunOut;
    }
}
