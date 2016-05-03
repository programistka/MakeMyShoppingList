package net.programistka.shoppingadvisor.selectallItems;

import android.content.Context;

import net.programistka.shoppingadvisor.dbhandlers.DbHandler;
import net.programistka.shoppingadvisor.dbhandlers.EmptyItemsDbHandler;
import net.programistka.shoppingadvisor.models.EmptyItem;
import net.programistka.shoppingadvisor.presenters.DbConfig;

import java.util.List;

public class SelectAllItemsInteractor {

    private DbHandler dbHandler;

    public SelectAllItemsInteractor(DbConfig dbConfig, Context context) {
        dbHandler = new EmptyItemsDbHandler(dbConfig, context);
    }

    public List<EmptyItem> selectAllItemsFromEmptyItemsHistoryTable() {
        return dbHandler.selectAllItemsFromEmptyItemsHistoryTable();
    }

    public List<EmptyItem> selectAllItemsFromItemsTable() {
        return dbHandler.selectAllItemsFromItemsTable();
    }

    public List<EmptyItem> selectShoppingHistoryForItemFromItemsHistoryTable(Long id) {
        return dbHandler.selectAllItemsFromEmptyItemsHistoryTableByItemId(id);
    }
}
