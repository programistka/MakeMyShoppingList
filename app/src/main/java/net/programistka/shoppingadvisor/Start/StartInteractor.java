package net.programistka.shoppingadvisor.start;

import android.content.Context;

import net.programistka.shoppingadvisor.dbhandlers.DbHandler;
import net.programistka.shoppingadvisor.presenters.DbConfig;

public class StartInteractor {
    private DbHandler dbHandler;

    public StartInteractor(DbConfig dbConfig, Context context) {
        dbHandler = new DbHandler(dbConfig, context);
    }

    public Boolean ifAnyItemsExists(){
        return dbHandler.selectAllItemsFromItemsTable().size() > 0;
    }
}
