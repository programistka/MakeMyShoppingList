package net.programistka.shoppingadvisor.models;

import java.util.Date;

public class EmptyItem {

    private int id;
    private String name;
    private Date creationDate;
    private Date predictionDate;

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

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getPredictionDate() { return this.predictionDate; }

    public void setPredictionDate(Date predictionDate) { this.predictionDate = predictionDate; }
}
