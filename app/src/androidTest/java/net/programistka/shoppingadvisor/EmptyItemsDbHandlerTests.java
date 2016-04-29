package net.programistka.shoppingadvisor;

import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

import net.programistka.shoppingadvisor.dbhandlers.EmptyItemsDbHandler;

public class EmptyItemsDbHandlerTests extends AndroidTestCase {

    public void testCreateDB() {
        EmptyItemsDbHandler dbHandler = new EmptyItemsDbHandler(mContext);
        SQLiteDatabase db = dbHandler.getWritableDatabase();
        assertTrue(db.isOpen());
        db.close();
    }

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
