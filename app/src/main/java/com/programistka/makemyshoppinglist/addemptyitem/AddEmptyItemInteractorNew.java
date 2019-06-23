package com.programistka.makemyshoppinglist.addemptyitem;

import android.content.Context;

import com.programistka.makemyshoppinglist.dbhandlers.EmptyItemsDbHandler;
import com.programistka.makemyshoppinglist.presenters.DbConfig;

public class AddEmptyItemInteractorNew {

    private EmptyItemsDbHandler dbHandler;

    public AddEmptyItemInteractorNew(DbConfig dbConfig, Context context) {
        dbHandler = new EmptyItemsDbHandler(dbConfig, context);
    }

    public long insertNewEmptyItem(String name, long time) {
        return dbHandler.insertNewEmptyItem(name, time);
    }

    public void insertExistingEmptyItem(long id, long time) {
        dbHandler.insertExistingEmptyItem(id, time);
    }

    public void insertNewEmptyItemWithHistoryAndPrediction(String name, long time1, long time2, int daysToRunOut) {
        dbHandler.insertNewEmptyItemWithHistoryAndPrediction(name, time1, time2, daysToRunOut);
    }
}
