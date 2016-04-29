package net.programistka.shoppingadvisor;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import net.programistka.shoppingadvisor.models.Item;
import net.programistka.shoppingadvisor.models.Prediction;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class DbHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "shopping_advisor.db";
    private static final String TABLE_ITEMS = "items";
    private static final String TABLE_EMPTY_ITEMS_HISTORY = "empty_items_history";
    private static final String TABLE_EMPTY_ITEMS_PREDICTIONS = "empty_items_predictions";
    private static final String TABLE_ARCHIVE = "archive";

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_ITEM_NAME = "name";
    private static final String COLUMN_ITEM_ID = "item_id";
    private static final String COLUMN_EMPTY_ITEM_DATE = "empty_item_date";
    private static final String COLUMN_NEXT_EMPTY_ITEM_DATE = "next_empty_item_date";
    private static final String COLUMN_DAYS_TO_RUN_OUT = "days_to_run_out";

    public DbHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
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
        initializeData(db);
        initializeData2(db);
    }

    public void initializeData(SQLiteDatabase db) {
        String INSERT_PRODUCTS = "INSERT INTO items VALUES(1, 'makaron')";
        db.execSQL(INSERT_PRODUCTS);
        String INSERT_HISTORY1 = "INSERT INTO empty_items_history VALUES(1, 1458428400000)";
        String INSERT_HISTORY2 = "INSERT INTO empty_items_history VALUES(1, 1458687600000)";
        String INSERT_HISTORY3 = "INSERT INTO empty_items_history VALUES(1, 1458946800000)";
        db.execSQL(INSERT_HISTORY1);
        db.execSQL(INSERT_HISTORY2);
        db.execSQL(INSERT_HISTORY3);
        String INSERT_PREDICTIONS = "INSERT INTO empty_items_predictions VALUES(1, 1479596400000, 3)";
        db.execSQL(INSERT_PREDICTIONS);
    }

    public void initializeData2(SQLiteDatabase db) {
        String INSERT_PRODUCTS = "INSERT INTO items VALUES(2, 'szampon')";
        db.execSQL(INSERT_PRODUCTS);
        String INSERT_HISTORY1 = "INSERT INTO empty_items_history VALUES(2, 1457996400000)";
        String INSERT_HISTORY2 = "INSERT INTO empty_items_history VALUES(2, 1458860400000)";
        String INSERT_HISTORY3 = "INSERT INTO empty_items_history VALUES(2, 1459461600000)";
        db.execSQL(INSERT_HISTORY1);
        db.execSQL(INSERT_HISTORY2);
        db.execSQL(INSERT_HISTORY3);
        String INSERT_PREDICTIONS = "INSERT INTO empty_items_predictions VALUES(2, 1459807200000, 3)";
        db.execSQL(INSERT_PREDICTIONS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEMS);
        onCreate(db);
    }

    public void insertNewEmptyItem(Item newEmptyItem) {
        SQLiteDatabase db = this.getWritableDatabase();

        insertNewEmptyItemIntoItemsTable(db, newEmptyItem);

        long lastInsertedId = getLastInsertedId(db);

        if(lastInsertedId != -1)
        {
            insertNewEmptyItemIntoHistoryTable(db, lastInsertedId);
        }

        addOrUpdatePredictionsForItem(lastInsertedId);

        db.close();
    }

    public void insertExistingEmptyItem(long existingEmptyItemId) {
        SQLiteDatabase db = this.getWritableDatabase();

        insertNewEmptyItemIntoHistoryTable(db, existingEmptyItemId);

        addOrUpdatePredictionsForItem(existingEmptyItemId);

        db.close();
    }

    public List<Item> selectAllItemsFromItemsTable () {
        ArrayList<Item> itemsList = new ArrayList<>();
        String selectQuery = "SELECT " + COLUMN_ID + ", " + COLUMN_ITEM_NAME + " FROM " + TABLE_ITEMS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()) {
            do {
                Item item = new Item();
                item.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                item.setName(cursor.getString(cursor.getColumnIndex(COLUMN_ITEM_NAME)));
                itemsList.add(item);
            } while (cursor.moveToNext());
            cursor.close();
            db.close();
        }
        return itemsList;
    }

    public ArrayList<Item> selectAllItemsFromHistoryTable() {
        ArrayList<Item> itemsList = new ArrayList<>();
        String selectQuery = "SELECT " + COLUMN_ID + ", " + COLUMN_ITEM_NAME  + ", " + COLUMN_EMPTY_ITEM_DATE +
                             " FROM " + TABLE_ITEMS +
                             " LEFT JOIN " + TABLE_EMPTY_ITEMS_HISTORY +
                             " ON " + TABLE_ITEMS + "." + COLUMN_ID + "="  + TABLE_EMPTY_ITEMS_HISTORY + "." + COLUMN_ITEM_ID;
        System.out.println(selectQuery);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()) {
            do {
                itemsList.add(createItem(cursor));
            } while (cursor.moveToNext());
            cursor.close();
            db.close();
        }
        return itemsList;
    }

    private Item createItem(Cursor cursor) {
        Item item = new Item();
        item.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
        item.setName(cursor.getString(cursor.getColumnIndex(COLUMN_ITEM_NAME)));
        item.setDate(new Date(cursor.getLong(cursor.getColumnIndex(COLUMN_EMPTY_ITEM_DATE))));
        return item;
    }

    private void addOrUpdatePredictionsForItem(long id) {
        Date c = calculatePredictionForItem(id);
        if(c == null)
        {
            return;
        }
        ContentValues predictionValues = new ContentValues();
        predictionValues.put(COLUMN_ITEM_ID, id);
        predictionValues.put(COLUMN_NEXT_EMPTY_ITEM_DATE, c.getTime());
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT COUNT(*) FROM " + TABLE_EMPTY_ITEMS_PREDICTIONS + " WHERE " + COLUMN_ITEM_ID + "=" + id;
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor != null && cursor.moveToFirst()) {
            db.delete(TABLE_EMPTY_ITEMS_PREDICTIONS, COLUMN_ITEM_ID + "=" + id, null);
            cursor.close();

            String selectFromArchiveQuery = "SELECT COUNT(*) FROM " + TABLE_ARCHIVE + " WHERE " + COLUMN_ITEM_ID + "=" + id;
            Cursor cursor2 = db.rawQuery(selectFromArchiveQuery, null);
            if(cursor2 != null && cursor2.moveToFirst()) {
                db.delete(TABLE_ARCHIVE, COLUMN_ITEM_ID + "=" + id, null);
                cursor2.close();
            }
        }
        db.insert(TABLE_EMPTY_ITEMS_PREDICTIONS, null, predictionValues);
        db.close();
    }

    private Date calculatePredictionForItem(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<Long> shoppingTimes = new ArrayList<>();
        String selectQuery = "SELECT DISTINCT(" + COLUMN_EMPTY_ITEM_DATE +") FROM " + TABLE_ITEMS +
                " LEFT JOIN " + TABLE_EMPTY_ITEMS_HISTORY +
                " ON " + TABLE_ITEMS + "." + COLUMN_ID + "="  + TABLE_EMPTY_ITEMS_HISTORY + "." + COLUMN_ITEM_ID +
                " WHERE " + TABLE_ITEMS + "." + COLUMN_ID + "=" + id + " ORDER BY " + COLUMN_EMPTY_ITEM_DATE;

        System.out.println(selectQuery);
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()) {
            do {
                shoppingTimes.add(cursor.getLong(cursor.getColumnIndex(COLUMN_EMPTY_ITEM_DATE)));
            } while (cursor.moveToNext());
            cursor.close();
            db.close();
        }
        if(shoppingTimes.size() > 1) {
            Prediction prediction = PredictionsHandler.generatePrediction(shoppingTimes);
            Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
            calendar.setTimeInMillis(prediction.getTime());
            return calendar.getTime();
        }
        return null;
    }

    public ArrayList<Item> getPredictions() {
        ArrayList<Item> itemsList = new ArrayList<>();
        String selectQuery = "SELECT DISTINCT(" + TABLE_EMPTY_ITEMS_PREDICTIONS + "." + COLUMN_ITEM_ID + "),*" +
                             " FROM " + TABLE_EMPTY_ITEMS_PREDICTIONS +
                             " LEFT JOIN " + TABLE_ITEMS +
                             " ON " + TABLE_EMPTY_ITEMS_PREDICTIONS + "." + COLUMN_ITEM_ID + "="  + TABLE_ITEMS + "." + COLUMN_ID +
                             " LEFT JOIN " + TABLE_ARCHIVE +
                             " ON " + TABLE_EMPTY_ITEMS_PREDICTIONS + "." + COLUMN_ITEM_ID + "="  + TABLE_ARCHIVE + "." + COLUMN_ITEM_ID +
                             " WHERE " + TABLE_ARCHIVE + "." +COLUMN_ITEM_ID + " ISNULL" +
                             " GROUP BY " + TABLE_EMPTY_ITEMS_PREDICTIONS +"." + COLUMN_ITEM_ID;
        System.out.println(selectQuery);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()) {
            do {
                Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
                calendar.setTimeInMillis(cursor.getLong(cursor.getColumnIndex(COLUMN_NEXT_EMPTY_ITEM_DATE)));

                Item item = new Item();
                item.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                item.setName(cursor.getString(cursor.getColumnIndex(COLUMN_ITEM_NAME)));
                item.setPredictionDate(calendar.getTime());
                itemsList.add(item);
            } while (cursor.moveToNext());
            cursor.close();
            db.close();
        }
        return itemsList;
    }

    public void addBoughtPrediction(long itemId) {
        Prediction prediction = getCurrentPredictionForItem(itemId);
        ContentValues itemValues = new ContentValues();
        itemValues.put(COLUMN_ITEM_ID, itemId);
        itemValues.put(COLUMN_NEXT_EMPTY_ITEM_DATE, prediction.getTime());
        itemValues.put(COLUMN_DAYS_TO_RUN_OUT, prediction.getDaysNumber());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_EMPTY_ITEMS_PREDICTIONS, null, itemValues);
    }

    public Prediction getCurrentPredictionForItem(long itemId) {
        String selectQuery = "SELECT * FROM " + TABLE_EMPTY_ITEMS_PREDICTIONS + " WHERE " + COLUMN_ITEM_ID + "=" + itemId;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        Prediction currentPrediction = new Prediction();
        if(cursor.moveToFirst()){
            currentPrediction.setTime(cursor.getLong(cursor.getColumnIndex(COLUMN_NEXT_EMPTY_ITEM_DATE)));
            currentPrediction.setDays_number(cursor.getInt(cursor.getColumnIndex(COLUMN_DAYS_TO_RUN_OUT)));
        }
        Prediction newPrediction = new Prediction();
        newPrediction.setTime(currentPrediction.getTime() + currentPrediction.getDaysNumber()*24*3600*1000);
        newPrediction.setDays_number(currentPrediction.getDaysNumber());
        return newPrediction;
    }

    public void addItemToArchive(long itemId) {
        ContentValues itemValues = new ContentValues();
        itemValues.put(COLUMN_ITEM_ID, itemId);
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_ARCHIVE, null, itemValues);
    }

    private long getLastInsertedId(SQLiteDatabase db) {
        String selectQuery = "SELECT " + COLUMN_ID + " FROM " + TABLE_ITEMS + " ORDER BY " + COLUMN_ID;
        Cursor cursor = db.rawQuery(selectQuery, null);
        long lastInsertedId = -1;
        if(cursor.moveToLast()) {
            lastInsertedId = cursor.getLong(cursor.getColumnIndex(COLUMN_ID));
        }
        cursor.close();
        return lastInsertedId;
    }

    private SQLiteDatabase insertNewEmptyItemIntoItemsTable(SQLiteDatabase db, Item itemId) {
        ContentValues emptyItemValues = new ContentValues();
        emptyItemValues.put(COLUMN_ITEM_NAME, itemId.getName());
        db.insert(TABLE_ITEMS, null, emptyItemValues);
        return db;
    }

    private void insertNewEmptyItemIntoHistoryTable(SQLiteDatabase db, long itemId) {
        Date c = new Date(System.currentTimeMillis());
        ContentValues shoppingValues = new ContentValues();
        shoppingValues.put(COLUMN_ITEM_ID, itemId);
        shoppingValues.put(COLUMN_EMPTY_ITEM_DATE, c.getTime());
        db.insert(TABLE_EMPTY_ITEMS_HISTORY, null, shoppingValues);
        addOrUpdatePredictionsForItem(itemId);
    }

    public void insertNewEmptyItemIntoHistoryTable(long itemId) {
        SQLiteDatabase db = this.getWritableDatabase();
        insertNewEmptyItemIntoHistoryTable(db, itemId);
        db.close();
    }
}
