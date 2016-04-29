package net.programistka.shoppingadvisor.dbhandlers;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import net.programistka.shoppingadvisor.dbhandlers.DbHandler;

public class ArchiveDbHandler extends DbHandler {

    public ArchiveDbHandler(Context context) {
        super(context);
    }

    public void insertItemToArchiveTable(long itemId) {
        ContentValues itemValues = new ContentValues();
        itemValues.put(COLUMN_ITEM_ID, itemId);
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_ARCHIVE, null, itemValues);
    }
}
