package com.programistka.makemyshoppinglist.dbhandlers;

import com.google.firebase.database.DatabaseReference;

public interface DatabaseConfig {

    public DatabaseReference getItems();

    public DatabaseReference getHistory();

    public DatabaseReference getPredictions();
}
