package net.programistka.shoppingadvisor.presenters;

import android.content.Context;

import net.programistka.shoppingadvisor.dbhandlers.EmptyItemsDbHandler;
import net.programistka.shoppingadvisor.models.EmptyItem;

import java.util.List;

public class EmptyItemsPresenter {
    private EmptyItemsDbHandler dbHandler;

    public EmptyItemsPresenter(Context context) {
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
