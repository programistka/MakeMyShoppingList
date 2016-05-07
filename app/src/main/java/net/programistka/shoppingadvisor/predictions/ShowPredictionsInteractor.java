package net.programistka.shoppingadvisor.predictions;

import android.content.Context;

import net.programistka.shoppingadvisor.dbhandlers.EmptyItemsDbHandler;
import net.programistka.shoppingadvisor.dbhandlers.PredictionsDbHandler;
import net.programistka.shoppingadvisor.models.EmptyItem;
import net.programistka.shoppingadvisor.models.Prediction;
import net.programistka.shoppingadvisor.presenters.DbConfig;

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
        for (Long itemId:selectedItems) {
            dbHandler.insertBoughtPredictionIntoPredictionsTable(itemId);
        }
    }

    public Prediction getPredictionForItem(long id) {
        return dbHandler.getPredictionForItem(id);
    }

    public void undoMarkAsBought(List<Long> selectedItems) {
        for (Long itemId:selectedItems) {
            emptyItemsDbHandler.updatePredictionForItemInPredictionsTable(itemId);
        }
    }
}
