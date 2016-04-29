package net.programistka.shoppingadvisor.presenters;

import android.content.Context;

import net.programistka.shoppingadvisor.dbhandlers.PredictionsDbHandler;
import net.programistka.shoppingadvisor.models.EmptyItem;

import java.util.List;

public class PredictionsPresenter {
    private PredictionsDbHandler dbHandler;

    public PredictionsPresenter(Context context) {
        this.dbHandler = new PredictionsDbHandler(context);
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
