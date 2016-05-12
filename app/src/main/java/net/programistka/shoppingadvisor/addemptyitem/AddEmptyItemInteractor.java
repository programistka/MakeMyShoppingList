package net.programistka.shoppingadvisor.addemptyitem;

import android.content.Context;

import net.programistka.shoppingadvisor.CalendarProvider;
import net.programistka.shoppingadvisor.dbhandlers.EmptyItemsDbHandler;
import net.programistka.shoppingadvisor.models.EmptyItem;
import net.programistka.shoppingadvisor.presenters.DbConfig;

import java.util.Calendar;
import java.util.List;

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


}
