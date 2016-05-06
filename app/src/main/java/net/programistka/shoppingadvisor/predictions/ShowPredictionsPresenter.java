package net.programistka.shoppingadvisor.predictions;

import net.programistka.shoppingadvisor.models.EmptyItem;
import net.programistka.shoppingadvisor.models.Prediction;

import java.util.List;

public class ShowPredictionsPresenter {

    private ShowPredictionsInteractor interactor;

    public ShowPredictionsPresenter(ShowPredictionsInteractor interactor) {
        this.interactor = interactor;
    }

    public List<EmptyItem> getPredictions() {
        return interactor.getPredictions();
    }

    public Prediction getPredictionForItem(long id) {
        return interactor.getPredictionForItem(id);
    }

    public void markAsBought(List<Long> selectedItems) {
        interactor.markAsBought(selectedItems);
    }
}
