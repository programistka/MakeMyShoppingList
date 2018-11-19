package com.programistka.makemyshoppinglist.predictions;

import android.content.Context;

import com.programistka.makemyshoppinglist.dbhandlers.EmptyItemsDbHandler;
import com.programistka.makemyshoppinglist.dbhandlers.PredictionsDbHandler;
import com.programistka.makemyshoppinglist.models.EmptyItem;
import com.programistka.makemyshoppinglist.models.Prediction;
import com.programistka.makemyshoppinglist.presenters.DbConfig;

import java.util.List;

public class ShowPredictionsInteractor {
    private PredictionsDbHandler dbHandler;
    private EmptyItemsDbHandler emptyItemsDbHandler;

    public ShowPredictionsInteractor(DbConfig dbConfig, Context context) {
        dbHandler = new PredictionsDbHandler(dbConfig, context);
        emptyItemsDbHandler = new EmptyItemsDbHandler(dbConfig, context);
    }

    public List<EmptyItem> getPredictions() {
        return dbHandler.getPredictions();
    }

    public void markAsBought(List<Long> selectedItems) {
        for (Long itemId : selectedItems) {
            dbHandler.insertBoughtPredictionIntoPredictionsTable(itemId);
        }
    }

    public Prediction getPredictionForItem(long id) {
        return dbHandler.getPredictionForItem(id);
    }

    public void undoMarkAsBought(List<Long> selectedItems) {
        for (Long itemId : selectedItems) {
            emptyItemsDbHandler.updatePredictionForItemInPredictionsTable(itemId);
        }
    }

    public List<EmptyItem> getPredictionsForWeek() {
        return dbHandler.getPredictionsForWeek();
    }

    public List<EmptyItem> getPredictionsForMonth() {
        return dbHandler.getPredictionsForMonth();
    }
}
