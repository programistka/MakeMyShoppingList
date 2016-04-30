package net.programistka.shoppingadvisor;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

import net.programistka.shoppingadvisor.dbhandlers.DbHandler;
import net.programistka.shoppingadvisor.dbhandlers.EmptyItemsDbHandler;
import net.programistka.shoppingadvisor.models.EmptyItem;

import java.util.List;

public class EmptyItemsDbHandlerTests extends AndroidTestCase {

    private EmptyItemsDbHandler dbHandler;

    public void setUp() throws Exception {
        dbHandler = new EmptyItemsDbHandler(mContext, "shopping_advisor_test.db");
        mContext.openOrCreateDatabase(dbHandler.getDatabaseName(), Context.MODE_PRIVATE, null);
    }

    public void tearDown() throws Exception {
        mContext.deleteDatabase(dbHandler.getDatabaseName());
    }

    public void testWhenAddNewEmptyItemThenNewItemAddedToEmptyItemsTable() {
        dbHandler.insertNewEmptyItem("Proszek do prania");
        List<EmptyItem> emptyItemsList = dbHandler.selectAllItemsFromItemsTable();
        assertEquals(1, emptyItemsList.size());
        assertEquals("Proszek do prania", emptyItemsList.get(0).getName());
    }

    public void testWhenAddNewEmptyItemThenNewItemAddedToEmptyItemsHistoryTable() {
        dbHandler.insertNewEmptyItem("Proszek do prania");
        List<EmptyItem> emptyItemsList = dbHandler.selectAllItemsFromEmptyItemsHistoryTable();
        assertEquals(1, emptyItemsList.size());
        assertEquals("Proszek do prania", emptyItemsList.get(0).getName());
    }
}
