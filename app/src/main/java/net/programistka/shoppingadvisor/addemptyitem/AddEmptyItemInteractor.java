package net.programistka.shoppingadvisor.addemptyitem;

import android.content.Context;

import net.programistka.shoppingadvisor.dbhandlers.EmptyItemsDbHandler;
import net.programistka.shoppingadvisor.models.EmptyItem;

import java.util.List;

public class AddEmptyItemInteractor {

    private EmptyItemsDbHandler dbHandler;

    public AddEmptyItemInteractor(Context context) {
        dbHandler = new EmptyItemsDbHandler(context);
    }

    public void insertNewEmptyItem(String name, Long time) {
        dbHandler.insertNewEmptyItem(name, time);
    }

    public void insertExistingEmptyItem(long id, long time) {
        dbHandler.insertExistingEmptyItem(id, time);
    }
}
