package com.programistka.makemyshoppinglist.addemptyitem;

public class AddEmptyItemPresenter {

    private AddEmptyItemInteractor interactor;
    private AddEmptyItemView view;

    public AddEmptyItemPresenter(AddEmptyItemInteractor interactor, AddEmptyItemView view) {
        this.interactor = interactor;
        this.view = view;
    }

    public long insertNewEmptyItem(String name, long time) {
        return interactor.insertNewEmptyItem(name, time);
    }

    public void insertExistingEmptyItem(long id, long time) {
        interactor.insertExistingEmptyItem(id, time);
    }

    public void insertNewEmptyItemWithHistoryAndPrediction(String name, long time1, long time2, int daysToRunOut) {
        interactor.insertNewEmptyItemWithHistoryAndPrediction(name, time1, time2, daysToRunOut);
    }

    public void addNewEmptyItem(String name, long time) {
        if (name.length() == 0) {
            view.showEmptyItemNameMessage();
        } else {
            if (insertNewEmptyItem(name, time) > 0) {
                view.showDialogToAddAnotherItem();
            } else {
                view.showAddingErrorMessage();
            }
        }
    }
}
