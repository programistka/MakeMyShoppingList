package com.programistka.makemyshoppinglist.addemptyitem;

public class AddEmptyItemPresenter {

    private AddEmptyItemInteractor interactor;

    public AddEmptyItemPresenter(AddEmptyItemInteractor interactor, AddEmptyItemView view) {
        this.interactor = interactor;
    }

    public void addEmptyItem(AddEmptyItemActivity addEmptyItemActivity, String toString, long timeInMillis) {
        interactor.addEmptyItem(addEmptyItemActivity, toString, timeInMillis);
    }
}
