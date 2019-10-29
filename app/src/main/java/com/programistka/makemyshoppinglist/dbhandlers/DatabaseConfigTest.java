package com.programistka.makemyshoppinglist.dbhandlers;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DatabaseConfigTest implements DatabaseConfig {
    private String items;
    private String history;
    private String predictions;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

    private DatabaseReference itemsStorageReference;
    private DatabaseReference emptyItemsHistoryStorageReference;
    private DatabaseReference emptyItemsPredictionsStorageReference;

    public DatabaseConfigTest() {
        items = "items-test";
        history = "history-test";
        predictions = "predictions-test";
        itemsStorageReference = firebaseDatabase.getReference().child(items);
        emptyItemsHistoryStorageReference = firebaseDatabase.getReference().child(history);
        emptyItemsPredictionsStorageReference = firebaseDatabase.getReference().child(predictions);
    }

    public DatabaseReference getItems() {
        return itemsStorageReference;
    }

    public DatabaseReference getHistory() {
        return emptyItemsHistoryStorageReference;
    }

    public DatabaseReference getPredictions() {
        return emptyItemsPredictionsStorageReference;
    }
}
