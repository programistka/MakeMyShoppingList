package net.programistka.shoppingadvisor.showpredictions;

import android.content.Context;

import net.programistka.shoppingadvisor.dbhandlers.PredictionsDbHandler;
import net.programistka.shoppingadvisor.models.EmptyItem;
import net.programistka.shoppingadvisor.presenters.DbConfig;

import java.util.List;

public class ShowPredictionsPresenter {
    private PredictionsDbHandler dbHandler;

    public ShowPredictionsPresenter(DbConfig dbConfig, Context context) {
        this.dbHandler = new PredictionsDbHandler(dbConfig, context);
    }

    public List<EmptyItem> getPredictions() {
        return this.dbHandler.getPredictions();
    }

    public void markAsBought(List<Long> selectedItems) {
        for (Long itemId:selectedItems) {
            dbHandler.insertBoughtPredictionIntoPredictionsTable(itemId);
        }
    }
}
