package com.programistka.makemyshoppinglist.addemptyitem;

import android.content.Context;

import com.programistka.makemyshoppinglist.dbhandlers.EmptyItemsDbHandler;
import com.programistka.makemyshoppinglist.presenters.DbConfig;

public class AddEmptyItemInteractor {

    private EmptyItemsDbHandler dbHandler;

    public AddEmptyItemInteractor(DbConfig dbConfig, Context context) {
        dbHandler = new EmptyItemsDbHandler(dbConfig, context);
    }

    long insertNewEmptyItem(String name, long time) {
        return dbHandler.insertNewEmptyItem(name, time);
    }

    void insertExistingEmptyItem(long id, long time) {
        dbHandler.insertExistingEmptyItem(id, time);
    }

    void insertNewEmptyItemWithHistoryAndPrediction(String name, long time1, long time2, int daysToRunOut) {
        dbHandler.insertNewEmptyItemWithHistoryAndPrediction(name, time1, time2, daysToRunOut);
    }
}
