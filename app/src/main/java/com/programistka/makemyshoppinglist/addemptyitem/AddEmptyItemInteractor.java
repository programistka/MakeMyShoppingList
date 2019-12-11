package com.programistka.makemyshoppinglist.addemptyitem;

import com.programistka.makemyshoppinglist.dbhandlers.DatabaseConfigApp;
import com.programistka.makemyshoppinglist.dbhandlers.FirebaseDbHandler;

public class AddEmptyItemInteractor {

    private FirebaseDbHandler firebaseDbHandler;

    public AddEmptyItemInteractor(DatabaseConfigApp databaseConfig) {
        firebaseDbHandler = FirebaseDbHandler.init(databaseConfig);
    }

    void addEmptyItem(AddEmptyItemActivity addEmptyItemActivity, String toString, long timeInMillis) {
        firebaseDbHandler.addEmptyItem2(addEmptyItemActivity, toString, timeInMillis);
    }
}
