package net.programistka.shoppingadvisor.dbhandlers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import net.programistka.shoppingadvisor.CalendarProvider;
import net.programistka.shoppingadvisor.models.Prediction;
import net.programistka.shoppingadvisor.models.PredictionsHandler;
import net.programistka.shoppingadvisor.presenters.DbConfig;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class EmptyItemsDbHandler extends DbHandler {

    public EmptyItemsDbHandler(DbConfig dbConfig, Context context) {
        super(dbConfig, context);
    }

    public long insertNewEmptyItem(String newEmptyItemName, long time) {
        String trimmedName = newEmptyItemName.toLowerCase().trim();
        long itemId = checkIfElementAlreadyExistsInDatabase(trimmedName);
        if(itemId > 0) {
            insertExistingEmptyItem(itemId, time);
            return itemId;
        }

        insertNewEmptyItemIntoItemsTable(trimmedName);

        long lastInsertedId = getLastInsertedId();

        if (lastInsertedId != -1) {
            insertNewEmptyItemIntoHistoryTable(lastInsertedId, time);
        }
        return lastInsertedId;
    }

    public void insertExistingEmptyItem(long existingEmptyItemId, long time) {
        if(checkIfItemExistsWithTheSameDate(existingEmptyItemId, time)) {
            return;
        }

        SQLiteDatabase db = this.getWritableDatabase();

        insertNewEmptyItemIntoHistoryTable(existingEmptyItemId, time);

        updatePredictionForItemInPredictionsTable(existingEmptyItemId);

        db.close();
    }

    private boolean checkIfItemExistsWithTheSameDate(long existingEmptyItemId, long time) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT " + COLUMN_ITEM_ID + " FROM " + TABLE_EMPTY_ITEMS_HISTORY +
                             " WHERE " + COLUMN_ITEM_ID + " = " + existingEmptyItemId +
                             " AND " + COLUMN_EMPTY_ITEM_DATE + " = " + time;
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.getCount() > 0){
            db.close();
            return true;
        }
        db.close();
        return false;
    }

    private long getLastInsertedId() {
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT " + COLUMN_ID + " FROM " + TABLE_ITEMS + " ORDER BY " + COLUMN_ID;
        Cursor cursor = db.rawQuery(selectQuery, null);
        long lastInsertedId = 0;
        if (cursor.moveToLast()) {
            lastInsertedId = cursor.getLong(cursor.getColumnIndex(COLUMN_ID));
        }
        cursor.close();
        db.close();
        return lastInsertedId;
    }

    private long checkIfElementAlreadyExistsInDatabase(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TABLE_ITEMS, new String[] { COLUMN_ID }, "" + COLUMN_ITEM_NAME + "= ?", new String[] { name }, null, null, null);
        if(cursor.getCount() > 0 )
        {
            cursor.moveToFirst();
            long id = cursor.getLong(cursor.getColumnIndex(COLUMN_ID));
            cursor.close();
            db.close();
            return id;
        }
        db.close();
        return -1;
    }

    private void insertNewEmptyItemIntoItemsTable(String itemName) {
        ContentValues emptyItemValues = new ContentValues();
        emptyItemValues.put(COLUMN_ITEM_NAME, itemName);

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_ITEMS, null, emptyItemValues);
        db.close();
    }

    private void insertNewEmptyItemIntoHistoryTable(long itemId, long time) {
        ContentValues shoppingValues = new ContentValues();
        shoppingValues.put(COLUMN_ITEM_ID, itemId);
        shoppingValues.put(COLUMN_EMPTY_ITEM_DATE, time);

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_EMPTY_ITEMS_HISTORY, null, shoppingValues);
        db.close();
    }

    public void insertPredictionForItemIntoPredictionsTable(long itemId, Prediction prediction) {
        ContentValues predictionValues = new ContentValues();
        predictionValues.put(COLUMN_ITEM_ID, itemId);
        predictionValues.put(COLUMN_NEXT_EMPTY_ITEM_DATE, prediction.getTime());
        predictionValues.put(COLUMN_DAYS_TO_RUN_OUT, prediction.getDaysNumber());

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_EMPTY_ITEMS_PREDICTIONS, null, predictionValues);
        db.close();
    }

    public void updatePredictionForItemInPredictionsTable(long itemId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_EMPTY_ITEMS_PREDICTIONS, COLUMN_ITEM_ID + "=" + itemId, null);
        db.delete(TABLE_ARCHIVE, COLUMN_ITEM_ID + "=" + itemId, null);
        db.close();
        insertPredictionForItemIntoPredictionsTable(itemId, calculatePredictionForItem(itemId));
    }

    public Prediction calculatePredictionForItem(long itemId) {
        List<Long> emptyTimes = getEmptyTimes(itemId);
        if (emptyTimes.size() > 1) {
            Prediction prediction = PredictionsHandler.generatePrediction(emptyTimes);
            return prediction;
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
        }
        db.close();
        return emptyTimes;
    }

    public void deleteExistingEmptyItem(Long itemId) {
        Calendar c = CalendarProvider.setNowCalendar();
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_EMPTY_ITEMS_HISTORY, COLUMN_EMPTY_ITEM_DATE + "=" + c.getTimeInMillis(), null);
        db.close();
        updatePredictionForItemInPredictionsTable(itemId);
    }

    public void insertNewEmptyItemAndPrediction(String name, long time, int daysToRunOut) {
        insertNewEmptyItemIntoItemsTable(name);
        long id = getLastInsertedId();
        Prediction prediction = new Prediction();
        prediction.setDaysNumber(daysToRunOut);
        prediction.setTime(time + daysToRunOut*1000*3600*24);
        insertPredictionForItemIntoPredictionsTable(id, prediction);
    }
}
