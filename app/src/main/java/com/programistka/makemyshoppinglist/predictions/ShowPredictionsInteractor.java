package com.programistka.makemyshoppinglist.predictions;

import com.programistka.makemyshoppinglist.dbhandlers.DatabaseConfigApp;
import com.programistka.makemyshoppinglist.dbhandlers.FirebaseDbHandler;
import com.programistka.makemyshoppinglist.models.EmptyItem;
import com.programistka.makemyshoppinglist.models.Prediction;

import java.util.ArrayList;
import java.util.List;

public class ShowPredictionsInteractor {
    private FirebaseDbHandler firebaseDbHandler;

    public ShowPredictionsInteractor(DatabaseConfigApp databaseConfig) {
        firebaseDbHandler = FirebaseDbHandler.init(databaseConfig);
        // dbHandler = new PredictionsDbHandler(dbConfig, context);
    }

    public List<EmptyItem> getPredictions() {
        return new ArrayList<>();
        // return dbHandler.getPredictions();
    }

    public void markAsBought(List<String> selectedItems) {
        for (String itemId : selectedItems) {
            // dbHandler.insertBoughtPredictionIntoPredictionsTable(itemId);
            firebaseDbHandler.addBoughtItem(itemId);
        }
    }

    public Prediction getPredictionForItem(long id) {
        return null;
        //return dbHandler.getPredictionForItem(id);
    }

    public void undoMarkAsBought(List<String> selectedItems) {
        for (String itemId : selectedItems) {
            // emptyItemsDbHandler.updatePredictionForItemInPredictionsTable(itemId);
        }
    }

    public List<EmptyItem> getPredictionsForWeek() {
        return new ArrayList<>();
        //return dbHandler.getPredictionsForWeek();
    }

    public List<EmptyItem> getPredictionsForMonth() {
        return new ArrayList<>();
        //return dbHandler.getPredictionsForMonth();
    }
}
