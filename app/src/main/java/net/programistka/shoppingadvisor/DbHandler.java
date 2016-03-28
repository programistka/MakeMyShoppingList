package net.programistka.shoppingadvisor;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import net.programistka.shoppingadvisor.models.Item;

import java.util.ArrayList;
import java.util.Date;

public class DbHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "shopping_advisor.db";
    private static final String TABLE_ITEMS = "items";
    private static final String TABLE_HISTORY = "history";
    private static final String TABLE_PREDICTIONS = "predictions";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_ITEMNAME = "_name";
    public static final String COLUMN_ITEMID = "item_id";
    public static final String COLUMN_CREATIONDATE = "creation_date";
    public static final String COLUMN_NEXTDATE = "next_date";


    public DbHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PRODUCTS_TABLE = "CREATE TABLE IF NOT EXISTS " +
                TABLE_ITEMS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_ITEMNAME + " TEXT)";
        db.execSQL(CREATE_PRODUCTS_TABLE);
        String CREATE_HISTORY_TABLE = "CREATE TABLE IF NOT EXISTS " +
                TABLE_HISTORY + "("
                + COLUMN_ITEMID + " TEXT,"
                + COLUMN_CREATIONDATE + " DATETIME)";
        db.execSQL(CREATE_HISTORY_TABLE);
        String CREATE_PREDICTIONS_TABLE = "CREATE TABLE IF NOT EXISTS " +
                TABLE_PREDICTIONS + "("
                + COLUMN_ITEMID + " TEXT,"
                + COLUMN_NEXTDATE + " DATETIME)";
        db.execSQL(CREATE_PREDICTIONS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEMS);
        onCreate(db);
    }

    public void addItem(Item item) {
        ContentValues itemValues = new ContentValues();
        itemValues.put(COLUMN_ITEMNAME, item.getName());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_ITEMS, null, itemValues);

        String selectQuery = "SELECT  * FROM " + TABLE_ITEMS + " ORDER BY " + COLUMN_ID;
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToLast();
        long lastInsertedId = cursor.getLong(0);

        Date c = new Date(System.currentTimeMillis());
        ContentValues shoppingValues = new ContentValues();
        shoppingValues.put(COLUMN_ITEMID, lastInsertedId);
        shoppingValues.put(COLUMN_CREATIONDATE, c.getTime());
        db.insert(TABLE_HISTORY, null, shoppingValues);


        db.close();
    }

    public ArrayList<Item> getSuggestionsItems() {
        ArrayList<Item> itemsList = new ArrayList<>();
        String selectQuery = "SELECT DISTINCT(" + COLUMN_ID + ")," + COLUMN_ITEMNAME  + " FROM " + TABLE_ITEMS + " LEFT JOIN " + TABLE_HISTORY + " ON " + TABLE_ITEMS + "." + COLUMN_ID + "="  + TABLE_HISTORY + "." + COLUMN_ITEMID;
        System.out.printf(selectQuery);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()) {
            do {
                Item item = new Item();
                item.setId(cursor.getInt(0));
                item.setName(cursor.getString(1));
                itemsList.add(item);
            } while (cursor.moveToNext());
            cursor.close();
            db.close();
        }
        return itemsList;
    }

    public ArrayList<Item> getItems() {
        ArrayList<Item> itemsList = new ArrayList<>();
        String selectQuery = "SELECT *  FROM " + TABLE_ITEMS + " LEFT JOIN " + TABLE_HISTORY + " ON " + TABLE_ITEMS + "." + COLUMN_ID + "="  + TABLE_HISTORY + "." + COLUMN_ITEMID;
        System.out.printf(selectQuery);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()) {
            do {
                Item item = new Item();
                item.setId(cursor.getInt(0));
                item.setName(cursor.getString(1));
                item.setDate(new Date(cursor.getLong(3)));
                itemsList.add(item);
            } while (cursor.moveToNext());
            cursor.close();
            db.close();
        }
        return itemsList;
    }

    public void addShoppingItem(long id) {
        Date c = new Date(System.currentTimeMillis());
        ContentValues shoppingValues = new ContentValues();
        shoppingValues.put(COLUMN_ITEMID, id);
        shoppingValues.put(COLUMN_CREATIONDATE, c.getTime());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_HISTORY, null, shoppingValues);
        addOrUpdatePredictionsForItem(id);
    }

    public void addOrUpdatePredictionsForItem(long id) {
        Date c = calculatePredictionForItem(id);
        if(c == null)
        {
            return;
        }
        ContentValues predictionValues = new ContentValues();
        predictionValues.put(COLUMN_ITEMID, id);
        predictionValues.put(COLUMN_NEXTDATE, c.getTime());
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT COUNT(*) FROM " + TABLE_PREDICTIONS + " WHERE " + COLUMN_ITEMID + "=" + id;
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor != null && cursor.moveToFirst()) {
            db.update(TABLE_PREDICTIONS, predictionValues, COLUMN_ITEMID + "=" + id, null);
        }
        else {
            db.insert(TABLE_PREDICTIONS, null, predictionValues);
        }
    }

    private Date calculatePredictionForItem(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<Long> shoppingTimes = new ArrayList<>();
        String selectQuery = "SELECT DISTINCT(DATE(" + COLUMN_CREATIONDATE + "/1000, 'unixepoch')) FROM " + TABLE_ITEMS +
                " LEFT JOIN " + TABLE_HISTORY +
                " ON " + TABLE_ITEMS + "." + COLUMN_ID + "="  + TABLE_HISTORY + "." + COLUMN_ITEMID +
                " WHERE " + TABLE_ITEMS + "." + COLUMN_ID + "=" + id + " ORDER BY DATE(" + COLUMN_CREATIONDATE + "/1000, 'unixepoch')";

        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()) {
            do {
                shoppingTimes.add(cursor.getLong(0));
            } while (cursor.moveToNext());
            cursor.close();
            db.close();
        }
        long prediction = 0;
        if(shoppingTimes.size() > 1) {
            long current = shoppingTimes.get(0);
            long next = shoppingTimes.get(1);
            if(shoppingTimes.size() == 2) {
                prediction = next - current;
            } else {
                for (int i = 2; i < shoppingTimes.size(); i++) {
                    prediction += next - current;
                    current = next;
                    next = shoppingTimes.get(i);
                }
            }
            return new Date(System.currentTimeMillis() + prediction);
        }
        else
        {
            return null;
        }

    }

    public ArrayList<Item> getPredictions() {
        ArrayList<Item> itemsList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_PREDICTIONS + " LEFT JOIN " + TABLE_ITEMS + " ON " + TABLE_PREDICTIONS + "." + COLUMN_ITEMID + "="  + TABLE_ITEMS + "." + COLUMN_ID;
        System.out.println(selectQuery);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()) {
            do {
                Item item = new Item();
                item.setId(cursor.getInt(0));
                item.setName(cursor.getString(3));
                item.setPredictionDate(new Date(cursor.getLong(1)));
                itemsList.add(item);
            } while (cursor.moveToNext());
            cursor.close();
            db.close();
        }
        return itemsList;
    }
}
