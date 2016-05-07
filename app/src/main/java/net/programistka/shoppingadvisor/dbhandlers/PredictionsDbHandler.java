package net.programistka.shoppingadvisor.dbhandlers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import net.programistka.shoppingadvisor.models.EmptyItem;
import net.programistka.shoppingadvisor.models.Prediction;
import net.programistka.shoppingadvisor.models.PredictionsHandler;
import net.programistka.shoppingadvisor.presenters.DbConfig;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class PredictionsDbHandler extends DbHandler {

    public PredictionsDbHandler(DbConfig dbConfig, Context context) {
        super(dbConfig, context);
    }

    public List<EmptyItem> getPredictions() {
        List<EmptyItem> itemsList = new ArrayList<>();
        String selectQuery = "SELECT DISTINCT(" + TABLE_EMPTY_ITEMS_PREDICTIONS + "." + COLUMN_ITEM_ID + "),*" +
                " FROM " + TABLE_EMPTY_ITEMS_PREDICTIONS +
                " LEFT JOIN " + TABLE_ITEMS +
                " ON " + TABLE_EMPTY_ITEMS_PREDICTIONS + "." + COLUMN_ITEM_ID + "="  + TABLE_ITEMS + "." + COLUMN_ID +
                " LEFT JOIN " + TABLE_ARCHIVE +
                " ON " + TABLE_EMPTY_ITEMS_PREDICTIONS + "." + COLUMN_ITEM_ID + "="  + TABLE_ARCHIVE + "." + COLUMN_ITEM_ID +
                " WHERE " + TABLE_ARCHIVE + "." +COLUMN_ITEM_ID + " ISNULL" +
                " GROUP BY " + TABLE_EMPTY_ITEMS_PREDICTIONS +"." + COLUMN_ITEM_ID;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()) {
            do {
                EmptyItem emptyItem = new EmptyItem();
                emptyItem.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                emptyItem.setName(cursor.getString(cursor.getColumnIndex(COLUMN_ITEM_NAME)));
                emptyItem.setPredictionDate(cursor.getLong(cursor.getColumnIndex(COLUMN_NEXT_EMPTY_ITEM_DATE)));
                itemsList.add(emptyItem);
            } while (cursor.moveToNext());
            cursor.close();
            db.close();
        }
        return itemsList;
    }

    public Prediction getPredictionForItem(long id) {
        String selectQuery = "SELECT " + COLUMN_DAYS_TO_RUN_OUT + ", " + COLUMN_NEXT_EMPTY_ITEM_DATE +
                " FROM " + TABLE_EMPTY_ITEMS_PREDICTIONS +
                " WHERE " + COLUMN_ITEM_ID + " = " + id;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.getCount() == 0) {
            return null;
        }
        Prediction prediction = new Prediction();
        if(cursor.moveToFirst()) {
            do {
                prediction.setDaysNumber(cursor.getInt(cursor.getColumnIndex(COLUMN_DAYS_TO_RUN_OUT)));
                prediction.setTime(cursor.getLong(cursor.getColumnIndex(COLUMN_NEXT_EMPTY_ITEM_DATE)));
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return prediction;
    }

    public void insertBoughtPredictionIntoPredictionsTable(long itemId) {
        Prediction prediction = getBoughtPredictionForEmptyItem(itemId);
        ContentValues itemValues = new ContentValues();
        itemValues.put(COLUMN_ITEM_ID, itemId);
        itemValues.put(COLUMN_NEXT_EMPTY_ITEM_DATE, prediction.getTime());
        itemValues.put(COLUMN_DAYS_TO_RUN_OUT, prediction.getDaysNumber());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_EMPTY_ITEMS_PREDICTIONS, null, itemValues);
    }

    public Prediction getBoughtPredictionForEmptyItem(long itemId) {
        String selectQuery = "SELECT * FROM " + TABLE_EMPTY_ITEMS_PREDICTIONS + " WHERE " + COLUMN_ITEM_ID + "=" + itemId;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        Prediction newPrediction = new Prediction();
        if(cursor.moveToFirst()) {
            long nextEmptyItemDate = cursor.getLong(cursor.getColumnIndex(COLUMN_NEXT_EMPTY_ITEM_DATE));
            int daysToRunOut = cursor.getInt(cursor.getColumnIndex(COLUMN_DAYS_TO_RUN_OUT));
            Calendar c = Calendar.getInstance();
            if(nextEmptyItemDate < c.getTimeInMillis()) {
                newPrediction = PredictionsHandler.generateExpiredBoughtPrediction(daysToRunOut);
            }
            else {
                newPrediction = PredictionsHandler.generateBoughtPrediction(nextEmptyItemDate, daysToRunOut);
            }
        }
        return newPrediction;
    }
}
