package net.programistka.shoppingadvisor;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

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
        dbHandler.getWritableDatabase();
        dbHandler.insertNewEmptyItem("Proszek do prania");
        List<EmptyItem> emptyItemsList = dbHandler.selectAllItemsFromItemsTable();
        assertEquals(1, emptyItemsList.size());
    }

    /*public void testCreateDB() {
        EmptyItemsDbHandler dbHandler = new EmptyItemsDbHandler(mContext, "shopping_advisor_test.db");
        SQLiteDatabase db = dbHandler.getWritableDatabase();
        assertTrue(db.isOpen());
        db.close();
    }*/

//    public void setUp() throws Exception {
//        dbHandler = new EmptyItemsDbHandler(mContext);
//        mContext.deleteDatabase(dbHandler.getDatabaseName());
//    }
//
//    public void tearDown() throws Exception {
//        dbHandler.close();
//    }
//
//    public void testWhenAddNewEmptyItemThenNewItemAddedToEmptyItemsTable() {
//        dbHandler.insertNewEmptyItem("Proszek do prania");
//        List<EmptyItem> emptyItemsList = dbHandler.selectAllItemsFromItemsTable();
//        assertEquals(1, emptyItemsList.size());
//    }
}
