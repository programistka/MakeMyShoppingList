package net.programistka.shoppingadvisor.addemptyitem;

import android.content.Context;

import net.programistka.shoppingadvisor.presenters.DbConfig;

public class AddEmptyItemPresenter {

    private AddEmptyItemView view;

    private AddEmptyItemInteractor interactor;

    public AddEmptyItemPresenter(AddEmptyItemView view, DbConfig dbConfig, Context context) {
        this.view = view;
        this.interactor = new AddEmptyItemInteractor(dbConfig, context);
    }

    public void insertNewEmptyItem(String name, Long time) {
        interactor.insertNewEmptyItem(name, time);
    }

    public void insertExistingEmptyItem(long id, Long time) {
        interactor.insertExistingEmptyItem(id, time);
    }

    public void addNewEmptyItem(String name, Long time) {
        String emptyItemNameText = name;
        if(emptyItemNameText.length() == 0) {
            view.showEmptyItemNameMessage();
        }
        else {
            insertNewEmptyItem(emptyItemNameText, time);
            view.redirectToEmptyItemsHistoryView();
        }
    }
}
