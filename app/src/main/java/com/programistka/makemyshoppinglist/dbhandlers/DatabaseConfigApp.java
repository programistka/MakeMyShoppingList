package com.programistka.makemyshoppinglist.dbhandlers;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DatabaseConfigApp implements DatabaseConfig {
    private String items;
    private String history;
    private String predictions;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

    private DatabaseReference itemsStorageReference;
    private DatabaseReference emptyItemsHistoryStorageReference;
    private DatabaseReference emptyItemsPredictionsStorageReference;

    public DatabaseConfigApp() {
        items = "items";
        history = "history";
        predictions = "predictions";
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
