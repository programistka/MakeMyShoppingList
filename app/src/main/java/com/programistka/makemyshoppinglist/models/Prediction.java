package com.programistka.makemyshoppinglist.models;

public class Prediction {
    private long time;
    private long daysNumber; //why long?

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long getDaysNumber() {
        return daysNumber;
    }

    public void setDaysNumber(long daysNumber) {
        this.daysNumber = daysNumber;
    }
}
