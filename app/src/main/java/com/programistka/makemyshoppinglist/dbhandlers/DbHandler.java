package com.programistka.makemyshoppinglist.dbhandlers;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.programistka.makemyshoppinglist.models.EmptyItem;
import com.programistka.makemyshoppinglist.presenters.DbConfig;

import java.util.ArrayList;
import java.util.List;

public class DbHandler extends SQLiteOpenHelper {
    protected static final int DATABASE_VERSION = 1;
    protected static final String TABLE_ITEMS = "items";
    protected static final String TABLE_EMPTY_ITEMS_HISTORY = "empty_items_history";
    protected static final String TABLE_EMPTY_ITEMS_PREDICTIONS = "empty_items_predictions";
    protected static final String TABLE_ARCHIVE = "archive";

    protected static final String COLUMN_ID = "id";
    protected static final String COLUMN_ITEM_NAME = "name";
    protected static final String COLUMN_ITEM_ID = "item_id";
    protected static final String COLUMN_EMPTY_ITEM_DATE = "empty_item_date";
    protected static final String COLUMN_NEXT_EMPTY_ITEM_DATE = "next_empty_item_date";
    protected static final String COLUMN_DAYS_TO_RUN_OUT = "days_to_run_out";

    public DbHandler(DbConfig config, Context context) {
        super(context, config.getDbName(), null, DATABASE_VERSION);
    }

    public List<EmptyItem> selectAllItemsFromItemsTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        List<EmptyItem> itemsList = new ArrayList<>();
        String selectQuery = "SELECT " + COLUMN_ID + ", " + COLUMN_ITEM_NAME + " FROM " + TABLE_ITEMS;
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                itemsList.add(createItemInstance(cursor));
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return itemsList;
    }

    public List<EmptyItem> selectAllItemsFromEmptyItemsHistoryTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        List<EmptyItem> itemsList = new ArrayList<>();
        String selectQuery = "SELECT " + COLUMN_ID + ", " + COLUMN_ITEM_NAME + ", " + COLUMN_EMPTY_ITEM_DATE +
                " FROM " + TABLE_ITEMS +
                " LEFT JOIN " + TABLE_EMPTY_ITEMS_HISTORY +
                " ON " + TABLE_ITEMS + "." + COLUMN_ID + "=" + TABLE_EMPTY_ITEMS_HISTORY + "." + COLUMN_ITEM_ID;
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                itemsList.add(createItemInstance(cursor));
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return itemsList;
    }

    public List<EmptyItem> selectAllItemsFromEmptyItemsHistoryTableByItemId(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        List<EmptyItem> itemsList = new ArrayList<>();
        String selectQuery = "SELECT " + COLUMN_ID + ", " + COLUMN_ITEM_NAME + ", " + COLUMN_EMPTY_ITEM_DATE +
                " FROM " + TABLE_ITEMS +
                " LEFT JOIN " + TABLE_EMPTY_ITEMS_HISTORY +
                " ON " + TABLE_ITEMS + "." + COLUMN_ID + "=" + TABLE_EMPTY_ITEMS_HISTORY + "." + COLUMN_ITEM_ID +
                " WHERE " + TABLE_ITEMS + "." + COLUMN_ID + "=" + id;
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                itemsList.add(createItemInstance(cursor));
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return itemsList;
    }

    private EmptyItem createItemInstance(Cursor cursor) {
        EmptyItem emptyItem = new EmptyItem();
        if (cursor.getColumnIndex(COLUMN_ID) != -1) {
            emptyItem.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
        }
        if (cursor.getColumnIndex(COLUMN_ITEM_NAME) != -1) {
            emptyItem.setName(cursor.getString(cursor.getColumnIndex(COLUMN_ITEM_NAME)));
        }
        if (cursor.getColumnIndex(COLUMN_EMPTY_ITEM_DATE) != -1) {
            emptyItem.setCreationDate(cursor.getLong(cursor.getColumnIndex(COLUMN_EMPTY_ITEM_DATE)));
        }
        return emptyItem;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
