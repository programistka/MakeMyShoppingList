package net.programistka.shoppingadvisor.addemptyitem;

import android.content.Context;

public class AddEmptyItemPresenter {

    private AddEmptyItemView view;
    private AddEmptyItemInteractor interactor;

    public AddEmptyItemPresenter(AddEmptyItemView view, Context context) {
        this.view = view;
        this.interactor = new AddEmptyItemInteractor(context);
    }
    public void insertNewEmptyItem(String name) {
        interactor.insertNewEmptyItem(name);
    }

    public void insertExistingEmptyItem(long id) {
        interactor.insertExistingEmptyItem(id);
    }

    public void addNewEmptyItem(String name) {
        String emptyItemNameText = name;
        if(emptyItemNameText.length() == 0) {
            view.showEmptyItemNameMessage();
        }
        else {
            insertNewEmptyItem(emptyItemNameText);
            view.redirectToEmptyItemsHistoryView();
        }
    }
}
