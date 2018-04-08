package com.programistka.makemyshoppinglist.models;

public class EmptyItem {

    private int id;
    private String name;
    private long creationDate;
    private long predictionDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(long creationDate) {
        this.creationDate = creationDate;
    }

    public long getPredictionDate() { return this.predictionDate; }

    public void setPredictionDate(long predictionDate) { this.predictionDate = predictionDate; }
}
