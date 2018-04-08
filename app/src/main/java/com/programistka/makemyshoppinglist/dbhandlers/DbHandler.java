package com.programistka.makemyshoppinglist.dbhandlers;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.programistka.makemyshoppinglist.CalendarProvider;
import com.programistka.makemyshoppinglist.models.EmptyItem;
import com.programistka.makemyshoppinglist.presenters.DbConfig;

import java.util.ArrayList;
import java.util.Calendar;
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

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PRODUCTS_TABLE = "CREATE TABLE IF NOT EXISTS " +
                TABLE_ITEMS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_ITEM_NAME + " TEXT)";
        db.execSQL(CREATE_PRODUCTS_TABLE);
        String CREATE_RUN_OUT_OF_HISTORY_TABLE = "CREATE TABLE IF NOT EXISTS " +
                TABLE_EMPTY_ITEMS_HISTORY + "("
                + COLUMN_ITEM_ID + " INTEGER,"
                + COLUMN_EMPTY_ITEM_DATE + " DATETIME)";
        db.execSQL(CREATE_RUN_OUT_OF_HISTORY_TABLE);
        String CREATE_RUN_OUT_OF_PREDICTIONS_TABLE = "CREATE TABLE IF NOT EXISTS " +
                TABLE_EMPTY_ITEMS_PREDICTIONS + "("
                + COLUMN_ITEM_ID + " INTEGER,"
                + COLUMN_NEXT_EMPTY_ITEM_DATE + " DATETIME,"
                + COLUMN_DAYS_TO_RUN_OUT + " INTEGER)";
        db.execSQL(CREATE_RUN_OUT_OF_PREDICTIONS_TABLE);
        String CREATE_ARCHIVE_TABLE = "CREATE TABLE IF NOT EXISTS " +
                TABLE_ARCHIVE + "("
                + COLUMN_ITEM_ID + " INTEGER)";
        db.execSQL(CREATE_ARCHIVE_TABLE);

        Calendar calendar = CalendarProvider.setNowCalendar();
        long lessThanSevenDays = calendar.getTimeInMillis() - 3*1000*24*3600;
        long lessThanThirtyDays = calendar.getTimeInMillis() - 25*1000*24*3600;

        //initializeData(db, 1,  "ziemniaki", 1469829600000L);
//        initializeData(db, 2, "makaron", calendar.getTimeInMillis());
//        initializeData(db, 3, "ziemniaki", lessThanSevenDays);
//        initializeData(db, 4, "kasza", lessThanSevenDays);
//        initializeData(db, 5, "płyn do mycia naczyń", lessThanThirtyDays);
//        initializeData(db, 6, "odkurzacz", lessThanThirtyDays);
//        initializeData(db, 7, "ziemia do kwiatów", lessThanThirtyDays);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEMS);
        onCreate(db);
    }

    public void initializeData(SQLiteDatabase db, long id, String name, long nextEmptyDate) {
        String INSERT_PRODUCTS = "INSERT INTO items VALUES(" + id +", '" + name + "')";
        db.execSQL(INSERT_PRODUCTS);
        String INSERT_HISTORY1 = "INSERT INTO empty_items_history VALUES(" + id + ", 1468792800000)";
        String INSERT_HISTORY2 = "INSERT INTO empty_items_history VALUES(" + id + ", 1469311200000)";
        //String INSERT_HISTORY3 = "INSERT INTO empty_items_history VALUES(" + id + ", 1469311200000 )";
        db.execSQL(INSERT_HISTORY1);
        db.execSQL(INSERT_HISTORY2);
        //db.execSQL(INSERT_HISTORY3);
        String INSERT_PREDICTIONS = "INSERT INTO empty_items_predictions VALUES(" + id + ", " + nextEmptyDate + ", 3)";
        db.execSQL(INSERT_PREDICTIONS);
    }

    public List<EmptyItem> selectAllItemsFromItemsTable () {
        SQLiteDatabase db = this.getWritableDatabase();
        List<EmptyItem> itemsList = new ArrayList<>();
        String selectQuery = "SELECT " + COLUMN_ID + ", " + COLUMN_ITEM_NAME + " FROM " + TABLE_ITEMS;
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()) {
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
        String selectQuery = "SELECT " + COLUMN_ID + ", " + COLUMN_ITEM_NAME  + ", " + COLUMN_EMPTY_ITEM_DATE +
                             " FROM " + TABLE_ITEMS +
                             " LEFT JOIN " + TABLE_EMPTY_ITEMS_HISTORY +
                             " ON " + TABLE_ITEMS + "." + COLUMN_ID + "="  + TABLE_EMPTY_ITEMS_HISTORY + "." + COLUMN_ITEM_ID;
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()) {
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
        String selectQuery = "SELECT " + COLUMN_ID + ", " + COLUMN_ITEM_NAME  + ", " + COLUMN_EMPTY_ITEM_DATE +
                " FROM " + TABLE_ITEMS +
                " LEFT JOIN " + TABLE_EMPTY_ITEMS_HISTORY +
                " ON " + TABLE_ITEMS + "." + COLUMN_ID + "="  + TABLE_EMPTY_ITEMS_HISTORY + "." + COLUMN_ITEM_ID +
                " WHERE " +  TABLE_ITEMS + "." + COLUMN_ID + "=" + id;
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()) {
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
        if(cursor.getColumnIndex(COLUMN_ID) != -1) {
            emptyItem.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
        }
        if(cursor.getColumnIndex(COLUMN_ITEM_NAME) != -1) {
            emptyItem.setName(cursor.getString(cursor.getColumnIndex(COLUMN_ITEM_NAME)));
        }
        if(cursor.getColumnIndex(COLUMN_EMPTY_ITEM_DATE) != -1) {
            emptyItem.setCreationDate(cursor.getLong(cursor.getColumnIndex(COLUMN_EMPTY_ITEM_DATE)));
        }
        return emptyItem;
    }
}
