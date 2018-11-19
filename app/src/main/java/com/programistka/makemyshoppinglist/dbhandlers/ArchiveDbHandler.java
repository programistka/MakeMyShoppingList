package com.programistka.makemyshoppinglist.dbhandlers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.programistka.makemyshoppinglist.presenters.DbConfig;

import java.util.List;

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
        if (cursor.getCount() != 0) {
            return true;
        }
        return false;
    }

    public void undoMarkAsArchived(List<Long> selectedItems) {
        SQLiteDatabase db = this.getWritableDatabase();
        for (Long itemId : selectedItems) {
            db.delete(TABLE_ARCHIVE, COLUMN_ITEM_ID + "=" + itemId, null);
        }
        db.close();
    }
}
