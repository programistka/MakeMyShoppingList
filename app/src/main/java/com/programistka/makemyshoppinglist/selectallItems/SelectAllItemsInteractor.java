package com.programistka.makemyshoppinglist.selectallItems;

import android.content.Context;

import com.programistka.makemyshoppinglist.dbhandlers.DbHandler;
import com.programistka.makemyshoppinglist.dbhandlers.EmptyItemsDbHandler;
import com.programistka.makemyshoppinglist.models.EmptyItem;
import com.programistka.makemyshoppinglist.presenters.DbConfig;

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
