package com.programistka.makemyshoppinglist.start;

import android.content.Context;

import com.programistka.makemyshoppinglist.dbhandlers.DbHandler;
import com.programistka.makemyshoppinglist.presenters.DbConfig;

public class StartInteractor {
    private DbHandler dbHandler;

    public StartInteractor(DbConfig dbConfig, Context context) {
        dbHandler = new DbHandler(dbConfig, context);
    }

    public Boolean ifAnyItemsExists(){
        return dbHandler.selectAllItemsFromItemsTable().size() > 0;
    }
}
