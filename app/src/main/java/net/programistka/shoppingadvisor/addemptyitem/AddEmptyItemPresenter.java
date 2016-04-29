package net.programistka.shoppingadvisor.addemptyitem;

import android.content.Context;

import net.programistka.shoppingadvisor.dbhandlers.EmptyItemsDbHandler;
import net.programistka.shoppingadvisor.models.EmptyItem;

import java.util.List;

public class AddEmptyItemPresenter {
    private EmptyItemsDbHandler dbHandler;

    public AddEmptyItemPresenter(Context context) {
        this.dbHandler = new EmptyItemsDbHandler(context);
    }

    public List<EmptyItem> selectAllItemsFromItemsTable() {
        return this.dbHandler.selectAllItemsFromItemsTable();
    }

    public void insertExistingEmptyItem(long id) {
        this.dbHandler.insertExistingEmptyItem(id);
    }

    public void insertNewEmptyItem(String name) {
        this.dbHandler.insertNewEmptyItem(name);
    }

    public List<EmptyItem> selectAllItemsFromEmptyItemsHistoryTable() {
        return this.dbHandler.selectAllItemsFromEmptyItemsHistoryTable();
    }
}
