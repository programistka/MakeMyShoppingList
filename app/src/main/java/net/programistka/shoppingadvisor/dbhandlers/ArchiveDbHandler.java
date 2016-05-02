package net.programistka.shoppingadvisor.dbhandlers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import net.programistka.shoppingadvisor.dbhandlers.DbHandler;
import net.programistka.shoppingadvisor.models.Prediction;
import net.programistka.shoppingadvisor.models.PredictionsHandler;
import net.programistka.shoppingadvisor.presenters.DbConfig;

public class ArchiveDbHandler extends DbHandler {

    public ArchiveDbHandler(DbConfig dbConfig, Context context) {
        super(dbConfig, context);
    }

    public void insertItemToArchiveTable(long itemId) {
        ContentValues itemValues = new ContentValues();
        itemValues.put(COLUMN_ITEM_ID, itemId);
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_ARCHIVE, null, itemValues);
    }

    public Boolean checkIfArchivedElement(long itemId) {
        String selectQuery = "SELECT * FROM " + TABLE_ARCHIVE + " WHERE " + COLUMN_ITEM_ID + "=" + itemId;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.getCount() != 0) {
            return true;
        }
        return false;
    }
}
