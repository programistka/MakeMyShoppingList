package net.programistka.shoppingadvisor;

import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

import net.programistka.shoppingadvisor.dbhandlers.EmptyItemsDbHandler;

public class MyTest extends AndroidTestCase {
    public void testCreateDB(){
        EmptyItemsDbHandler dbHelper = new EmptyItemsDbHandler(mContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        assertTrue(db.isOpen());
        db.close();
    }
}
