package com.programistka.makemyshoppinglist.addemptyitem;

import android.content.Context;

import com.programistka.makemyshoppinglist.dbhandlers.FirebaseDbHandler;
import com.programistka.makemyshoppinglist.presenters.DbConfig;

public class AddEmptyItemInteractor {

    private FirebaseDbHandler firebaseDbHandler;

    public AddEmptyItemInteractor(DbConfig dbConfig, Context context) {
        firebaseDbHandler = new FirebaseDbHandler();
    }

    void addEmptyItem(AddEmptyItemActivity addEmptyItemActivity, String toString, long timeInMillis) {
        firebaseDbHandler.addEmptyItem(addEmptyItemActivity, toString, timeInMillis);
    }
}
