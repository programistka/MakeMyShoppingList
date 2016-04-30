package net.programistka.shoppingadvisor.dbhandlers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import net.programistka.shoppingadvisor.models.Prediction;
import net.programistka.shoppingadvisor.models.PredictionsHandler;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class EmptyItemsDbHandler extends DbHandler {

    public EmptyItemsDbHandler(Context context) {
        super(context);
    }

    public EmptyItemsDbHandler(Context context, String databaseName) {
        super(context, databaseName);
    }

    public void insertNewEmptyItem(String newEmptyItemName) {
        insertNewEmptyItemIntoItemsTable(newEmptyItemName);

        long lastInsertedId = getLastInsertedId();

        if (lastInsertedId != -1) {
            insertNewEmptyItemIntoHistoryTable(lastInsertedId);
            insertPredictionForItemIntoPredictionsTable(lastInsertedId);
        }
    }

    public void insertExistingEmptyItem(long existingEmptyItemId) {
        SQLiteDatabase db = this.getWritableDatabase();

        insertNewEmptyItemIntoHistoryTable(existingEmptyItemId);

        updatePredictionForItemInPredictionsTable(existingEmptyItemId);

        db.close();
    }

    private long getLastInsertedId() {
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT " + COLUMN_ID + " FROM " + TABLE_ITEMS + " ORDER BY " + COLUMN_ID;
        Cursor cursor = db.rawQuery(selectQuery, null);
        long lastInsertedId = -1;
        if (cursor.moveToLast()) {
            lastInsertedId = cursor.getLong(cursor.getColumnIndex(COLUMN_ID));
        }
        cursor.close();
        db.close();
        return lastInsertedId;
    }

    private void insertNewEmptyItemIntoItemsTable(String itemName) {
        ContentValues emptyItemValues = new ContentValues();
        emptyItemValues.put(COLUMN_ITEM_NAME, itemName);

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_ITEMS, null, emptyItemValues);
        db.close();
    }

    private void insertNewEmptyItemIntoHistoryTable(long itemId) {
        Date c = new Date(System.currentTimeMillis());
        ContentValues shoppingValues = new ContentValues();
        shoppingValues.put(COLUMN_ITEM_ID, itemId);
        shoppingValues.put(COLUMN_EMPTY_ITEM_DATE, c.getTime());

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_EMPTY_ITEMS_HISTORY, null, shoppingValues);
        insertPredictionForItemIntoPredictionsTable(itemId);
        db.close();
    }

    private void insertPredictionForItemIntoPredictionsTable(long itemId) {
        Date c = calculatePredictionForItem(itemId);
        if (c == null) {
            return;
        }
        ContentValues predictionValues = new ContentValues();
        predictionValues.put(COLUMN_ITEM_ID, itemId);
        predictionValues.put(COLUMN_NEXT_EMPTY_ITEM_DATE, c.getTime());

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_EMPTY_ITEMS_PREDICTIONS, null, predictionValues);
        db.close();
    }

    private void updatePredictionForItemInPredictionsTable(long itemId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_EMPTY_ITEMS_PREDICTIONS, COLUMN_ITEM_ID + "=" + itemId, null);
        db.delete(TABLE_ARCHIVE, COLUMN_ITEM_ID + "=" + itemId, null);
        insertPredictionForItemIntoPredictionsTable(itemId);
    }

    @Nullable
    private Date calculatePredictionForItem(long itemId) {
        SQLiteDatabase db = this.getWritableDatabase();
        List<Long> emptyTimes = getEmptyTimes(itemId);
        if (emptyTimes.size() > 1) {
            Prediction prediction = PredictionsHandler.generatePrediction(emptyTimes);
            Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
            calendar.setTimeInMillis(prediction.getTime());
            return calendar.getTime();
        }
        return null;
    }

    @NonNull
    private List<Long> getEmptyTimes(long itemId) {
        SQLiteDatabase db = this.getWritableDatabase();

        List<Long> emptyTimes = new ArrayList<>();
        String selectQuery = "SELECT DISTINCT(" + COLUMN_EMPTY_ITEM_DATE + ") FROM " + TABLE_ITEMS +
                " LEFT JOIN " + TABLE_EMPTY_ITEMS_HISTORY +
                " ON " + TABLE_ITEMS + "." + COLUMN_ID + "=" + TABLE_EMPTY_ITEMS_HISTORY + "." + COLUMN_ITEM_ID +
                " WHERE " + TABLE_ITEMS + "." + COLUMN_ID + "=" + itemId + " ORDER BY " + COLUMN_EMPTY_ITEM_DATE;

        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                emptyTimes.add(cursor.getLong(cursor.getColumnIndex(COLUMN_EMPTY_ITEM_DATE)));
            } while (cursor.moveToNext());
            cursor.close();
            db.close();
        }
        return emptyTimes;
    }
}
