package net.programistka.shoppingadvisor;

import android.test.AndroidTestCase;

import net.programistka.shoppingadvisor.dbhandlers.EmptyItemsDbHandler;
import net.programistka.shoppingadvisor.models.EmptyItem;

import java.util.List;

public class EmptyItemsDbHandlerTests extends AndroidTestCase {
    private EmptyItemsDbHandler dbHandler;

    public void setUp() throws Exception {
        dbHandler = new EmptyItemsDbHandler(mContext);
        mContext.deleteDatabase(dbHandler.getDatabaseName());
    }

    public void tearDown() throws Exception {
        dbHandler.close();
    }

    public void testWhenAddNewEmptyItemThenNewItemAddedToEmptyItemsTable() {
        dbHandler.insertNewEmptyItem("Proszek do prania");
        List<EmptyItem> emptyItemsList = dbHandler.selectAllItemsFromItemsTable();
        assertEquals(1, emptyItemsList.size());
    }
}
