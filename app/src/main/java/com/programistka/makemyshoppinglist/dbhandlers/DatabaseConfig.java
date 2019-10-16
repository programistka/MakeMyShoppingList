package com.programistka.makemyshoppinglist.dbhandlers;

public class DatabaseConfig {
    private String items;
    private String history;
    private String predictions;

    public DatabaseConfig() {
        items = "items";
        history = "history";
        predictions = "predictions";
    }

    public String getItems() {
        return items;
    }

    public String getHistory() {
        return history;
    }

    public String getPredictions() {
        return predictions;
    }
}
