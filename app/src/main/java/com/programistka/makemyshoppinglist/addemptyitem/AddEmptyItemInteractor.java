package com.programistka.makemyshoppinglist.addemptyitem;

import com.programistka.makemyshoppinglist.dbhandlers.DatabaseConfig;
import com.programistka.makemyshoppinglist.dbhandlers.FirebaseDbHandler;

public class AddEmptyItemInteractor {

    private FirebaseDbHandler firebaseDbHandler;

    public AddEmptyItemInteractor(DatabaseConfig databaseConfig) {
        firebaseDbHandler = new FirebaseDbHandler(databaseConfig);
    }

    void addEmptyItem(AddEmptyItemActivity addEmptyItemActivity, String toString, long timeInMillis) {
        firebaseDbHandler.addEmptyItem(addEmptyItemActivity, toString, timeInMillis);
    }
}
