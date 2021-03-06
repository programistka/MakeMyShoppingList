package com.programistka.makemyshoppinglist.predictions;

import com.programistka.makemyshoppinglist.models.EmptyItem;
import com.programistka.makemyshoppinglist.models.Prediction;

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

    public void undoMarkAsBought(List<Long> selectedItems) {
        interactor.undoMarkAsBought(selectedItems);
    }

    public List<EmptyItem> getPredictionsForWeek() {
        return interactor.getPredictionsForWeek();
    }

    public List<EmptyItem> getPredictionsForMonth() {
        return interactor.getPredictionsForMonth();
    }


}
