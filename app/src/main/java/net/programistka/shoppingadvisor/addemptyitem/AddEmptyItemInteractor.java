package net.programistka.shoppingadvisor.addemptyitem;

import android.content.Context;

import net.programistka.shoppingadvisor.dbhandlers.EmptyItemsDbHandler;
import net.programistka.shoppingadvisor.presenters.DbConfig;

public class AddEmptyItemInteractor {

    private EmptyItemsDbHandler dbHandler;

    public AddEmptyItemInteractor(DbConfig dbConfig, Context context) {
        dbHandler = new EmptyItemsDbHandler(dbConfig, context);
    }

    public long insertNewEmptyItem(String name, long time) {
        return dbHandler.insertNewEmptyItem(name, time);
    }

    public void insertExistingEmptyItem(long id, long time) {
        dbHandler.insertExistingEmptyItem(id, time);
    }

    public void insertNewEmptyItemAndPrediction(String name, long time, int daysToRunOut) {
        dbHandler.insertNewEmptyItemAndPrediction(name, time, daysToRunOut);
    }

    public void insertNewEmptyItemWithHistoryAndPrediction(String name, long time1, long time2, int daysToRunOut) {
        dbHandler.insertNewEmptyItemWithHistoryAndPrediction(name, time1, time2, daysToRunOut);
    }
}
