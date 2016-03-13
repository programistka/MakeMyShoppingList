package net.programistka.shoppingadvisor;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DbHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "shopping_advisor.db";
    private static final String TABLE_ITEMS = "items";
    private static final String TABLE_HISTORY = "history";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_ITEMNAME = "_name";
    public static final String COLUMN_ITEMID = "item_id";
    public static final String COLUMN_CREATIONDATE = "creation_date";

    public DbHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PRODUCTS_TABLE = "CREATE TABLE " +
                TABLE_ITEMS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_ITEMNAME + " TEXT)";
        db.execSQL(CREATE_PRODUCTS_TABLE);
        String CREATE_HISTORY_TABLE = "CREATE TABLE IF NOT EXISTS " +
                TABLE_HISTORY + "("
                + COLUMN_ITEMID + " TEXT,"
                + COLUMN_CREATIONDATE + " DATETIME)";
        db.execSQL(CREATE_HISTORY_TABLE);
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

    public ArrayList<Item> getItems() {
        ArrayList<Item> itemsList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_ITEMS + " LEFT JOIN " + TABLE_HISTORY + " ON " + TABLE_ITEMS + "." + COLUMN_ID + "="  + TABLE_HISTORY + "." + COLUMN_ITEMID;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()) {
            do {
                Item item = new Item();
                item.setId(cursor.getInt(0));
                item.setName(cursor.getString(1));
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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
    }
}
