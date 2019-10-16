package com.programistka.makemyshoppinglist.dbhandlers;

public class DatabaseConfigTest {
    private String items;
    private String history;
    private String predictions;

    public DatabaseConfigTest() {
        items = "items-test";
        history = "history-test";
        predictions = "predictions-test";
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
